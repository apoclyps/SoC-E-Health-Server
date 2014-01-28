package uk.co.euanmorrison.ehealth.push;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PushTester {

	public static void main(String[] args) {
		System.out.println(">> PushTester MAIN method running.");
		// use me to deploy pusher server in testing
		// in deployment, add this into main server launch sequence
		
		// instantiates and sets up PushServer.
		// leave this running to handle subscriptions and notifications
		// HOW do we leave it running? currently terminates
		// or do we need to just make an instance whenever the REST is hit?
		PushServer pushServer = new PushServer();
		
		// do we need to save on stopping the server?
		// if it saves every time a new sub is received, that avoids it
		// kinda impractical though. hmm.
		
		// THIS WILL BE CALLED BY RESTFUL STUFF, presumably?
		pushServer.addSubApns(fakeToken());
		ArrayList<String> subs = pushServer.getSubsApns();
		
		for(int i = 0; i<subs.size(); i++) {
			System.out.println(">> subs(" + i + "): " + subs.get(i));
		}
		
		// test saving subs list
		pushServer.saveSubs();
		
		// test push
		pushServer.pushApns(testJson(),subs);
	}
	
	public static JSONObject testJson() {
		// from http://www.mkyong.com/java/json-simple-example-read-and-write-json/
		JSONObject obj = new JSONObject();
		obj.put("year", "yeartext");
		obj.put("category", "categorytext");
		obj.put("title", "titletext");
	 
		JSONArray list = new JSONArray();
		list.add("msg1");
		list.add("msg2");
		list.add("msg3");
	 
		obj.put("messages", list);
		
		return obj;
	}
	
	public static String fakeToken() {
		return "e278a071b803c1d5cf324342871a4fc8f6f92c99b172c95008d6fee8cc5c931f";
	}
	
}
