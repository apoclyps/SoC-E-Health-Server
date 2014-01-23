package uk.co.euanmorrison.ehealth.push;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


// JSON object Libraries
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


// iOS push Libraries
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

public class push {

	public static void main(String[] args) {

		// test out JSON object maker thing
		JSONObject myObj = testJson();
		
		//String testJson = "test notification";
		//System.out.println(testJson);
		
		int result = -1;
		
		// POST TO ANDROID
		//result = post_android(myObj);
		
		
		
		//POST TO iOS
		result = post_iOS(myObj);
		
		System.out.println(result);
	}
	
	public static int post_iOS(JSONObject itemToBeSent) {
		// http://stackoverflow.com/questions/9357632/how-do-you-set-up-javapns-push-notifications-for-ios
		// https://github.com/notnoop/java-apns/wiki/installation
		
		try {
			// set up the connection
			ApnsService service =
				    APNS.newService()
				    // next line's arguments: (.PEM file location , password)
				    .withCert("/E-Health Server/resources/APNS.pem", "apnsCertificateForEuan")
				    .withSandboxDestination()
				    .build();
			
			// create and send the message
			String payload = APNS.newPayload().alertBody("Test for Toby!").build();
			String token = "fedfbcfb....";
			service.push(token, payload);
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	public static int post_android(JSONObject itemToBeSent) {
		try {
			// ANDROID STUFF
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	public static void someSortOfPost() throws IOException {
		String urlParameters = "param1=a&param2=b&param3=c";
		String request = "http://example.com/index.php";
		URL url = new URL(request); 
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setInstanceFollowRedirects(false); 
		connection.setRequestMethod("POST"); 
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connection.setRequestProperty("charset", "utf-8");
		connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
		connection.setUseCaches (false);

		//DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
		//wr.writeBytes(urlParameters);
		//wr.flush();
		//wr.close();
		//connection.disconnect();
	}
	
	public static JSONObject testJson() {
		// from http://www.mkyong.com/java/json-simple-example-read-and-write-json/
		JSONObject obj = new JSONObject();
		obj.put("key1", "value1");
		obj.put("key2", "value2");
		//obj.put("age", new Integer(100));
	 
		JSONArray list = new JSONArray();
		list.add("msg1");
		list.add("msg2");
		list.add("msg3");
	 
		obj.put("messages", list);
		
		return obj;
		
		//System.out.print(obj);
	}

}
