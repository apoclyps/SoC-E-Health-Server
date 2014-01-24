package uk.co.kyleharrison.ehealth.controller.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.service.jackson.construct.JSONItem;
import uk.co.kyleharrison.ehealth.service.xml.XMLFacade;

/**
 * Servlet implementation class RequestController
 */
@WebServlet("/feeds/*")
public class RequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private JSONItem item;
	private XMLFacade XMLFacade;
	private RSSChannel RC;
	private RSSItem RI;
	private JSONObject[] itemList;
	private URL url;

	public RequestController() {
		super();

	}

	public void init(ServletConfig config) throws ServletException {
		System.out.println("Request Controller Initialised");
		this.item = new JSONItem();
		this.XMLFacade = new XMLFacade();
		this.RC = new RSSChannel();
		this.RI = new RSSItem();
		this.itemList = new JSONObject[10];
		this.url = null;
	}

	private String[] getParameters(String requestPath) {

		return requestPath.split("/");
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Server reached");

		String feedID = null;

		String[] pathComponents = getParameters(request.getRequestURI());

		System.out.println("Path Components : " + request.getRequestURI() + " : " +pathComponents.length);
		
		for(String s : pathComponents){
			System.out.println(s);
		}

		if (pathComponents.length >= 0 && pathComponents.length <= 4) {
			feedID = pathComponents[3];
			System.out.println("Feed ID : " + feedID);
		}

		int fID = 0;
		try {
			if (feedID.substring(4, 5) == "A") {
				fID = 6;
			} else {
				fID = Integer.parseInt(feedID.substring(4, 5));
			}
		} catch (Exception e) {
			e.getMessage();
		}

		switch (fID) {

		case 1:
			// Get year 1 RSS
			try {
				url = new URL("https://mbchb.dundee.ac.uk/category/year1/feed");
				JSONObject jo = new JSONObject();
				jo.put("items",getFeedJSON(url));
				System.out.println("jo test"+jo.toString());
				printOut(response, jo);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case 2:
			// Get year 2 RSS
			try {
				url = new URL("https://mbchb.dundee.ac.uk/category/year2/feed");
				// return getFeedJSON(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			// Get year 3 RSS
			try {
				url = new URL("https://mbchb.dundee.ac.uk/category/year3/feed");
				// return getFeedJSON(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 4:
			// Get year 4 RSS
			try {
				url = new URL("https://mbchb.dundee.ac.uk/category/year4/feed");
				// return getFeedJSON(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 5:
			// Get year 5 RSS
			try {
				url = new URL("https://mbchb.dundee.ac.uk/category/year5/feed");
				// return getFeedJSON(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 6:
			// Get all years RSS
			try {
				url = new URL(
						"https://mbchb.dundee.ac.uk/category/all-years/feed/");
				// return getFeedJSON(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

	private void printOut(HttpServletResponse response, JSONObject json) {
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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
