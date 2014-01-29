package uk.co.euanmorrison.ehealth.push;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PushGCM {

	private JSONObject obj;
	private String response;
	private final String GCM_AUTH = "key=YOUR_API_KEY";							// NEEDED FOR GCM PUSH. Comes from Yolina.
	private final String GCM_URL = "https://android.googleapis.com/gcm/send";	// Where POST goes to
	private final String GCM_CONTENT = "application/x-www-form-urlencoded;charset=UTF-8";
			// Content-Type: application/json for JSON; application/x-www-form-urlencoded;charset=UTF-8 for plain text.
	
	public PushGCM(String payload, String[] ids) throws Exception {
		Date timestamp = new Date();
		
		//this.obj = this.setJSON("test payload", timestamp, ids);
		
		this.response = this.sendPost("https://selfsolve.apple.com/wcResults.do", this.obj);
		
		// prints contents of JSON object to console
		System.out.println("# Attempted POST. Result:\n" + this.getResponse() + "\n");
	}
	
	private String sendPost(String url, JSONObject jsonObj) throws Exception {
		 
		URL obj = new URL(GCM_URL);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		// Required headers for GCM POST
		con.setRequestProperty("Content-Type", GCM_CONTENT);
		con.setRequestProperty("Authorization", GCM_AUTH);
 
		String urlParameters = "";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		return response.toString();
	}
	
	private String getResponse() {
		return this.response;
	}
	
}
