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

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pageID = "0";
		String yearID = null;
		String callback ="callback";
		int fID = 0;
		
		try{
			yearID=request.getParameter("year");
		}catch(Exception e){
			System.out.println("Error : Request Controller - Path COmponent year");
		}
		
		try{
			pageID = (String) request.getParameter("page");
			
				if(Integer.parseInt(pageID)>0){
					//int id = Integer.parseInt(pageID)-1;
					int id = Integer.parseInt(pageID);
					pageID = Integer.toString(id);
				}else{
					pageID="0";
				}
			}catch(Exception e){
				System.out.println("page not set");
				pageID="0";
			}
			
			try{
				if(request.getParameter("callback")==null){
					callback="callback";
				}else{
					callback=request.getParameter("callback");
				}
			}catch(NullPointerException npe){
				System.out.println("callback not set");
				callback="callback";
			}
			
//		}catch(Exception e){
//			e.printStackTrace();
//			pageID="0";
//			callback="callback";
//		}

		fID = rcu.ParseYearValue(yearID);
		
		switch (fID) {
			case 0:
				//rcu.ResponseBuilder("all-years",pageID,callback,response);
				rcu.DefaultResponseBuilder(pageID, callback, response);
			break;
			case 1:
				rcu.ResponseBuilder("year1",pageID,callback,response);
				break;
			case 2:
				rcu.ResponseBuilder("year2",pageID,callback,response);
				break;
			case 3:
				rcu.ResponseBuilder("year3",pageID,callback,response);
				break;
			case 4:
				rcu.ResponseBuilder("year4",pageID,callback,response);
				break;
			case 5:
				rcu.ResponseBuilder("year5",pageID,callback,response);
				break;
			case 6 :
				rcu.PushNotification(request, response);
			default:
				rcu.ResponseBuilder("all-years","0",callback,response);
				
			}
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}
}
