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
	private final String GCM_AUTH = "";	// NEEDED FOR GCM PUSH. Comes from Yolina.
	private final String GCM_URL = "https://android.googleapis.com/gcm/send";
	
	public PushGCM(String payload, int[] ids) throws Exception {
		Date timestamp = new Date();
		
		this.obj = this.setJSON("test payload", timestamp, ids);
		
		this.response = this.sendPost("https://selfsolve.apple.com/wcResults.do", this.obj);
		
		// prints contents of JSON object to console
		System.out.println("# Created JSON object:\n" + this.getJSON() + "\n");
		System.out.println("# Attempted POST. Result:\n" + this.getResponse() + "\n");
	}
	
	private JSONObject getJSON() {
		return this.obj;
	}
	
	private JSONObject setJSON(String payload, Date timestamp, int[] ids) {
		JSONObject obj = new JSONObject();		// the JSON we will return
		JSONObject myData = new JSONObject();	// the payload
		JSONArray regIds = new JSONArray();		// the devices who will receive it
		
		myData.put("payload" , payload);
		myData.put("time" , timestamp);
		
		for(int i=0; i<ids.length; i++) {
			regIds.add(ids[i]);
		}
		
		obj.put("data" , myData );
		obj.put("registration_ids" , regIds);
		
		return obj;
	}
	
	private String sendPost(String url, JSONObject jsonObj) throws Exception {
		 
		//String url = "https://selfsolve.apple.com/wcResults.do";
		//URL obj = new URL(url);
		URL obj = new URL(GCM_URL);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		// Required headers for GCM POST
		con.setRequestProperty("Content-Type", "application/json"); // for json
		con.setRequestProperty("Authorization", GCM_AUTH);
 
		//String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
		String urlParameters = "";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		// System.out.println("headers : " + con.getRequestProperty("User-Agent") );
		//System.out.println("Response Code : " + responseCode);
 
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
