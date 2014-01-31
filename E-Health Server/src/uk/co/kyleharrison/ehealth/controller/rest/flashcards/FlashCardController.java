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
		String pageID = "0";
		String callback = "callback";
		int subjectID = 1;

		try {
			pageID = (String) request.getParameter("page");

			if (Integer.parseInt(pageID) > 0) {
				int id = Integer.parseInt(pageID);
				pageID = Integer.toString(id);
			} else {
				pageID = "0";
			}
		} catch (Exception e) {
			pageID = "0";
		}
		try {
			if (request.getParameter("callback") == null) {
				callback = "callback";
			} else {
				callback = request.getParameter("callback");
			}
		} catch (NullPointerException npe) {
			System.out.println("callback not set");
			callback = "callback";
		}
		try {
			if (request.getParameter("subjectID") == null) {
				subjectID=1;
			} else {
				subjectID = request.getIntHeader("subjectID");
			}
		} catch (NullPointerException npe) {
			//System.out.println("callback not set");
			subjectID=1;
		}

		switch (subjectID) {
		case 0:
			fcu.ResponseBuilder("all-years", pageID, callback, subjectID, response);
		break;
		case 1:
			fcu.ResponseBuilder("all-years", pageID, callback, subjectID, response);
			break;
		case 2:
			fcu.ResponseBuilder("all-years", pageID, callback, subjectID, response);
			break;
		case 3:
			fcu.ResponseBuilder("all-years", pageID, callback, subjectID, response);
			break;
		case 4:
			fcu.ResponseBuilder("all-years", pageID, callback, subjectID, response);
			break;
		case 5:
			fcu.ResponseBuilder("all-years", pageID, callback, subjectID, response);
			break;
		case 6 :
			fcu.ResponseBuilder("all-years", pageID, callback, subjectID, response);
		default:
			fcu.ResponseBuilder("all-years", pageID, callback, subjectID, response);
			
		}

		fcu.ResponseBuilder("all-years", pageID, callback, subjectID, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
