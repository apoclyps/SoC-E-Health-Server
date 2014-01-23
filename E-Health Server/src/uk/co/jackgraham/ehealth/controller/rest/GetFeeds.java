package uk.co.jackgraham.ehealth.controller.rest;

import java.net.MalformedURLException;
import java.net.URL;

import uk.co.jackgraham.ehealth.controller.interfaces.RestInterface;
import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.service.jackson.construct.JSONItem;
import uk.co.kyleharrison.ehealth.service.xml.XMLFacade;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/feeds")
public class GetFeeds implements RestInterface {
	
	JSONItem item;
	XMLFacade XMLFacade = new XMLFacade();
	RSSChannel RC = new RSSChannel();
	RSSItem RI = new RSSItem();
	JSONObject[] itemList = new JSONObject[10];
	URL url=null;
	
	@GET
	@Path("/{param}")
	public JSONObject[] getFeed(@PathParam("param") String FeedID) throws JSONException {

		
		
		switch (FeedID) {

		case "year1":
			// Get year 1 RSS
			try {
				 url = new URL("https://mbchb.dundee.ac.uk/category/year1/feed");
				 return getFeedJSON(url);	 
			} catch (MalformedURLException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case "year2":
			// Get year 2 RSS
			try {
				 url = new URL("https://mbchb.dundee.ac.uk/category/year2/feed"); 
				 return getFeedJSON(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "year3":
			// Get year 3 RSS
			try {
				 url = new URL("https://mbchb.dundee.ac.uk/category/year3/feed");
				 return getFeedJSON(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "year4":
			// Get year 4 RSS
			try {
				 url = new URL("https://mbchb.dundee.ac.uk/category/year4/feed");
				 return getFeedJSON(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "year5":
			// Get year 5 RSS
			try {
				 url = new URL("https://mbchb.dundee.ac.uk/category/year5/feed");
				 return getFeedJSON(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "yearALL":
			// Get all years RSS
			try {
				 url = new URL("https://mbchb.dundee.ac.uk/category/all-years/feed/");
				 return getFeedJSON(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

		return null;
	}

	@Override
	public JSONObject[] getFeedJSON(URL url) throws JSONException {
		// TODO Auto-generated method stub
		 item = new JSONItem();
		 
		 XMLFacade.setUrl(url.toString());
		 RC = XMLFacade.DeconstructXMLToPojo();
		 
		 for(int x=0;x<itemList.length;x++)
		 {
			 RI = RC.getItem_list().get(x);
			 itemList[x] = item.writeToJson(RI);
		 }
		
		return itemList;
	}

}
