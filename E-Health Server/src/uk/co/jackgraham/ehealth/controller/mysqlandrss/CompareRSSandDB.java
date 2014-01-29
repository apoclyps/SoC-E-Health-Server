package uk.co.jackgraham.ehealth.controller.mysqlandrss;

import java.util.ArrayList;

import uk.co.ehealth.storage.mysql.MySQLFacade;
import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.service.xml.XMLFacade;


public class CompareRSSandDB {

	private XMLFacade xml;
	private MySQLFacade sql;
	private RSSChannel rc;
	private ArrayList<RSSItem> rssItemList;
	private ArrayList<RSSItem> sqlItemList;
	private String yearID;
	
	public CompareRSSandDB()
	{
		super();
	}
	
	public CompareRSSandDB(String url)
	{
		xml = new XMLFacade(url);
		sql = new MySQLFacade();
		
		String[] pathComponents = url.split("/");
		yearID = pathComponents[3];
	}
	
	public void getRSS()
	{
		rc = xml.DeconstructXMLToPojo();
		setRssItemList(rc.getItem_list());
	}
	
	public void getDBdata()
	{
		setSqlItemList(sql.selectItemsFromYear(yearID));
	}

	public ArrayList<RSSItem> getRssItemList() {
		return rssItemList;
	}

	public void setRssItemList(ArrayList<RSSItem> rssItemList) {
		this.rssItemList = rssItemList;
	}

	public ArrayList<RSSItem> getSqlItemList() {
		return sqlItemList;
	}

	public void setSqlItemList(ArrayList<RSSItem> sqlItemList) {
		this.sqlItemList = sqlItemList;
	}
	
	
}
