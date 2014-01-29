package uk.co.ehealth.storage.mysql;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import uk.co.kyleharrison.ehealth.model.pojo.FlashCard;
import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;

public class MySQLDAO extends MySQLConnector {

	private PreparedStatement preparedStatement = null;

	public MySQLDAO() {
		super();
	}

	public void insertChannel(RSSChannel rssChannel) throws SQLException {
		if (this.checkConnection()) {
			// PreparedStatements can use variables and are more efficient
			preparedStatement = connection
					.prepareStatement("insert into mbchb.Channel"
							+ "(Title, Link, Description, LastBuild, Language, UpdatePeriod, UpdateFrequency, URLGenerator)"
							+ " values  (?,?,?,?,?,?,?,?)");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// Parameters start with 1
			preparedStatement.setString(1, rssChannel.getTitle());
			preparedStatement.setString(2, rssChannel.getLink().toString());
			preparedStatement.setString(3, rssChannel.getDescription());
			preparedStatement.setString(4, rssChannel.getLastBuildDate()
					.toString());
			preparedStatement.setString(5, rssChannel.getLanguage());
			preparedStatement.setString(6, rssChannel.getUpdatePeriod());
			preparedStatement.setInt(7, rssChannel.getUpdateFrequency());
			preparedStatement
					.setString(8, rssChannel.getGenerator().toString());
			System.out.println("Insert succeed!");
			preparedStatement.executeUpdate();
		} else {
			System.out.println("MYSQLDOA : Insert Channel : Connection Failed");
		}
		if (connection != null) {
			connection.close();
		}
	}

	public boolean insertItem(RSSItem rssItem) throws SQLException {
		// note that Channel would be the ChannelID that contain this item
		if (this.checkConnection()) {

			preparedStatement = connection
					.prepareStatement("insert into mbchb.Item"
							+ "(Title,Link,PubDate,Creator,Category,Description,CommentRSS,Year)"
							+ " VALUES (?,?,?,?,?,?,?,?)");

			preparedStatement.setString(1, rssItem.getTitle());
			preparedStatement.setString(2, rssItem.getLink().toString());
			// preparedStatement.setInt(3, rssItem.getSlashComments());
			preparedStatement.setString(3, rssItem.getPubDate().toString());
			preparedStatement.setString(4, rssItem.getCreator());
			preparedStatement.setString(5, rssItem.getCategory());
			preparedStatement.setString(6, rssItem.getDescription());
			preparedStatement.setString(7, rssItem.getComments().toString());
			preparedStatement.setInt(8, rssItem.getYear());
			// preparedStatement.setDate(8, new
			// java.sql.Date(rssItem.getCreationDate().getTime()));
			// System.out.println("Insert succeed!"+ rssItem.getYear());
			preparedStatement.executeUpdate();
			
			if (connection != null) {
				connection.close();
			}
			return true;
		} else {
			System.out.println("MYSQLDOA : Insert item : Connection Failed");
			if (connection != null) {
				connection.close();
			}
			return false;
		}

	}

