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
       
	private RequestControllerUtil rcu;
	protected MySQLDAO mysqlConnector;
	
    public FlashCardController() {
        super();
       this.rcu = new RequestControllerUtil();
       this.mysqlConnector = new MySQLDAO();
    }
    
	
	public void Destroy(ServletConfig config) throws ServletException{
		this.mysqlConnector.close();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Flash Card servlet hit");
		String pageID = "0";
		String callback ="callback";
	
		String[] pathComponents = getParameters(request.getRequestURI());

	
		//rcu.ResponseBuilder("all-years",pageID,callback,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private String[] getParameters(String requestPath) {
		return requestPath.split("/");
	}

}
