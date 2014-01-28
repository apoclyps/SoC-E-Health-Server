package uk.co.euanmorrison.ehealth.push;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

public class PushIOS {
	
	private JSONObject messageText;
	private ArrayList<String> recipients;

	public PushIOS(JSONObject itemToBeSent, ArrayList<String> recipients) {
		this.messageText = itemToBeSent;
		this.recipients = recipients;
	}
	
	@SuppressWarnings("unused")
	public boolean send() {
		try {
			System.out.println("PUSH >> Attempting to push to iOS: "+this.messageText);

			ApnsService service =
				    APNS.newService()
				    .withCert("../E-Health Server/resources/Certificates.p12", "apnsCertificateForEuan")
				    .withSandboxDestination()
				    .build();

			String payload = APNS.newPayload().alertBody("Test for Toby!").build();

			for(int i=0; i<this.recipients.size(); i++) {
				//String token = "e278a071b803c1d5cf324342871a4fc8f6f92c99b172c95008d6fee8cc5c931f";
				//service.push(token, payload);
				try {
					service.push(this.recipients.get(i),payload);
					System.out.println("Pushed item "+i+" successfully!");
				}
				catch(Exception e) {
					System.out.println("Error on item "+i+": " + e.getMessage());
				}
			}
		}
		catch (Exception e) {
			System.out.println("PUSH >> Error: " + e.getMessage());
			return false;
		}
		return true;
	}
	
}
