package uk.co.kyleharrison.ehealth.controller.rest;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.ehealth.mysql.MySQLConnector;
import uk.co.ehealth.mysql.MySQLDAO;

@WebServlet("/feeds/*")
public class RequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RequestControllerUtil rcu;
	protected MySQLDAO mysqlConnector;

	public RequestController() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		System.out.println("Request Controller Initialised");
		this.rcu = new RequestControllerUtil();
		this.mysqlConnector = new MySQLDAO();
	}
	
	public void Destroy(ServletConfig config) throws ServletException{
		this.mysqlConnector.close();
	}

	private String[] getParameters(String requestPath) {

		return requestPath.split("/");
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String feedID = null;
		int fID = 0;
		String[] pathComponents = getParameters(request.getRequestURI());
		
		//System.out.println("Server reached");
		//System.out.println("Path Components : " + request.getRequestURI() + " : " +pathComponents.length);
		
		// Select year from request
		if (pathComponents.length >= 0 && pathComponents.length <= 4) {
			feedID = pathComponents[3];
		}

		fID = rcu.ParseYearValue(feedID);
		
		switch (fID) {
			case 0:
				rcu.ResponseBuilder("all-years",response);
			break;
			case 1:
				rcu.ResponseBuilder("year1",response);
				break;
			case 2:
				rcu.ResponseBuilder("year2",response);
				break;
			case 3:
				rcu.ResponseBuilder("year3",response);
				break;
			case 4:
				rcu.ResponseBuilder("year4",response);
				break;
			case 5:
				rcu.ResponseBuilder("year5",response);
				break;
			case 9:
				rcu.ResponsePresistentStorage("year1");
			default:
				//Return empty json to app
				
			}
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}
}
