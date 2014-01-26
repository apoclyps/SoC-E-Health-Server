package uk.co.euanmorrison.ehealth.push;

// JSON object Libraries
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


// iOS push Libraries
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

public class Push {

	public static void main(String[] args) {

		// test out JSON object maker thing
		JSONObject myObj = testJson();
		
		// call POST TO ANDROID method
		int result_android = post_android(myObj);
		System.out.println("PUSH >> Android status: "+result_android);
		
		// call POST TO iOS method
		int result_ios = post_iOS(myObj);
		System.out.println("PUSH >> iOS status: "+result_ios);
	}
	
	public static int post_iOS(JSONObject itemToBeSent) {
		// http://stackoverflow.com/questions/9357632/how-do-you-set-up-javapns-push-notifications-for-ios
		// https://github.com/notnoop/java-apns/wiki/installation
		
		try {
			System.out.println("PUSH >> Attempting to push to iOS: "+itemToBeSent);
			// set up the connection
			ApnsService service =
				    APNS.newService()
				    // next line's arguments: (.PEM file location , password)
				    .withCert("../E-Health Server/resources/APNS.pem", "apnsCertificateForEuan")
				    .withSandboxDestination()
				    .build();
			
			// create and send the message
			String payload = APNS.newPayload().alertBody("Test for Toby!").build();
			String token = "___"; // what is this?
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
			System.out.println("PUSH >> Attempting to push to Android: "+itemToBeSent);
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
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
