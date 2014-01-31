package uk.co.euanmorrison.ehealth.push;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PushPanel
 */
@WebServlet("/PushPanel")
public class PushPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PushPanel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseText = "";
		
		Map<String,String[]> args = request.getParameterMap();
		
		switch(args.get("function")[0]) {
		
		case "broadcast":
			try {
				String pushText = args.get("pushText")[0];
				PushFacade pf  = new PushFacade();
				pf.broadcast(pushText);
				responseText = "Successfully pushed text!";
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				responseText = "Push failed.";
			}
			break;
			
		case "add_device":
			try {
				String platform = args.get("platform")[0];
				String token = args.get("token")[0];
				
				PushFacade pf  = new PushFacade();
				
				switch(platform) {
					case "ios":
						pf.addSubApns(token);
						break;
					case "android":
						pf.addSubGcm(token);
						break;
				}
				
				responseText = "Successfully added device!";
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				responseText = "Add failed.";
			}
			break;
			
		case "remove_device":
			try {
				String platform = args.get("platform")[0];
				String token = args.get("token")[0];
				
				PushFacade pf  = new PushFacade();
				
				switch(platform) {
					case "ios":
						pf.deleteSubApns(token);
						break;
					case "android":
						pf.deleteSubGcm(token);
						break;
				}
				
				responseText = "Successfully removed device!";
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				responseText = "Removal failed.";
			}
			break;
			
		} // end switch
		
		PrintWriter pw = response.getWriter();
		pw.print(responseText);

		pw.close();
	}

}
