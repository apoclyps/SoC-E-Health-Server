package uk.co.euanmorrison.ehealth.push;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PushController
 */
@WebServlet("/PushController")
public class PushController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PushServer ps;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PushController() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		System.out.println("Push Controller initialised");
		this.ps = new PushServer();
	}

	public void Destroy(ServletConfig config) throws ServletException {
		System.out.println("Push Controller shutting down");
		this.ps.serverStop();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("SERVLET 'GET' HIT");
		
		String responseOutput = "";
		
		Map<String,String[]> params = request.getParameterMap();

		String type = (String) request.getAttribute("type");
		try{
			System.out.println("type"+type);
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
		if(type.equals("broadcast")){
			System.out.println("Broadcasting");
			try{
				String pushJSON = (String) request.getAttribute("pushJSON");
				System.out.println("PushJson"+pushJSON);
				pushPost(request,response,pushJSON);
				
			}catch(NullPointerException e){
				e.printStackTrace();
			}
		}else{
			switch(params.get("platform")[0]) {
			case "ios":
				if(ps.addSubApns(params.get("token")[0])) {
					// successfully added to iOS token set
					System.out.println("added token to iOS set: "+params.get("token")[0]);
					responseOutput = "true";
				}
				else {
					System.out.println("Failed to add token");
					responseOutput = "false";
				}
				break;
				
			case "android":
				if(ps.addSubGcm(params.get("token")[0])) {
					// successfully added to Android token set
					System.out.println("added token to Android set: "+params.get("token")[0]);
					responseOutput = "true";
				}
				else {
					System.out.println("Failed to add token");
					responseOutput = "false";
				}
				break;
		}
		/*for(String value:params.get("test")) {
			System.out.println("Value of 'test': " + value);
		}*/
		PrintWriter pw = response.getWriter();
		pw.print(responseOutput);
		pw.close();
		//ps.pushApns(ps.testJson(), ps.getSubsApns());
		ps.saveSubs();
		}
	}

	protected void pushPost(HttpServletRequest request,HttpServletResponse response,String pushJson){
		System.out.println("SERVLET POST HIT");

		// ENTRY POINT FOR GCM POST
		// COPYPASTE THE APNS STUFF AND ADAPT

		if (ps.pushApns(pushJson, ps.getSubsApns())) {
			// successfully pushed to APNS
			System.out.println("successfully pushed to APNS "+pushJson);
			//responseOutput = "true";
		} else {
			System.out.println("Failed to push to APNS");
		//	responseOutput = "false";
		}

	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("SERVLET POST HIT");
		String responseOutput = "";

		String requestBody = getBody(request);

		if (ps.pushApns(requestBody, ps.getSubsApns())) {
			// successfully pushed to APNS
			System.out.println("successfully pushed to APNS");
			responseOutput = "true";
		} else {
			System.out.println("Failed to push to APNS");
			responseOutput = "false";
		}

		PrintWriter pw = response.getWriter();
		pw.print(responseOutput);

		pw.close();
	}

	public static String getBody(HttpServletRequest request) throws IOException {

		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(
						inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		body = stringBuilder.toString();
		return body;
	}

}
