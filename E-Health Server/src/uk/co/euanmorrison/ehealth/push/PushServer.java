package uk.co.euanmorrison.ehealth.push;

import java.io.IOException;
import java.util.ArrayList;

public class PushServer {

	ArrayList<String> subs_apns = new ArrayList<String>();
	ArrayList<String> subs_gcm = new ArrayList<String>();

	public PushServer() {
		System.out.println(">> PushServer Constructor called");
		serverSetup(); // on instantiation, set up server.

		for (int i = 0; i < subs_apns.size(); i++) {
			System.out.println(">> subs_apns(" + i + "): " + subs_apns.get(i));
		}
		saveSubs();
	}

	private boolean serverSetup() {
		System.out.println(">> Method call PushServer.serverSetup()");
		if (loadSubs()) {
			// success
		} else {
			return false;
		}
		return true;
	}

	public boolean serverStop() {
		System.out.println(">> Method call PushServer.serverStop()");
		if (saveSubs()) {
			// success
		} else {
			return false;
		}
		return true;
	}

	public boolean addSubApns(String token) {
		System.out.println(">> Method call PushServer.addSubApns(String token)");
		if (subs_apns.contains(token)) {
			// already in the list. do nothing
		} else {
			try {
				subs_apns.add(token);
				saveSubsApns();
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
				return false;
			}
		}
		return true; // successfully added
	}

	public boolean addSubGcm(String token) {
		System.out.println(">> Method call PushServer.addSubGcm(String token)");
		if (subs_gcm.contains(token)) {
			// already in the list. do nothing
		} else {
			try {
				subs_gcm.add(token);
				saveSubsApns();
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
				return false;
			}
		}
		return true; // successfully added
	}

	public boolean sendPush(String payload, ArrayList<String> recipients_ios, ArrayList<String> recipients_android) {
		System.out.println(">> Method call PushServer.sendPush(String payload, ArrayList<String> recipients_ios, ArrayList<String> recipients_android)");
		
		// HANDLE POTENTIAL EXCEPTIONS WITH EACH SERVICE
		if( pushGcm(payload, recipients_ios) && pushApns(payload,recipients_android ) ) {
			// yay, everyone is happy!
		}
		else {
			return false;
		}
		
		return true;
	}

	public boolean pushApns(String payload, ArrayList<String> recipients) {
		System.out.println(">> Method call PushServer.pushApns(String payload, ArrayList<String> recipients)");
		PushIOS push = new PushIOS(payload, recipients);
		return push.send();
	}

	public boolean pushGcm(String payload, ArrayList<String> recipients) {
		System.out.println(">> Method call PushServer.pushGcm(String payload, ArrayList<String> recipients)");
		PushGCM push = new PushGCM(payload, recipients);
		try {
			push.send();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

	private boolean loadSubs() {
		System.out.println(">> Method call PushServer.loadSubs()");

		if( loadSubsApns() && loadSubsGcm() ) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean loadSubsApns() {
		System.out.println(">> Method call PushServer.loadSubsApns()");

		// set up instance of facadey thing for DB
		// open the connection

		try {
			// this.subs_apns = call method to GET all devices in APNS (returns Arraylist<String>)
			// CLOSE DB CONNECTION
		}
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}

		System.out.println("Opened file successfully");
		return true;
	}

	private boolean loadSubsGcm() {
		System.out.println(">> Method call PushServer.loadSubsGcm()");

		return true;
	}

	public boolean saveSubs() {
		System.out.println(">> Method call PushServer.saveSubs()");

		if( saveSubsApns() && saveSubsGcm() ) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean saveSubsApns() {
		System.out.println(">> Method call PushServer.saveSubsApns()");

		try {
			
		}
		catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return false;
		}
		System.out.println("Wrote to file successfully");
		return true;
	}

	private boolean saveSubsGcm() {
		System.out.println(">> Method call PushServer.saveSubsGcm()");
		return true;
	}

	public ArrayList<String> getSubsApns() {
		System.out.println(">> Method call getSubsApns()");
		return subs_apns;
	}

	public ArrayList<String> getSubsGcm() {
		System.out.println(">> Method call getSubsGcm()");
		return subs_gcm;
	}

}
