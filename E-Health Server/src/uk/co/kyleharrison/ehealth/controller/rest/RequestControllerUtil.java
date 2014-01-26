package uk.co.kyleharrison.ehealth.controller.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import sun.rmi.runtime.Log;
import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.service.jackson.construct.JSONItem;
import uk.co.kyleharrison.ehealth.service.xml.XMLFacade;

public class RequestControllerUtil extends RequestController implements RequestControllerInterface {

	private static final long serialVersionUID = 1L;
	
	protected JSONItem jsonItem;
	protected XMLFacade xmlFacade;
	protected RSSChannel rssChannel;
	protected RSSItem rssItem;
	protected JSONObject[] jsonItemArray;
	protected URL url;
	
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

	public RequestControllerUtil(){
		super();
		this.jsonItem = new JSONItem();
		this.xmlFacade = new XMLFacade();
		this.rssChannel = new RSSChannel();
		this.rssItem = new RSSItem();
		this.jsonItemArray = new JSONObject[10];
		this.url = null;
	}

	public int ParseYearValue(String feedID) {
		int fID =0;
		
		try {
			if (feedID.substring(4, 5) == "a") {
				fID = 0;
			} else {
				fID = Integer.parseInt(feedID.substring(4, 5));
			}
		} catch (Exception e) {
			e.getMessage();
			fID=0;
		}
		return fID;
	}

	public void ResponseBuilder(String parameter,HttpServletResponse response){
		try {
			url = new URL("https://mbchb.dundee.ac.uk/category/"+parameter+"/feed");
			JSONObject responseObject = new JSONObject();
			JSONObject [] jsonItemsArray = ConstructJSONArray(url);
			
			Date now = new Date();

		    // Additional fields can only be added once RSS Channel has been updated "ConstructJsonArray" 
			responseObject.put("channel", this.rssChannel.getTitle());
			responseObject.put("lastUpdated", now.toString());
			responseObject.put("totalRecords", this.rssChannel.getItem_list().size());
			responseObject.put("numberOfRecordsReturned", this.rssChannel.getItem_list().size());
			
			// Items array
			responseObject.put("items",jsonItemsArray);
		
			JSONResponse(response, responseObject);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject[] ConstructJSONArray(URL url) throws JSONException {
		this.jsonItem = new JSONItem();
		this.xmlFacade.setUrl(url.toString());
		this.rssChannel = this.xmlFacade.DeconstructXMLToPojo();

		for (int x = 0; x < this.jsonItemArray.length; x++) {
			this.rssItem = rssChannel.getItem_list().get(x);
			this.jsonItemArray[x] = this.jsonItem.writeToJson(this.rssItem);
		}
		return jsonItemArray;
	}
	
	public void JSONResponse(HttpServletResponse response, JSONObject jsonResponse) {
		if (jsonResponse != null) {
			response.setContentType("text/x-json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = null;
			response.setContentType("application/json");

			try {
				out = response.getWriter();
				out.print(jsonResponse);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("JSON Response Returned");
	}
	
}
