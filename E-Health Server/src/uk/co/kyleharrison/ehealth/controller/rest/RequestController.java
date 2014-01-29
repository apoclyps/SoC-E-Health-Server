package uk.co.kyleharrison.ehealth.controller.rest;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.ehealth.storage.mysql.MySQLDAO;

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
		String pageID = "0";
		String yearID = null;
		int fID = 0;
		
		String[] pathComponents = getParameters(request.getRequestURI());
		yearID = pathComponents[3];

		//boolean pageRequest = rcu.PagingCheck(request);

		fID = rcu.ParseYearValue(yearID);
		
		switch (fID) {
			case 0:
				//boolean download = rcu.CheckRSSFeed();
			//	System.out.println("RSS Object = "+download);
				rcu.ResponseBuilder("all-years",pageID,response);
			break;
			case 1:
				rcu.ResponseBuilder("year1",pageID,response);
				rcu.ResponsePresistentStorage("year1",pageID);
				break;
			case 2:
				rcu.ResponseBuilder("year2",pageID,response);
				rcu.ResponsePresistentStorage("year2",pageID);
				break;
			case 3:
				rcu.ResponseBuilder("year3",pageID,response);
				rcu.ResponsePresistentStorage("year3",pageID);
				break;
			case 4:
				rcu.ResponseBuilder("year4",pageID,response);
				rcu.ResponsePresistentStorage("year4",pageID);
				break;
			case 5:
				rcu.ResponseBuilder("year5",pageID,response);
				rcu.ResponsePresistentStorage("year5",pageID);
				break;
			default:
				//Return empty json to app
				
			}
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}
}
