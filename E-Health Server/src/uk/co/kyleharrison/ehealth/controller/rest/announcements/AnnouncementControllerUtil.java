package uk.co.kyleharrison.ehealth.controller.rest.announcements;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.ehealth.storage.mysql.MySQLFacade;
import uk.co.kyleharrison.ehealth.model.inmemory.rss.RSSChannel;
import uk.co.kyleharrison.ehealth.model.inmemory.rss.RSSItem;
import uk.co.kyleharrison.ehealth.model.json.JSONItem;
import uk.co.kyleharrison.ehealth.model.xml.XMLFacade;

public class AnnouncementControllerUtil {

	protected JSONItem jsonItem;
	protected XMLFacade xmlFacade;
	protected RSSChannel rssChannel;
	protected RSSItem rssItem;
	protected JSONObject[] jsonItemArray;
	protected MySQLFacade mysqlFacade;
	
	public AnnouncementControllerUtil() {
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
	}
	
	public JSONObject[] ConstructYearSpecificJSONArrayFromMySQL(String[] years,int limit,int offset) throws JSONException {
		this.jsonItem = new JSONItem();
		this.jsonItemArray=null;
		
		ArrayList<RSSItem> rssItems = null;
		try{
			rssItems = mysqlFacade.selectItemsFromSpecifiedYears(years,limit,offset);
		}catch(NullPointerException e){
			e.printStackTrace();
		}

		try{
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
