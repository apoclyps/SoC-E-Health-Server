package uk.co.kyleharrison.ehealth.controller.rest;

import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.service.xml.XMLFacade;

@WebListener
public class RequestControllerContextListener implements ServletContextListener {

	private Thread myThread = null;
	private XMLFacade xmlFacade;
	private int [] years = {1,2,3,4,5};
	private long delayBetweenRequests =10000;
	private long waitTime = 60000;
	
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    	System.out.println("ServletContextListener started for retrieving xml feed");
    	
    	new Thread() {
    		  public void run() {
    			
    			for(int year : years){
    				xmlFacade = new XMLFacade();
        			xmlFacade.setUrl("https://mbchb.dundee.ac.uk/category/year"+year+"/feed/");
        		    RSSChannel rc = xmlFacade.DeconstructXMLToPojo();
        	
        		    for(RSSItem ri : rc.getItem_list()){
        		    	ri.setYear(year);
        		    	System.out.println(rc.getTitle());
        		    }
        		    try {
    					this.sleep(delayBetweenRequests);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
    			}
    		    try {
					this.sleep(waitTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		   // run();
    		  }
    		}.start();
    }
    
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
	}
}
