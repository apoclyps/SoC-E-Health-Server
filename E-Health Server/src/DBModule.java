import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DBModule {
	
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private Connection connection ; 
	
	public Connection connectToDB(){
		
		String url = "jdbc:mysql://localhost:3306/mbchb";
		String username = "giang";
		String password = "meotom";
		try {
		    System.out.println("Connecting database...");
		    connection = DriverManager.getConnection(url, username, password);
		    System.out.println("Database connected!");
		} catch (SQLException e) {
		    throw new RuntimeException("Cannot connect the database!", e);
		}
		return connection ; 
	}

	public void insertChannel(String title, String link, String desc,
							  Date lastBuild, String language, String updatePeriod,
							  int updateFrequency,URL generator) throws SQLException {
		connection = connectToDB();
		 // PreparedStatements can use variables and are more efficient
	      preparedStatement = connection.prepareStatement("insert into mbchb.channel" +
	      		"(Title, Link, Description, LastBuild, Language, UpdatePeriod, UpdateFrequency, URLGenerator)" +
	      		" values  (?,?,?,?,?,?,?,?)");
	      // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	      // Parameters start with 1
	      preparedStatement.setString(1, title);
	      preparedStatement.setString(2, link);
	      preparedStatement.setString(3, desc);
	      preparedStatement.setDate(4, new java.sql.Date(lastBuild.getTime()));
	      preparedStatement.setString(5, language);
	      preparedStatement.setString(6, updatePeriod);
	      preparedStatement.setInt(7,updateFrequency);
	      preparedStatement.setString(8, generator.toString());
	      System.out.println("Insert succeed!");
	      preparedStatement.executeUpdate();
	      if (connection != null) {
              connection.close();
          }
	}
	
	public void insertItem(String title,URL link,int comment,
						   Date pubDate, String creator,String category,
						   String description,String commentRSS,int channel,Date creationDate) throws SQLException{
						   // note that Channel would be the ChannelID that contain this item  
		
		connection = connectToDB();
		
		preparedStatement = connection.prepareStatement("insert into mbchb.itemtable" +
	      		"(Title,Link,Comment,PubDate,Creator,Category,Description,CommentRSS,Channel,CreationDate)" +
	      		" VALUES (?,?,?,?,?,?,?,?,?,?)");
	
	      preparedStatement.setString(1, title);
	      preparedStatement.setString(2, link.toString());
	      preparedStatement.setInt(3, comment);
	      preparedStatement.setDate(4, new java.sql.Date(pubDate.getTime()));
	      preparedStatement.setString(5, creator);
	      preparedStatement.setString(6, category);
	      preparedStatement.setString(7,description);
	      preparedStatement.setString(8, commentRSS);
	      preparedStatement.setInt(9, channel);
	      preparedStatement.setDate(10, new java.sql.Date(creationDate.getTime()));
	      System.out.println("Insert succeed!");
	      preparedStatement.executeUpdate();
	      if (connection != null) {
             connection.close();
         }
		
	}
	
	public void selectChannel() throws SQLException {
		connection = connectToDB();
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
		connection = connectToDB();
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
