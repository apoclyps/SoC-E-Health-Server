package uk.co.euanmorrison.ehealth.push;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PushServer {

	ArrayList<String> subs_apns = new ArrayList<String>();
	ArrayList<String> subs_gcm = new ArrayList<String>();

	private static final String API_KEY = "4815162342"; // USED FOR API CALLS
	private static final String LOC_APNS = "C:/subs_apns.txt"; // NEEDS CHANGED
																// ON DEPLOY
	private static final String LOC_GCM = ""; // NOT USED YET

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
		System.out
				.println(">> Method call PushServer.addSubApns(String token)");
		if (subs_apns.contains(token)) {
			// already in the list. do nothing
		} else {
			try {
				subs_apns.add(token);
			} catch (Exception e) {
				// e.printStackTrace();
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
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println("ERROR: " + e.getMessage());
				return false;
			}
		}
		return true; // successfully added
	}

	public boolean sendPush(JSONObject payload) {
		System.out
				.println(">> Method call PushServer.sendPush(JSONObject payload)");
		// Push p = new Push();
		// return p.sendPush(payload);
		return true;
	}

	public boolean pushApns(String payload, ArrayList<String> recipients) {
		System.out
				.println(">> Method call PushServer.pushApns(String payload, ArrayList<String> recipients)");
		PushIOS push = new PushIOS(payload, recipients);
		return push.send();
	}

	public boolean pushGcm() {
		System.out.println(">> Method call PushServer.pushGcm()");
		return false;
		// FILL IN LATER
	}

	private boolean loadSubs() {
		System.out.println(">> Method call PushServer.loadSubs()");

		// FOR NOW: Only doing APNS. Sort this later.
		return loadSubsApns();
	}

	private boolean loadSubsApns() {
		System.out.println(">> Method call PushServer.loadSubsApns()");

		String fileName = LOC_APNS;
		
		String line = null;
		System.out.println(fileName);

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				// System.out.println(line);
				subs_apns.add(line);
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
			return false;
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			return false;
		}

		System.out.println("opened file successfully");
		return true;
	}

	private boolean loadSubsGcm() {
		System.out.println(">> Method call PushServer.loadSubsGcm()");

		return true;
	}

	public boolean saveSubs() {
		System.out.println(">> Method call PushServer.saveSubs()");

		// NEEDS UPDATED TO INCLUDE GCM
		return saveSubsApns();
	}

	private boolean saveSubsApns() {
		System.out.println(">> Method call PushServer.saveSubsApns()");

		String fileName = LOC_APNS;

		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(fileName);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			for (int i = 0; i < subs_apns.size(); i++) {
				bufferedWriter.write(subs_apns.get(i));
				bufferedWriter.newLine();
			}

			// Always close files.
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
			System.out.println("ERROR: " + ex.getMessage());
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

	public JSONObject testJson() {
		// from
		// http://www.mkyong.com/java/json-simple-example-read-and-write-json/
		JSONObject obj = new JSONObject();
		obj.put("year", "year1");
		obj.put("title", "this is an example post title");

		return obj;
	}

}
