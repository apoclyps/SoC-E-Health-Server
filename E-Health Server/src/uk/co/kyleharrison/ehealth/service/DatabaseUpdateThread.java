package uk.co.kyleharrison.ehealth.service;

import java.util.ArrayList;

import uk.co.ehealth.storage.mysql.MySQLFacade;
import uk.co.euanmorrison.ehealth.push.PushFacade;
import uk.co.kyleharrison.ehealth.model.inmemory.rss.RSSChannel;
import uk.co.kyleharrison.ehealth.model.inmemory.rss.RSSItem;
import uk.co.kyleharrison.ehealth.model.xml.XMLFacade;

public class DatabaseUpdateThread extends Thread {

	private long delayBetweenRequests;
	private long waitTime;
	private XMLFacade xmlFacade;
	private int[] years = { 1, 2, 3, 4, 5 };
	private MySQLFacade mysqlFacade = new MySQLFacade();
	protected RSSChannel rc;
	private PushFacade pf;

	public DatabaseUpdateThread() {
		this.setDelayBetweenRequests(1000);
		this.waitTime = 60000*3;
		pf = new PushFacade();
	}

	public DatabaseUpdateThread(long delayBetweenRequests, long waitTime) {
		this.setDelayBetweenRequests(delayBetweenRequests);
		this.setWaitTime(waitTime);
		pf = new PushFacade();
	}

	@Override
	public void run() {
		InsertRSSFeed();
		try {
			DatabaseUpdateThread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		run();
	}

	public long getDelayBetweenRequests() {
		return delayBetweenRequests;
	}

	public void setDelayBetweenRequests(long delayBetweenRequests) {
		this.delayBetweenRequests = delayBetweenRequests;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

	protected void InsertRSSFeed() {
		// Creating Model of RSS items for last 10 posts.
		for (int year : years) {
			// Calls XML Facade and creates RSSChannel based on year provided.
			CreateMBCHBModel(year);
			// Update storage model
			ArrayList<RSSItem> rssItemsPersistent = mysqlFacade.selectItemsFromYear(Integer.toString(year));

			int count = 0;
			if (rssItemsPersistent.size() > 0) {

				for (int i = 0; i < rc.getItem_list().size(); i++) {
					// compare each record
					try {
						// if items from list == items from database
						boolean insert = mysqlFacade.selectItemByTitleDate(rc
								.getItem_list().get(i).getTitle(), rc
								.getItem_list().get(i).getPubDate());
						if (!insert) {

							// Check if item exists in the database and if false
							// then
							mysqlFacade.selectItemByTitleDate(
									rssItemsPersistent.get(i).getTitle(),
									rssItemsPersistent.get(i).getPubDate());

							count++;
							mysqlFacade.insertItem(rc.getItem_list().get(i));
						}
					} catch (IndexOutOfBoundsException e) {
						System.out
								.println("Exception in Request Controller Service");
						e.printStackTrace();
					}
				}
				if (count > 0) {
					System.out.println("RC Service : Inserts Required : "
							+ count + "\n");
				}
			} else {
				for (RSSItem ri : rc.getItem_list()) {
					mysqlFacade.insertItem(ri);
					try {
						// BROADCAST PUSH
						 pf.broadcast(ri.getPushJSON().toJSONString());
					} catch (Exception e) {
						System.out
								.println("Exception in Database context listener");
						e.printStackTrace();
					}
				}
			}
			// Wait time between requests
			try {
				// Thread.sleep(delayBetweenRequests);
				DatabaseUpdateThread.sleep(delayBetweenRequests);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mysqlFacade.closeConnection();
		}
	}

	private void CreateMBCHBModel(int year) {
		xmlFacade = new XMLFacade();
		xmlFacade.setUrl("https://mbchb.dundee.ac.uk/category/year" + year
				+ "/feed/");
		this.rc = new RSSChannel();
		this.rc = xmlFacade.DeconstructXMLToPojo();

		for (RSSItem ri : rc.getItem_list()) {
			ri.setYear(year);
		}
	}

}
