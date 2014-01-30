package uk.co.kyleharrison.ehealth.controller.rest.flashcards;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.ehealth.storage.mysql.MySQLDAO;
import uk.co.kyleharrison.ehealth.controller.rest.RequestControllerUtil;

@WebServlet("/FlashCardController")
public class FlashCardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private FlashCardControllerUtil fcu;
	protected MySQLDAO mysqlConnector;
	
    public FlashCardController() {
        super();
    }
    
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Request Controller Initialised");
		this.fcu = new FlashCardControllerUtil();
		this.mysqlConnector = new MySQLDAO();
	}
    
	
	public void Destroy(ServletConfig config) throws ServletException{
		this.mysqlConnector.close();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Flash Card servlet hit");
		String pageID = "0";
		String callback ="callback";
		int subjectID=1;
	
		String[] pathComponents = getParameters(request.getRequestURI());

		fcu.ResponseBuilder("all-years",pageID,callback,subjectID,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private String[] getParameters(String requestPath) {
		return requestPath.split("/");
	}

}
