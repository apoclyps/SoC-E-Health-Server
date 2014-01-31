package uk.co.kyleharrison.ehealth.controller.rest.announcements;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.ehealth.storage.mysql.MySQLDAO;

@WebServlet("/AnnouncementController")
public class AnnouncementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private AnnouncementControllerUtil ifcu;
	protected MySQLDAO mysqlConnector;
	
    public AnnouncementController() {
        super();
        System.out.println("Announcement Controller Created : "+ new Date().toString());
        this.ifcu = new AnnouncementControllerUtil();
        this.mysqlConnector = new MySQLDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Item Feed Controller Accessed : "+ new Date().toString());
		String[] years = null ;
		String callback = null;
		int offset = 0;
		int limit = 0;
		
		try{
			years = request.getParameter("years").split("x");
		}catch(Exception e){
			System.out.println("Announcements Exception for years: "+new Date().toString());
			//e.printStackTrace();
		}
		
		try{
			callback = request.getParameter("callback");
		}catch(Exception e){
			System.out.println("Announcements Exception for callback: "+new Date().toString());
		//	e.printStackTrace();
			callback="callback";
		}
		
		try{
			offset = Integer.parseInt(request.getParameter("offset"));
		}catch(NumberFormatException nfe){
			System.out.println("Announcements Exception for offset: "+new Date().toString());
		//	nfe.printStackTrace();
			offset=0;
		}
		
		try{
			limit = Integer.parseInt(request.getParameter("limit"));
		}catch(NumberFormatException nfe){
			System.out.println("Announcements Exception for limit: "+new Date().toString());
		//	nfe.printStackTrace();
			limit=10;
		}

		ifcu.SpecificYearsResponseBuilder(callback, years, offset, limit, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
