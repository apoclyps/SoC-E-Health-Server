package uk.co.jackgraham.ehealth.controller.rest;

import java.net.MalformedURLException;
import java.net.URL;

import uk.co.jackgraham.ehealth.controller.interfaces.RestInterface;
import uk.co.kyleharrison.ehealth.service.xml.XMLFacade;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/feeds")
public class GetFeeds implements RestInterface {

	@GET
	@Path("/{param}")
	public URL getFeed(@PathParam("param") String FeedID) {

		XMLFacade XMLFacade = new XMLFacade();
		
		URL url=null;
		
		switch (FeedID) {

		case "year1":
			// Get year 1 RSS
			try {
				 url = new URL("https://mbchb.dundee.ac.uk/category/year1/feed");
				 
				 XMLFacade.setUrl(url.toString());
				 XMLFacade.DeconstructXMLToPojo();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case "year2":
			// Get year 2 RSS
			try {
				 url = new URL("https://mbchb.dundee.ac.uk/category/year2/feed");
				 
				 XMLFacade.setUrl(url.toString());
				 XMLFacade.DeconstructXMLToPojo();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "year3":
			// Get year 3 RSS
			try {
				 url = new URL("https://mbchb.dundee.ac.uk/category/year3/feed");
				 
				 XMLFacade.setUrl(url.toString());
				 XMLFacade.DeconstructXMLToPojo();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "year4":
			// Get year 4 RSS
			try {
				 url = new URL("https://mbchb.dundee.ac.uk/category/year4/feed");
				 
				 XMLFacade.setUrl(url.toString());
				 XMLFacade.DeconstructXMLToPojo();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "year5":
			// Get year 5 RSS
			try {
				 url = new URL("https://mbchb.dundee.ac.uk/category/year5/feed");
				 
				 XMLFacade.setUrl(url.toString());
				 XMLFacade.DeconstructXMLToPojo();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "yearALL":
			// Get all years RSS
			try {
				 url = new URL("https://mbchb.dundee.ac.uk/category/all-years/feed/");
				 
				 XMLFacade.setUrl(url.toString());
				 XMLFacade.DeconstructXMLToPojo();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

		return url;
	}

}
