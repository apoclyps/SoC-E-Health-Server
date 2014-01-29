package uk.co.kyleharrison.ehealth.model.pojo;

public class PhoneID {

	private String phoneID;
	
	public PhoneID()
	{
		super();
		phoneID = null;
	}

	public PhoneID(String newPhoneID)
	{
		super();
		phoneID = newPhoneID;
	}
	
	public String getPhoneID() {
		return phoneID;
	}

	public void setPhoneID(String phoneID) {
		this.phoneID = phoneID;
	}
	
}
