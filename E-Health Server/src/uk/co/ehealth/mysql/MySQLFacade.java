package uk.co.ehealth.mysql;
import java.net.MalformedURLException;
import java.sql.SQLException;

import org.json.JSONException;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;


public class MySQLFacade implements MySQLInterface {
	
	private MySQLDAO connection;
	
	public MySQLFacade() {
	super();
	this.setConnection(new MySQLDAO());
	}

	@Override
	public boolean insertChannel(RSSChannel rssChannel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertItem(RSSItem rssItem) {
		// TODO Auto-generated method stub
		/*
		try {
			connection.insertItem(rssItem);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return false;
	}

	@Override
	public RSSChannel selectChannel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RSSItem selectItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RSSChannel[] selectChannels() {
		// TODO Auto-generated method stub
		try {
			connection.selectChannel();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public RSSItem[] selectItems() {
		// TODO Auto-generated method stub
		try {
			connection.selectItem();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public MySQLDAO getConnection() {
		return connection;
	}

	public void setConnection(MySQLDAO connection) {
		this.connection = connection;
	}
	
}
