package uk.co.kyleharrison.ehealth.controller.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.service.jackson.construct.JSONItem;
import uk.co.kyleharrison.ehealth.service.xml.XMLFacade;

public class RequestControllerUtil extends RequestController {

	private static final long serialVersionUID = 1L;
	
	protected JSONItem item;
	protected XMLFacade XMLFacade;
	protected RSSChannel RC;
	protected RSSItem RI;
	protected JSONObject[] itemList;
	protected URL url;
	
	public JSONItem getItem() {
		return item;
	}

	public void setItem(JSONItem item) {
		this.item = item;
	}

	public XMLFacade getXMLFacade() {
		return XMLFacade;
	}

	public void setXMLFacade(XMLFacade xMLFacade) {
		XMLFacade = xMLFacade;
	}

	public RSSChannel getRC() {
		return RC;
	}

	public void setRC(RSSChannel rC) {
		RC = rC;
	}

	public RSSItem getRI() {
		return RI;
	}

	public void setRI(RSSItem rI) {
		RI = rI;
	}

	public JSONObject[] getItemList() {
		return itemList;
	}

	public void setItemList(JSONObject[] itemList) {
		this.itemList = itemList;
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
	}

	public int parseYearValue(String feedID) {
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

	public void callURL(String parameter,HttpServletResponse response){
		try {
			url = new URL("https://mbchb.dundee.ac.uk/category/"+parameter+"/feed");
			JSONObject jo = new JSONObject();
			jo.put("items",getFeedJSON(url));

			printOut(response, jo);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void printOut(HttpServletResponse response, JSONObject json) {
		if (json != null) {
			response.setContentType("text/x-json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = null;
			response.setContentType("application/json");

			try {
				out = response.getWriter();
				out.print(json);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public JSONObject[] getFeedJSON(URL url) throws JSONException {
		this.item = new JSONItem();
		this.XMLFacade.setUrl(url.toString());
		this.RC = XMLFacade.DeconstructXMLToPojo();

		for (int x = 0; x < this.itemList.length; x++) {
			this.RI = RC.getItem_list().get(x);
			this.itemList[x] = this.item.writeToJson(this.RI);
		}
		return itemList;
	}
	
}
