package uk.co.kyleharrison.ehealth.controller.rest;

import java.util.ArrayList;
import java.util.Date;

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

import uk.co.ehealth.storage.mysql.MySQLFacade;
import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.service.xml.XMLFacade;

@WebListener
public class RequestControllerContextListener implements ServletContextListener {

	private Thread myThread = null;
	private XMLFacade xmlFacade;
	private int[] years = { 1, 2, 3, 4, 5 };
	private long delayBetweenRequests = 10000;
	private long waitTime = 60000;
	private MySQLFacade mysqlFacade;
	protected RSSChannel rc;

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("ServletContextListener started for retrieving xml feed");

		mysqlFacade = new MySQLFacade();
		
		new Thread() {
			public void run() {

				// Selecting records from database for comparison
				
				Date date = new Date("Tue Jan 28 15:41:29 GMT 2014");
				System.out.println(date.toGMTString());
				
			//	ValidateRSSFeed();

				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//run();
			}
		}.start();
	}

	protected void ValidateRSSFeed() {
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
			if (rssItemsPersistent.size() > 0) {
				for (int i = 0; i < rc.getItem_list().size(); i++) {
					// compare each recordd
					try {
						if (!(rc.getItem_list().get(i).getTitle()
								.equals(rssItemsPersistent.get(i).getTitle()))) {
							count++;
							/*System.out.println("DIFF RC model : "
									+ rc.getItem_list().get(i).getTitle());
							System.out.println("DIFF ST model : "
									+ rssItemsPersistent.get(i).getTitle());*/
						} else {
							/*System.out.println("EQL : RC model : "
									+ rc.getItem_list().get(i).getTitle()
									+ "ST model : "
									+ rssItemsPersistent.get(i).getTitle());*/
						}
					} catch (IndexOutOfBoundsException e) {
						System.out
								.println("Exception in Request Controller Service");
						e.printStackTrace();
					}
				}
				System.out.println("RC Service : Inserts Required : " + count);
			} else {
				System.out.println("Update Model with full RSS RC");
			}

			// Wait time between requests
			try {
				Thread.sleep(delayBetweenRequests);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void CreateMBCHBModel(int year) {
		// TODO Auto-generated method stub
		xmlFacade = new XMLFacade();
		xmlFacade.setUrl("https://mbchb.dundee.ac.uk/category/year" + year
				+ "/feed/");
		this.rc = xmlFacade.DeconstructXMLToPojo();

		// Setting the year manually for each RSSItem
		for (RSSItem ri : rc.getItem_list()) {
			ri.setYear(year);
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
	}
}
