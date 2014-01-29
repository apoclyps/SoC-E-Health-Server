package uk.co.ehealth.storage.mysql;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;

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
		insertItem(rssItem);
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
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<RSSItem> selectItems() {
		// TODO Auto-generated method stub
		try {
			return connection.selectItem();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ArrayList<RSSItem> selectItemsFromYear(String yearID) {
		// TODO Auto-generated method stub
		try {
			//return connection.selectItem();
			return connection.selectItemFromYear(yearID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
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
