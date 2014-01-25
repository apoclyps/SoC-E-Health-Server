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

@WebServlet("/feeds/*")
public class RequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	private RequestControllerUtil rcu;

	public RequestController() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		System.out.println("Request Controller Initialised");
		this.rcu = new RequestControllerUtil();
		this.rcu.item = new JSONItem();
		this.rcu.XMLFacade = new XMLFacade();
		this.rcu.RC = new RSSChannel();
		this.rcu.RI = new RSSItem();
		this.rcu.itemList = new JSONObject[10];
		this.rcu.url = null;
	}

	private String[] getParameters(String requestPath) {

		return requestPath.split("/");
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String feedID = null;
		int fID = 0;
		String[] pathComponents = getParameters(request.getRequestURI());
		
		System.out.println("Server reached");
		System.out.println("Path Components : " + request.getRequestURI() + " : " +pathComponents.length);
		
		// Select year from request
		if (pathComponents.length >= 0 && pathComponents.length <= 4) {
			feedID = pathComponents[3];
		}

		fID = rcu.parseYearValue(feedID);
		
		switch (fID) {
			case 1:
				rcu.callURL("year1",response);
				break;
			case 2:
				rcu.callURL("year2",response);
				break;
			case 3:
				rcu.callURL("year3",response);
				break;
			case 4:
				rcu.callURL("year4",response);
				break;
			case 5:
				rcu.callURL("year5",response);
				break;
			case 6:
				rcu.callURL("all-years",response);
				break;
			}
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
