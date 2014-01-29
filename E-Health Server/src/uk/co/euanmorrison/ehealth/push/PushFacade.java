package uk.co.euanmorrison.ehealth.push;

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
	
	public void writeSubscriptions() {
		this.ps.serverStop();
	}
	
	protected void broadcast(String pushJson) {				
		System.out.println("Broadcasting");
		try{
			if (ps.pushApns(pushJson, ps.getSubsApns())) {	// CHANGE ME TO PUSH TO BOTH
				System.out.println("successfully pushed to APNS "+pushJson);
			} else {
				System.out.println("Failed to push to APNS");
			}
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		ps.saveSubs();
	}
	
}
