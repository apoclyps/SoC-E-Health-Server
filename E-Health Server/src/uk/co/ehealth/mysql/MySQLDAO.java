package uk.co.ehealth.mysql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;

public class MySQLDAO extends MySQLConnector {
	
	private PreparedStatement preparedStatement = null;

	public MySQLDAO() {
		super();
	}

	public Connection openConnection(){
		
		String url = "jdbc:mysql://localhost:3306/mbchb";
		String username = "root";
		String password = "hellokitty1";
		try {
		    System.out.println("Connecting database...");
		    this.connection = DriverManager.getConnection(url, username, password);
		    System.out.println("Database connected!");
		} catch (SQLException e) {
		    throw new RuntimeException("Cannot connect the database!", e);
		}
		return connection ; 
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
		connection = openConnection();
		
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
	      if (connection != null) {
             connection.close();
         }
		
	}
	
	public void selectChannel() throws SQLException {
		connection = openConnection();
		 // PreparedStatements can use variables and are more efficient
	      preparedStatement = connection.prepareStatement("select * from mbchb.channel");
	      // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	      // Parameters start with 1
	  
	      //System.out.println("Insert succeed!");
	      ResultSet resultSet = preparedStatement.executeQuery();
	      
	      // Pulling data by resultset.. 

		  while (resultSet.next()) {
	   
	     // Getting data... 
			  String channelId = resultSet.getString("ChannelID");
			  System.out.println("Channel ID : " + channelId);
	  
	    }
	      
	      if (connection != null) {
            connection.close();
        }
	}
	public void selectItem() throws SQLException{
		connection = openConnection();
		 // PreparedStatements can use variables and are more efficient
	      preparedStatement = connection.prepareStatement("select * from mbchb.itemtable");
	      // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	      // Parameters start with 1
	  
	      //System.out.println("Insert succeed!");
	      ResultSet resultSet = preparedStatement.executeQuery();
	      
	      // Pulling data by resultset..
		  while (resultSet.next()) {
	     // Getting data... 
			  String itemID = resultSet.getString("ID");
			  System.out.println("Item ID : " + itemID);
	    }
	      
	      if (connection != null) {
           connection.close();
       }

		
	}

}
