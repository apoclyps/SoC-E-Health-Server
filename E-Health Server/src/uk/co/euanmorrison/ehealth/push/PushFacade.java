package uk.co.euanmorrison.ehealth.push;

import java.util.ArrayList;

public class PushFacade {

	private PushServer ps;

	public PushFacade(PushServer ps) {
		super();
		this.ps = ps;
	}
	
	public PushFacade() {
		super();
		this.ps = new PushServer();
	}

	public PushServer getPs() {
		return ps;
	}

	public void setPs(PushServer ps) {
		this.ps = ps;
	}
	
	public ArrayList<String> getSubsApns() {
		return this.ps.getSubsApns();
	}
	
	public ArrayList<String> getSubsGcm() {
		return this.ps.getSubsGcm();
	}
	
	public boolean deleteSubApns(String key) {
		return this.ps.deleteSubApns(key);
	}
	
	public boolean deleteSubGcm(String key) {
		return this.ps.deleteSubGcm(key);
	}
	
	public boolean addSubApns(String key) {
		return this.ps.addSubApns(key);
	}
	
	public boolean addSubGcm(String key) {
		return this.ps.addSubGcm(key);
	}
	
	public void writeSubscriptions() {
		this.ps.serverStop();
	}
	
	public void broadcast(String pushJson) {				
		System.out.println("Broadcasting");
		
		if(pushJson=="" || pushJson==null) {
			System.out.println("Error: no text defined to push.");
			return;
		}
		
		// SEND TO APNS
		try {
			if (ps.pushApns(pushJson, ps.getSubsApns())) {
				System.out.println("Successfully pushed to APNS "+pushJson);
			} else {
				System.out.println("Failed to push to APNS");
			}
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// SEND TO GCM
		try {
			if (ps.pushGcm(pushJson, ps.getSubsGcm())) {
				System.out.println("Successfully pushed to GCM "+pushJson);
			} else {
				System.out.println("Failed to push to GCM");
			}
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
	
}
