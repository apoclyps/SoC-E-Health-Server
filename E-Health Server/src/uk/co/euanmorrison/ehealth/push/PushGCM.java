package uk.co.euanmorrison.ehealth.push;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PushGCM {

	private String response;
	private final String GCM_AUTH = "key=AIzaSyDRHjCe_wWhxR1hf6_-93wb-imobSYPRJg";					// NEEDED FOR GCM PUSH. Comes from Yolina.
	private final String GCM_URL = "https://android.googleapis.com/gcm/send";						// Where POST goes to
	private final String GCM_CONTENT = "application/x-www-form-urlencoded;charset=UTF-8";
			// Content-Type: application/json for JSON; application/x-www-form-urlencoded;charset=UTF-8 for plain text.
	private String payload = "";
	private JSONObject payloadJson;
	private String[] ids;
	
	public PushGCM(String payload, ArrayList<String> recipients) {
		
		this.payload = payload;
		this.ids = recipients.toArray(new String[recipients.size()]);
		
		try {
			this.payloadJson = buildBody();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			this.response = this.send();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("# Attempted POST. Result:\n" + this.getResponse() + "\n");
	}
	
	public String send() throws IOException {
		 
		URL obj;
		obj = new URL(GCM_URL);
		
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		// Required headers for GCM POST
		con.setRequestProperty("Content-Type", GCM_CONTENT);
		con.setRequestProperty("Authorization", GCM_AUTH);
 
		//String urlParameters = "";
		String urlParameters = this.payloadJson.toString();
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		//wr.writeBytes(urlParameters);
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		
		//int responseCode = con.getResponseCode();
 
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
	
	private JSONObject buildBody() throws JSONException {
		JSONObject payloadJson = new JSONObject(this.payload);
		
		JSONObject body = new JSONObject();
		JSONObject data = new JSONObject();
		
		data.put("year", payloadJson.get("year"));
		data.put("title", payloadJson.get("title"));
		
		JSONArray registration_ids = new JSONArray(this.ids);
		
		body.put("data", data);
		body.put("registration_ids", registration_ids);
		
		return body;
	}
	
}
