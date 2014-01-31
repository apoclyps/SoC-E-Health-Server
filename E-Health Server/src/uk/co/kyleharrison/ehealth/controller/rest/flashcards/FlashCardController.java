package uk.co.kyleharrison.ehealth.controller.rest.flashcards;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.ehealth.storage.mysql.MySQLDAO;

@WebServlet("/FlashCardController")
public class FlashCardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private FlashCardControllerUtil fcu;
	protected MySQLDAO mysqlConnector;

	public FlashCardController() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		System.out.println("Flash Card Controller Created : "+new Date().toString());
		this.fcu = new FlashCardControllerUtil();
		this.mysqlConnector = new MySQLDAO();
	}

	public void Destroy(ServletConfig config) throws ServletException {
		this.mysqlConnector.close();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Flash Card Controller Accessed :"+new Date().toString());
		String callback = "callback";
		int subjectID = 1;
		
		try{
			try{
				String [] years = request.getParameter("years").split("x");
				if(years.length==1){
					subjectID = Integer.parseInt(request.getParameter("years"));
				}else if (years.length>1){
					subjectID = (int) (Math.random()*years.length);
					subjectID = subjectID +2;
				}
			}catch(NullPointerException npe){
				subjectID = (int) (Math.random()*5)+1;
			}

		}catch(Exception e){
			System.out.println("Announcements Exception for years: "+new Date().toString());
			e.printStackTrace();
		}

		try {
			if (request.getParameter("callback") == null) {
				callback = "callback";
			} else {
				callback = request.getParameter("callback");
			}
		} catch (NullPointerException npe) {
			callback = "callback";
			System.out.println("Flash Card Exception for Callback ID : "+new Date().toString());
			npe.getStackTrace();
		}
		
		fcu.ResponseBuilder(callback, subjectID, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
