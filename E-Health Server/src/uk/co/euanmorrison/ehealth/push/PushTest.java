package uk.co.euanmorrison.ehealth.push;

public class PushTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PushFacade pf  = new PushFacade();	
		pf.broadcast("some text to push at your phone thing");
		
	}

}
