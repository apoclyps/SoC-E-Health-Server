package uk.co.kyleharrison.ehealth.controller.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.ehealth.storage.mysql.MySQLFacade;
import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.service.jackson.model.JSONItem;
import uk.co.kyleharrison.ehealth.service.xml.XMLFacade;

public class ItemFeedControllerUtil {

	protected JSONItem jsonItem;
	protected XMLFacade xmlFacade;
	protected RSSChannel rssChannel;
	protected RSSItem rssItem;
	protected JSONObject[] jsonItemArray;
	protected MySQLFacade mysqlFacade;
	
	public ItemFeedControllerUtil() {
		super();
		this.jsonItem = new JSONItem();
		this.xmlFacade = new XMLFacade();
		this.rssChannel = new RSSChannel();
		this.rssItem = new RSSItem();
		this.jsonItemArray = new JSONObject[10];
		this.mysqlFacade = new MySQLFacade();
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
	
	public JSONObject[] ConstructYearSpecificJSONArrayFromMySQL(String[] years,int limit,int offset) throws JSONException {
		this.jsonItem = new JSONItem();
		this.jsonItemArray=null;
		
		System.out.println("Limit"+limit + " : OFFSET :" +offset);
		
		// get result set
		System.out.println("Selecting Items from Mysql");
		ArrayList<RSSItem> rssItems = null;
		try{
			rssItems = mysqlFacade.selectItemsFromSpecifiedYears(years,limit,offset);
		}catch(NullPointerException e){
			e.printStackTrace();
		}

		try{
			System.out.println("Size ="+rssItems.size());
			this.jsonItemArray = new JSONObject[rssItems.size()];
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
		for (int x = 0; x < rssItems.size(); x++) {
			try{
				this.jsonItemArray[x]  = this.jsonItem.writeToJson(rssItems.get(x));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return jsonItemArray;
	}
	
	public void SpecificYearsResponseBuilder(String callback, String[] years,
			int offset, int limit, HttpServletResponse response) {

		try {
			JSONObject responseObject = new JSONObject();
			JSONObject[] jsonFlashCardArray = ConstructYearSpecificJSONArrayFromMySQL(years,limit,offset);
			
		//	System.out.println("Collected Results : "+jsonFlashCardArray.length);

			Date now = new Date();

			responseObject.put("channel", this.rssChannel.getTitle());
			responseObject.put("lastUpdated", now.toString());
			responseObject.put("totalRecords", this.rssChannel.getItem_list()
					.size());
			responseObject.put("numberOfRecordsReturned", this.rssChannel
					.getItem_list().size());

			responseObject.put("flashcards", jsonFlashCardArray);

			JSONResponse(response, responseObject, callback);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
