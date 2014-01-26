package uk.co.euanmorrison.ehealth.push;

import org.json.simple.JSONObject;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

public class PushIOS {

	public PushIOS() {
		
	}
	
	private int post_iOS(JSONObject itemToBeSent) {
		// http://stackoverflow.com/questions/9357632/how-do-you-set-up-javapns-push-notifications-for-ios
		// https://github.com/notnoop/java-apns/wiki/installation
		
		try {
			System.out.println("PUSH >> Attempting to push to iOS: "+itemToBeSent);
			// set up the connection
			ApnsService service =
				    APNS.newService()
				    // next line's arguments: (.p12 file location , password)
				    .withCert("../E-Health Server/resources/Certificates.p12", "apnsCertificateForEuan")
				    .withSandboxDestination()
				    .build();

			// create and send the message
			String payload = APNS.newPayload().alertBody("Test for Toby!").build();
			String token = "___"; // WORK DAMNIT
			service.push(token, payload);
		}
		catch (Exception e) {
			//e.printStackTrace();
			System.out.println("PUSH >> Error: "+e.getMessage());
			return 0;
		}
		
		return 1;
	}
	
}
