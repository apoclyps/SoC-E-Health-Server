package uk.co.euanmorrison.ehealth.push;

import java.util.ArrayList;

import uk.co.ehealth.storage.mysql.MySQLFacade;

public class PushServer {

	ArrayList<String> subs_apns = new ArrayList<String>();
	ArrayList<String> subs_gcm = new ArrayList<String>();
	private MySQLFacade sql;
	private final String API_KEY = "040815162342";

	public PushServer() {
		System.out.println(">> PushServer Constructor called");
		this.sql =  new MySQLFacade();
		serverSetup(); // on instantiation, set up server.
	}

	private boolean serverSetup() {
		System.out.println(">> Method call PushServer.serverSetup()");
		if (loadSubs()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean serverStop() {
		return true;
	}

	public boolean addSubApns(String token) {
		System.out.println(">> Method call PushServer.addSubApns(String token)");
		if (this.subs_apns.contains(token)) {
			// already in the list. do nothing
		} else {
			try {
				this.subs_apns.add(token);
				saveSubsApns(token);
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
				return false;
			}
		}
		return true; // successfully added
	}

	public boolean addSubGcm(String token) {
		System.out.println(">> Method call PushServer.addSubGcm(String token)");
		if (this.subs_gcm.contains(token)) {
			// already in the list. do nothing
		} else {
			try {
				this.subs_gcm.add(token);
				saveSubsGcm(token);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true; // successfully added
	}

	public boolean sendPush(String payload, ArrayList<String> recipients_ios, ArrayList<String> recipients_android) {
		System.out.println(">> Method call PushServer.sendPush(String payload, ArrayList<String> recipients_ios, ArrayList<String> recipients_android)");
		
		// HANDLE POTENTIAL EXCEPTIONS WITH EACH SERVICE
		if( pushGcm(payload, recipients_ios) && pushApns(payload,recipients_android ) ) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean pushApns(String payload, ArrayList<String> recipients) {
		System.out.println(">> Method call PushServer.pushApns(String payload, ArrayList<String> recipients)");
		PushIOS push = new PushIOS(payload, recipients);

		return push.send();
	}

	public boolean pushGcm(String payload, ArrayList<String> recipients) {
		System.out.println(">> Method call PushServer.pushGcm(String payload, ArrayList<String> recipients)");
		
		System.out.println("ABOUT TO PUSH TO GCM: "+payload.toString());
		PushGCM push = new PushGCM(payload, recipients);
		
		return push.send();
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

		try {
			this.subs_apns = sql.getPhoneIDs("ios");
		}
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}

		System.out.println("loaded apns subs successfully");
		return true;
	}

	private boolean loadSubsGcm() {
		System.out.println(">> Method call PushServer.loadSubsGcm()");

		try {
			this.subs_gcm = sql.getPhoneIDs("android");
		}
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}

		System.out.println("loaded apns subs successfully");
		return true;
	}

	private boolean saveSubsApns(String key) {
		System.out.println(">> Method call PushServer.saveSubsApns(String key)");

		try {
			sql.insertIOSKey(key);
		}
		catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return false;
		}
		System.out.println("Wrote to DB successfully");
		return true;
	}

	private boolean saveSubsGcm(String key) {
		System.out.println(">> Method call PushServer.saveSubsGcm(String key)");
		
		try {
			sql.insertAndroidKey(key);
		}
		catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return false;
		}
		System.out.println("Wrote to DB successfully");
		return true;
	}
	
	public boolean deleteSubApns(String key) {
		System.out.println(">> Method call PushServer.deleteSubApns(String key)");
		
		try {
			MySQLFacade sql = new MySQLFacade();
			sql.deleteIOS(key);
			this.subs_apns.remove(key);
		}
		catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return false;
		}
		System.out.println("Removed Successfully");
		return true;
	}
	
	public boolean deleteSubGcm(String key) {
		System.out.println(">> Method call PushServer.deleteSubApns(String key)");
		
		try {
			MySQLFacade sql = new MySQLFacade();
			sql.deleteAndroid(key);
			this.subs_gcm.remove(key);
		}
		catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return false;
		}
		System.out.println("Removed Successfully");
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

	public boolean checkApiKey(String key) {
		return key.equals(API_KEY);
	}
}
