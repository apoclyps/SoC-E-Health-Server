package uk.co.ehealth.storage.mysql;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import uk.co.kyleharrison.ehealth.model.flashcards.FlashCard;
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
		try {
			connection.insertItem(rssItem);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<FlashCard> selectFlashCard(int subjectID){
		ArrayList<FlashCard> flashCardsArray = null;
		try {
			flashCardsArray = connection.selectFlashCardBySubject(subjectID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flashCardsArray;
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
			return connection.selectItemAllYears();
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
			// return connection.selectItem();
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
	
	public ArrayList<RSSItem> selectItemsFromAllYear() {
		// TODO Auto-generated method stub
		try {
			// return connection.selectItem();
			return connection.selectItemAllYears();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public void insertIOSKey(String key)
	{
		try {
			if (connection.addiosKey(key)) {
				System.out.println("Inserted 1 Key " + key);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertAndroidKey(String key)
	{
		try {
			if (connection.addAndroidKey(key)) {
				System.out.println("Inserted 1 Key " + key);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getPhoneIDs(String phoneType) throws SQLException {
		if (phoneType.equals("ios")) {
			return connection.getIOSIDs();
		} else if (phoneType.equals("android")) {

			return connection.getAndroidIDs();
		}
		return null;
	}
	
	public boolean deleteIOS(String key) throws SQLException
	{
		return connection.deleteIOSKey(key);
	}

	public boolean deleteAndroid(String key) throws SQLException
	{
		return connection.deleteAndroidKey(key);
	}


	public MySQLDAO getConnection() {
		return connection;
	}

	public void setConnection(MySQLDAO connection) {
		try{
			closeConnection();
		}catch(NullPointerException e){
			System.out.println("Connection closing problem");
		}
		this.connection = connection;
	}

	public void closeConnection(){
		this.connection.close();
	}

	public boolean selectItemByTitleDate(String title, Date pubDate) {
		try {
			return this.connection.isItemExist(title,pubDate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<RSSItem> selectItemsFromSpecifiedYears(String [] years,
			int limit, int offset) {

		System.out.println("Limit"+limit + " : OFFSET :" +offset);
	
		return this.connection.selectItemsFromSpecificYears(years,limit, offset);
	}


}
