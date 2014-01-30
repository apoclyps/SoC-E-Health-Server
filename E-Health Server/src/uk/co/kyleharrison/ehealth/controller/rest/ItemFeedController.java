package uk.co.kyleharrison.ehealth.controller.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.ehealth.storage.mysql.MySQLDAO;

@WebServlet("/ItemFeedController")
public class ItemFeedController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ItemFeedControllerUtil ifcu;
	protected MySQLDAO mysqlConnector;
	
    public ItemFeedController() {
        super();
        this.ifcu = new ItemFeedControllerUtil();
        this.mysqlConnector = new MySQLDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Item Feed Servlet Hit");
		String[] years = null ;
		String callback = null;
		int offset = 0;
		int limit = 0;
		
		try{
			years = request.getParameterValues("years");
			callback = request.getParameter("callback");
		}catch(Exception e){
			e.printStackTrace();
			callback="callback";
		}
		
		try{
			offset = Integer.parseInt(request.getParameter("offset"));
			limit = Integer.parseInt(request.getParameter("limit"));
		}catch(NumberFormatException nfe){
			//nfe.getMessage();
			offset=0;
			limit=10;
		}
		catch(Exception e){
			e.printStackTrace();
		}

		ifcu.SpecificYearsResponseBuilder(callback, years, offset, limit, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
