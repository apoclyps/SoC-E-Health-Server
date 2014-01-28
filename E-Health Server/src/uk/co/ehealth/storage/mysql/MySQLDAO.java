package uk.co.ehealth.storage.mysql;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;

public class MySQLDAO extends MySQLConnector {
	
	private PreparedStatement preparedStatement = null;

	public MySQLDAO() {
		super();
	}

	public void insertChannel(RSSChannel rssChannel) throws SQLException {
		if(this.checkConnection()){
			 // PreparedStatements can use variables and are more efficient
		      preparedStatement = connection.prepareStatement("insert into mbchb.channel" +
		      		"(Title, Link, Description, LastBuild, Language, UpdatePeriod, UpdateFrequency, URLGenerator)" +
		      		" values  (?,?,?,?,?,?,?,?)");
		      // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
		      // Parameters start with 1
		      preparedStatement.setString(1, rssChannel.getTitle());
		      preparedStatement.setString(2, rssChannel.getLink().toString());
		      preparedStatement.setString(3, rssChannel.getDescription());
		      preparedStatement.setDate(4, new java.sql.Date(rssChannel.getLastBuildDate().getTime()));
		      preparedStatement.setString(5, rssChannel.getLanguage());
		      preparedStatement.setString(6, rssChannel.getUpdatePeriod());
		      preparedStatement.setInt(7,rssChannel.getUpdateFrequency());
		      preparedStatement.setString(8, rssChannel.getGenerator().toString());
		      System.out.println("Insert succeed!");
		      preparedStatement.executeUpdate();
		}else{
			System.out.println("MYSQLDOA : Insert Channel : Connection Failed");
		}
	}
	
	public void insertItem(RSSItem rssItem) throws SQLException{
		// note that Channel would be the ChannelID that contain this item  
		if(this.checkConnection()){
		
		preparedStatement = connection.prepareStatement("insert into mbchb.itemtable" +
	      		"(Title,Link,PubDate,Creator,Category,Description,CommentRSS)" +
	      		" VALUES (?,?,?,?,?,?,?)");
	
	      preparedStatement.setString(1, rssItem.getTitle());
	      preparedStatement.setString(2, rssItem.getLink().toString());
	 //     preparedStatement.setInt(3, rssItem.getSlashComments());
	      preparedStatement.setDate(3, new java.sql.Date(rssItem.getPubDate().getTime()));
	      preparedStatement.setString(4, rssItem.getCreator());
	      preparedStatement.setString(5, rssItem.getCatergory());
	      preparedStatement.setString(6,rssItem.getDescription());
	      preparedStatement.setString(7, rssItem.getComments().toString());
	//      preparedStatement.setDate(8, new java.sql.Date(rssItem.getCreationDate().getTime()));
	      System.out.println("Insert succeed!");
	      preparedStatement.executeUpdate();
		}else{
			System.out.println("MYSQLDOA : Insert item : Connection Failed");
		}
		
	}
	
	public ArrayList<RSSChannel> selectChannel() throws SQLException, MalformedURLException {
		ArrayList<RSSChannel> channelList = new ArrayList<RSSChannel>();
		
		if(this.checkConnection()){
		 // PreparedStatements can use variables and are more efficient
	      preparedStatement = connection.prepareStatement("select * from mbchb.channel");
	      // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	      // Parameters start with 1
	  
	      //System.out.println("Insert succeed!");
	      ResultSet resultSet = preparedStatement.executeQuery();
	      
	      // Pulling data by resultset.. 

		  while (resultSet.next()) {
	     // Getting data... 
			  RSSChannel channel = new RSSChannel();
			  URL url = new URL(resultSet.getString("Link"));
			//  DateFormat lastBuildDate = new Date(resultSet.getString("LastBuild"));
			  URL generator = new URL(resultSet.getString("URLGenerator"));
			  String channelId = resultSet.getString("ChannelID");
			  channel.setTitle(resultSet.getString("Title"));
			  
			  channel.setLink(url);
			  channel.setDescription(resultSet.getString("Description"));
			  channel.setLastBuildDate(resultSet.getDate("LastBuild"));
			  channel.setLanguage(resultSet.getString("Language"));
			  channel.setUpdatePeriod(resultSet.getString("UpdatePeriod"));
			  channel.setUpdateFrequency(resultSet.getInt("UpdateFrequency"));
			  channel.setGenerator(generator);
			  
			  System.out.println("Channel ID : " + channelId);
			  channelList.add(channel);	
	    }
	      
	      if (connection != null) {
            connection.close();
        }
		}
	      return channelList;
	}
	public ArrayList<RSSItem> selectItem() throws SQLException, MalformedURLException{
		ArrayList<RSSItem> itemList = new ArrayList<RSSItem>();
		
		if(this.checkConnection()){
		// PreparedStatements can use variables and are more efficient
	      preparedStatement = connection.prepareStatement("select * from mbchb.Item");
	      // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	      // Parameters start with 1
	  
	      //System.out.println("Insert succeed!");
	      ResultSet resultSet = preparedStatement.executeQuery();
	      
	      // Pulling data by resultset.. 

		  while (resultSet.next()) {
	     // Getting data... 
		 //	  String itemID = resultSet.getString("ID");
			  RSSItem item = new RSSItem();
			  URL link = new URL(resultSet.getString("Link"));
			//  URL commentRss = new URL(resultSet.getString("CommentRSS"));
				  
			  item.setTitle(resultSet.getString("Title"));
			  item.setLink(link);
			  item.setSlashComments(resultSet.getInt("Comments"));
			  item.setPubDate(resultSet.getDate("PubDate"));
			  item.setCreator(resultSet.getString("Creator"));
			  item.setCatergory(resultSet.getString("Category"));
			  item.setDescription(resultSet.getString("Description"));
			//  item.setCommentRss(commentRss);
			  itemList.add(item);
	    }
	      if (connection != null) {
           connection.close();
       }
		}
	      return itemList;
	}
}
