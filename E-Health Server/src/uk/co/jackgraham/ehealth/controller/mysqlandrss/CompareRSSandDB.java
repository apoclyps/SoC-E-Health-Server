package uk.co.jackgraham.ehealth.controller.mysqlandrss;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.ehealth.storage.mysql.MySQLFacade;
import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.service.jackson.model.JSONItem;
import uk.co.kyleharrison.ehealth.service.xml.XMLFacade;

public class CompareRSSandDB {

	private XMLFacade xml;
	private MySQLFacade sql;
	private RSSChannel rc;
	private ArrayList<RSSItem> rssItemList;
	private ArrayList<RSSItem> sqlItemList;
	private String yearID;

	public CompareRSSandDB() {
		super();
	}

	// Constructor
	public CompareRSSandDB(String url) {// sets up Facades and splits url to get
										// yearID
		xml = new XMLFacade(url);
		sql = new MySQLFacade();

		String[] pathComponents = url.split("/");
		yearID = pathComponents[3];
	}

	// Getters and Setters-------------------------------------------------

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

	// Get ArrayList from RSS feed by yearID
	public void getRSS() {
		rc = xml.DeconstructXMLToPojo();
		setRssItemList(rc.getItem_list());
	}

	// Get ArrayList from DB by yearID
	public void getDBdata() {
		setSqlItemList(sql.selectItemsFromYear(yearID));
	}

	// Check RSS and DB Array Lists
	public void compareRSStoSQL() throws JSONException {
		RSSItem rss;
		RSSItem sqlItem;
		rss = rssItemList.get(0);
		sqlItem = sqlItemList.get(0);
		JSONItem jsonItem = new JSONItem();

		if (rss.getTitle().equals(sqlItem.getTitle())) {
			// push to app
			JSONObject[] newJSON = new JSONObject[rssItemList.size()];

			for (int i = 0; i < rssItemList.size(); i++) {
				newJSON[i] = jsonItem.writeToJson(rssItemList.get(i));
			}
			// newJSON now ready to sent to app
		} else {
			// push to sql then app
			for (int x = 0; x < rssItemList.size(); x++) {
				sql.insertItem(rssItemList.get(x));
			}
			ArrayList<RSSItem> newList = sql.selectItemsFromYear(yearID);
			JSONObject[] newJSON = new JSONObject[newList.size()];

			for (int i = 0; i < newList.size(); i++) {
				newJSON[i] = jsonItem.writeToJson(newList.get(i));
			}
			// newJSON now ready to be sent to app
		}
	}

}
