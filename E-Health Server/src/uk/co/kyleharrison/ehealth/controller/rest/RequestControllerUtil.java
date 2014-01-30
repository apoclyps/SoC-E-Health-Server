package uk.co.kyleharrison.ehealth.controller.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.ehealth.storage.mysql.MySQLDAO;
import uk.co.ehealth.storage.mysql.MySQLFacade;
import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.service.jackson.model.JSONItem;
import uk.co.kyleharrison.ehealth.service.xml.XMLFacade;

public class RequestControllerUtil extends RequestController implements RequestControllerInterface {

	private static final long serialVersionUID = 1L;

	protected JSONItem jsonItem;
	protected XMLFacade xmlFacade;
	protected RSSChannel rssChannel;
	protected RSSItem rssItem;
	protected JSONObject[] jsonItemArray;
	protected URL url;
	protected MySQLFacade mysqlFacade;

	public JSONItem getItem() {
		return jsonItem;
	}

	public void setItem(JSONItem jsonItem) {
		this.jsonItem = jsonItem;
	}

	public XMLFacade getXMLFacade() {
		return xmlFacade;
	}

	public void setXMLFacade(XMLFacade xmlFacade) {
		this.xmlFacade = xmlFacade;
	}

	public RSSChannel getRC() {
		return rssChannel;
	}

	public void setRC(RSSChannel rssChannel) {
		this.rssChannel = rssChannel;
	}

	public RSSItem getRI() {
		return rssItem;
	}

	public void setRI(RSSItem rssItem) {
		this.rssItem = rssItem;
	}

	public JSONObject[] getItemList() {
		return jsonItemArray;
	}

	public void setItemList(JSONObject[] jsonItemArray) {
		this.jsonItemArray = jsonItemArray;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public RequestControllerUtil() {
		super();
		this.jsonItem = new JSONItem();
		this.xmlFacade = new XMLFacade();
		this.rssChannel = new RSSChannel();
		this.rssItem = new RSSItem();
		this.jsonItemArray = new JSONObject[10];
		this.url = null;
		this.mysqlFacade = new MySQLFacade();
	}

	public int ParseYearValue(String feedID) {
		int fID = 0;

		try {
			if (feedID.substring(4, 5) == "a") {
				fID = 0;
			} else {
				fID = Integer.parseInt(feedID.substring(4, 5));
			}
		} catch (Exception e) {
			e.getMessage();
			fID = 0;
		}
		return fID;
	}

	public void ResponseBuilder(String year, String page,String callback,
			HttpServletResponse response) {
		try {
			url = new URL("https://mbchb.dundee.ac.uk/category/" + year
					+ "/feed/?paged=" + page);
			
			System.out.println("url"+url.toString());
			JSONObject responseObject = new JSONObject();
			JSONObject[] jsonItemsArray = ConstructJSONArray(url);

			Date now = new Date();

			// Additional fields can only be added once RSS Channel has been
			// updated "ConstructJsonArray"
			responseObject.put("channel", this.rssChannel.getTitle());
			responseObject.put("lastUpdated", now.toString());
			responseObject.put("totalRecords", this.rssChannel.getItem_list()
					.size());
			responseObject.put("numberOfRecordsReturned", this.rssChannel
					.getItem_list().size());

			// Items array
			responseObject.put("items", jsonItemsArray);

			JSONResponse(response, responseObject,callback);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void DefaultResponseBuilder(String page, String callback,HttpServletResponse response) {
		try {
			JSONObject responseObject = new JSONObject();
			JSONObject[] jsonItemsArray = ConstructJSONArrayFromMySQL();
			
			System.out.println("Collected Results : "+jsonItemsArray.length);

			Date now = new Date();

			// Additional fields can only be added once RSS Channel has been
			// updated "ConstructJsonArray"
			responseObject.put("channel", this.rssChannel.getTitle());
			responseObject.put("lastUpdated", now.toString());
			responseObject.put("totalRecords", this.rssChannel.getItem_list()
					.size());
			responseObject.put("numberOfRecordsReturned", this.rssChannel
					.getItem_list().size());

			responseObject.put("items", jsonItemsArray);

			JSONResponse(response, responseObject, callback);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public JSONObject[] ConstructJSONArray(URL url) throws JSONException {
		this.jsonItem = new JSONItem();
		this.xmlFacade.setUrl(url.toString());
		this.rssChannel = this.xmlFacade.DeconstructXMLToPojo();
		this.jsonItemArray = new JSONObject[this.rssChannel.getItem_list().size()];

		for (int x = 0; x < this.jsonItemArray.length; x++) {
			this.rssItem = rssChannel.getItem_list().get(x);
			this.jsonItemArray[x] = this.jsonItem.writeToJson(this.rssItem);
		}
		return jsonItemArray;
	}
	
	public JSONObject[] ConstructJSONArrayFromMySQL() throws JSONException {
		this.jsonItem = new JSONItem();
		
		// get result set
		System.out.println("Selecting Items from Mysql");
		ArrayList<RSSItem> rssItems = mysqlFacade.selectItemsFromAllYear();
		
		try{
			System.out.println("Size ="+rssItems.size());
			this.jsonItemArray = new JSONObject[rssItems.size()];
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
		for (int x = 0; x < rssItems.size(); x++) {
			//jsonItemArray[x] = rssItems.get(x);
		//	System.out.println("Question "+x +" " +this.jsonItem.getQuestion());
			try{
				//this.jsonItemArray[x] 
				this.jsonItemArray[x]  = this.jsonItem.writeToJson(rssItems.get(x));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return jsonItemArray;
	}
	
	public void JSONResponse(HttpServletResponse response,
			JSONObject jsonResponse,String callback) {
		if (jsonResponse != null) {
			response.setContentType("text/x-json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = null;
			response.setContentType("application/json");

			try {
				out = response.getWriter();
			//	out.print(jsonResponse);
				out.print(callback+"(" + jsonResponse+ ");");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("JSON Response Returned");
	}

	public void ResponsePresistentStorage(String yearID, String pageID) {
		try {
			url = new URL("https://mbchb.dundee.ac.uk/category/" + yearID
					+ "/feed/?paged=" + pageID);

			MySQLDAO mysqlDAO = new MySQLDAO();
			mysqlDAO.insertChannel(rssChannel);
			for (RSSItem rssItem : this.rssChannel.getItem_list()) {
				rssItem.setYear(Integer.parseInt(yearID.substring(4, 5)));
				mysqlDAO.insertItem(rssItem);
			}

			System.out.println("Storage updated");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean CheckRSSFeed(){
		this.xmlFacade.setUrl(url.toString());
		RSSChannel rssChannelMemory = this.xmlFacade.DeconstructXMLToPojo();
		return (this.rssChannel.getLastBuildDate().equals(rssChannelMemory.getLastBuildDate()));
	}
	
	public boolean pagingCheck(HttpServletRequest request){
	try{
		String startID = request.getParameter("start");
		String endID = request.getParameter("end");
		String pageID = request.getParameter("page");
		
		int page = Integer.getInteger(endID)/ Integer.getInteger(startID);

		System.out.println("PageID "+pageID + " page" + page);
		System.out.println("startID "+startID);
		System.out.println("endID "+endID);
		
		// calculate return
		
	}catch(NullPointerException e){
		System.out.println("RC : Parameter exception");
	}
	return false;
	}

	public void PushNotification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("type", "broadcast");
		request.setAttribute("pushJSON", this.rssChannel.getItem_list().get(0).getPushString());
	    RequestDispatcher rd = request.getRequestDispatcher("/PushController/");
		rd.forward(request, response);
	}



}