	public ArrayList<RSSChannel> selectChannel() throws SQLException,
			MalformedURLException {
		ArrayList<RSSChannel> channelList = new ArrayList<RSSChannel>();

		if (this.checkConnection()) {
			// PreparedStatements can use variables and are more efficient
			preparedStatement = connection
					.prepareStatement("select * from mbchb.Channel");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// Parameters start with 1

			// System.out.println("Insert succeed!");
			ResultSet resultSet = preparedStatement.executeQuery();

			// Pulling data by resultset..

			while (resultSet.next()) {
				// Getting data...
				RSSChannel channel = new RSSChannel();
				URL url = new URL(resultSet.getString("Link"));
				// DateFormat lastBuildDate = new
				// Date(resultSet.getString("LastBuild"));
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

	public ArrayList<RSSItem> selectItem() throws SQLException,
			MalformedURLException {
		ArrayList<RSSItem> itemList = new ArrayList<RSSItem>();

		if (this.checkConnection()) {
			// PreparedStatements can use variables and are more efficient
			preparedStatement = connection
					.prepareStatement("select * from mbchb.Item");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// Parameters start with 1

			// System.out.println("Insert succeed!");
			ResultSet resultSet = preparedStatement.executeQuery();

			// Pulling data by resultset..

			while (resultSet.next()) {
				// Getting data...
				// String itemID = resultSet.getString("ID");
				RSSItem item = new RSSItem();
				URL link = new URL(resultSet.getString("Link"));
				// URL commentRss = new URL(resultSet.getString("CommentRSS"));

				item.setTitle(resultSet.getString("Title"));
				item.setLink(link);
				item.setSlashComments(resultSet.getInt("Comments"));
				item.setPubDate(resultSet.getDate("PubDate"));
				item.setCreator(resultSet.getString("Creator"));
				item.setCategory(resultSet.getString("Category"));
				item.setDescription(resultSet.getString("Description"));
				item.setYear(resultSet.getInt("Year"));
				// item.setCommentRss(commentRss);
				itemList.add(item);
			}
			if (connection != null) {
				connection.close();
			}
		}
		return itemList;
	}

	public ArrayList<RSSItem> selectItemFromYear(String yearID)
			throws SQLException, MalformedURLException {
		ArrayList<RSSItem> itemList = new ArrayList<RSSItem>();

		if (this.checkConnection()) {
			// PreparedStatements can use variables and are more efficient
			preparedStatement = connection
					.prepareStatement("select * from mbchb.Item WHERE Year = '"
							+ yearID + "'");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// Parameters start with 1

			// System.out.println("Insert succeed!");
			ResultSet resultSet = preparedStatement.executeQuery();

			// Pulling data by resultset..

			while (resultSet.next()) {
				// Getting data...
				// String itemID = resultSet.getString("ID");
				RSSItem item = new RSSItem();
				URL link = new URL(resultSet.getString("Link"));
				// URL commentRss = new URL(resultSet.getString("CommentRSS"));

				item.setTitle(resultSet.getString("Title"));
				item.setLink(link);
				item.setSlashComments(resultSet.getInt("Comments"));
			//	System.out.println("MYSQLDAO pub date needs fixed");
				//item.setPubDate(resultSet.getDate("PubDate"));
				item.setCreator(resultSet.getString("Creator"));
				item.setCategory(resultSet.getString("Category"));
				item.setDescription(resultSet.getString("Description"));
				item.setYear(resultSet.getInt("Year"));
				// item.setCommentRss(commentRss);
				itemList.add(item);
			}
			if (connection != null) {
				connection.close();
			}
		}
		return itemList;
	}

	public void insertToFlashCard(int cardID, int subjectID,
			int questionNumber, String question, String answer)
			throws SQLException {
		if (this.checkConnection()) {
			// PreparedStatements can use variables and are more efficient
			preparedStatement = connection
					.prepareStatement("insert into mbchb.FlashCards"
							+ "(CardID, SubjectID, QuestionNum,Question, Answer)"
							+ " values  (?,?,?,?,?)");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// Parameters start with 1
			preparedStatement.setInt(1, cardID);
			preparedStatement.setInt(2, subjectID);
			preparedStatement.setInt(3, questionNumber);
			preparedStatement.setString(4, question);
			preparedStatement.setString(5, answer);
			System.out.println("Insert succeed!");
			preparedStatement.executeUpdate();
		}
		if (connection != null) {
			connection.close();
		}
	}

	public void insertToSubject(int subjectID, String cardSubject,
			int yearStudied) throws SQLException {
		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("insert into mbchb.Subject"
							+ "(SubjectID,CardSubject,YearStudied)"
							+ "values (?,?,?)");

			preparedStatement.setInt(1, subjectID);
			preparedStatement.setString(2, cardSubject);
			preparedStatement.setInt(3, yearStudied);
			System.out.println("Insert succeed!");
			preparedStatement.executeUpdate();
		}
		if (connection != null) {
			connection.close();
		}

	}

	public ArrayList<FlashCard> selectFlashCard() throws SQLException {
		ArrayList<FlashCard> flashCardList = new ArrayList<FlashCard>();
		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("SELECT Flashcards.CardID, Flashcards.LectureNum, Flashcards.QuestionNum,"
							+ "Flashcards.Question, Flashcards.Answer , `Subject`.SubjectID,`Subject`.CardSubject,"
							+ "`Subject`.YearStudied"
							+ "FROM Flashcards"
							+ "INNER JOIN `Subject`"
							+ "ON Flashcards.SubjectID=`Subject`.SubjectID"
							+ "ORDER BY `Subject`.SubjectID;");

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				FlashCard card = new FlashCard();
				card.setCardID(rs.getInt("CardID"));
				card.setSubjectID(rs.getInt("SubjectID"));
				card.setAnswer(rs.getString("Answer"));
				card.setCardSubject(rs.getString("CardSubject"));
				card.setLectureNumber(rs.getInt("LectureNum"));
				card.setQuestion(rs.getString("Question"));
				card.setCardSubject(rs.getString("CardSubject"));
				card.setYearStudied(rs.getInt("YearStudied"));

				flashCardList.add(card);
			}
		}
		return flashCardList;
	}

	public ArrayList<FlashCard> selectFlashCardBySubject(String subject)
			throws SQLException {

		ArrayList<FlashCard> flashCardList = new ArrayList<FlashCard>();
		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("SELECT `Subject`.SubjectID, `Subject`.CardSubject,Flashcards.Question,"
							+ "Flashcards.Answer FROM Flashcards INNER JOIN `Subject`ON"
							+ "Flashcards.SubjectID=`Subject`.SubjectID"
							+ "WHERE `Subject`.SubjectID = ?"
							+ "ORDER BY `Subject`.SubjectID;");

			preparedStatement.setString(0, subject);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				FlashCard card = new FlashCard();
				card.setCardID(rs.getInt("CardID"));
				card.setSubjectID(rs.getInt("SubjectID"));
				card.setAnswer(rs.getString("Answer"));
				card.setCardSubject(rs.getString("CardSubject"));
				card.setLectureNumber(rs.getInt("LectureNum"));
				card.setQuestion(rs.getString("Question"));
				card.setCardSubject(rs.getString("CardSubject"));
				card.setYearStudied(rs.getInt("YearStudied"));

				flashCardList.add(card);
			}
		}
		return flashCardList;
	}
	
	public ArrayList<String> getAndroidIDs() throws SQLException
	{
		ArrayList<String> androidIDs = new ArrayList<String>();
		if (this.checkConnection()){
			preparedStatement = connection.prepareStatement("SELECT * FROM subs_android;");
		
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String ID =rs.getString("ID");
				androidIDs.add(ID);
			}
			
		}
		
		return androidIDs;
	}
	
	public ArrayList<String> getIOSIDs() throws SQLException
	{
		ArrayList<String> iOSIDs = new ArrayList<String>();
		if (this.checkConnection()){
			preparedStatement = connection.prepareStatement("SELECT * FROM subs_ios;");
		
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String ID =rs.getString("ID");
				iOSIDs.add(ID);
			}
			
		}
		
		return iOSIDs;
	}
	
}
