package uk.co.euanmorrison.ehealth.push;

import java.io.DataOutputStream;
import java.net.URL;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PushGCM {

	private final String GCM_AUTH = "key=AIzaSyDRHjCe_wWhxR1hf6_-93wb-imobSYPRJg";					// NEEDED FOR GCM PUSH. Comes from Yolina.
	private final String GCM_URL = "https://android.googleapis.com/gcm/send";						// Where POST goes to
	private final String GCM_CONTENT = "application/x-www-form-urlencoded;charset=UTF-8";
			// Content-Type: application/json for JSON; application/x-www-form-urlencoded;charset=UTF-8 for plain text.
	private String payload = "";
	private JSONObject payloadJson = new JSONObject();
	private String[] ids;
	
	public PushGCM(String payload, ArrayList<String> recipients) {
		
		System.out.println("LOADED GCM METHOD");
		
		this.payload = payload;
		this.ids = recipients.toArray(new String[recipients.size()]);
		
		try {
			this.payloadJson = buildBody();
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("broken");
		} 
		
	}
	
	public boolean send() {
		try {
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
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();
			
			if(responseCode==200) {
				// success, do nothing
				System.out.println("RESPONSE CODE 200: SUCCESS (GCM)");
			}
			else {
				System.out.println("Error on Push: "+responseCode);
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private JSONObject buildBody() throws JSONException {
		System.out.println("payload: "+this.payload);

		
		String bigUglyString = "{ \"data\": " + payloadJson.toString() + ", \"registration_ids\": [";
		
		for(int i=0; i<ids.length; i++) {
			if(i==0) {
				bigUglyString.concat("\"" + ids[i] + "\"");
			}
			
			else {
				bigUglyString.concat(",\"" + ids[i] + "\"");
			}
		}
		
		bigUglyString.concat("]}");
		
		System.out.println("biguglystring: " +bigUglyString);
		
		JSONObject body = new JSONObject(bigUglyString);
		
		return body;
	}
	
}
