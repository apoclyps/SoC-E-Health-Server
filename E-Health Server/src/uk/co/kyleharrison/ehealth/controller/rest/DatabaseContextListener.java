package uk.co.kyleharrison.ehealth.controller.rest;

import java.util.ArrayList;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import uk.co.ehealth.storage.mysql.MySQLFacade;
import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.service.xml.XMLFacade;

@WebListener
public class DatabaseContextListener implements ServletContextListener {

	private Thread myThread = null;
	private XMLFacade xmlFacade;
	private int[] years = { 1, 2, 3, 4, 5 };
	private long delayBetweenRequests = 10000;
	private long waitTime = 60000;
	private MySQLFacade mysqlFacade = new MySQLFacade();
	protected RSSChannel rc;
	
    public void contextInitialized(ServletContextEvent arg0) {
    	
    	System.out.println("Database context listener");
    	new Thread() {
			public void run() {
				
				InsertRSSFeed();
				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			//	run();
			}
		}.start();
    }

    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
    
    protected void InsertRSSFeed() {
		// TODO Auto-generated method stub
		// Creating Model of RSS items for last 10 posts.
		for (int year : years) {
			//Calls XML Facade and creates RSSChannel based on year provided.
			CreateMBCHBModel(year);

			

			// Update storage model
			ArrayList<RSSItem> rssItemsPersistent = mysqlFacade
					.selectItemsFromYear(Integer.toString(year));

			int count = 0;
			// Compare model vs storage
			System.out.println("For year " + year);
			
			
			System.out.println("Size = "+rssItemsPersistent.size());
			
			
			if (rssItemsPersistent.size() > 0) {
				for (int i = 0; i < rc.getItem_list().size(); i++) {
					// compare each recordd
					try {
						if (!(rc.getItem_list().get(i).getTitle()
								.equals(rssItemsPersistent.get(i).getTitle()))||rssItemsPersistent.get(i).equals(null)) {
							count++;
								mysqlFacade.insertItem(rc.getItem_list().get(i));
								System.out.println("Inserted record : "+rc.getItem_list().get(i).getTitle());
						}
					} catch (IndexOutOfBoundsException e) {
						System.out.println("Exception in Request Controller Service");
						e.printStackTrace();
					}
				}
				System.out.println("RC Service : Inserts Required : " + count);
			} else {
				//System.out.println("Update Model with full RSS RC");
				for(RSSItem ri : rc.getItem_list()){
					mysqlFacade.insertItem(ri);
				}
			}

			// Wait time between requests
			try {
				Thread.sleep(delayBetweenRequests);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mysqlFacade.closeConnection();
		}

	}

	private void CreateMBCHBModel(int year) {
		// TODO Auto-generated method stub
		xmlFacade = new XMLFacade();
		xmlFacade.setUrl("https://mbchb.dundee.ac.uk/category/year" + year
				+ "/feed/");
		this.rc = new RSSChannel();
		this.rc = xmlFacade.DeconstructXMLToPojo();

		// Setting the year manually for each RSSItem
		for (RSSItem ri : rc.getItem_list()) {
			ri.setYear(year);
		}

	}
	
}
