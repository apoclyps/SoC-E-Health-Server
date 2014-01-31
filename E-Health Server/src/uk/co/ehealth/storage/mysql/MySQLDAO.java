package uk.co.ehealth.storage.mysql;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.CommunicationsException;

import uk.co.kyleharrison.ehealth.model.inmemory.flashcards.FlashCard;
import uk.co.kyleharrison.ehealth.model.inmemory.rss.RSSChannel;
import uk.co.kyleharrison.ehealth.model.inmemory.rss.RSSItem;

public class MySQLDAO extends MySQLConnector {

	private PreparedStatement preparedStatement = null;

	public MySQLDAO() {
		super();
	}

	public void insertChannel(RSSChannel rssChannel) throws SQLException {
		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("insert into mbchb.Channel"
							+ "(Title, Link, Description, LastBuild, Language, UpdatePeriod, UpdateFrequency, URLGenerator)"
							+ " values  (?,?,?,?,?,?,?,?)");

			preparedStatement.setString(1, rssChannel.getTitle());
			preparedStatement.setString(2, rssChannel.getLink().toString());
			preparedStatement.setString(3, rssChannel.getDescription());
			preparedStatement.setTimestamp(4, new java.sql.Timestamp(rssChannel
					.getLastBuildDate().getTime()));
			preparedStatement.setString(5, rssChannel.getLanguage());
			preparedStatement.setString(6, rssChannel.getUpdatePeriod());
			preparedStatement.setInt(7, rssChannel.getUpdateFrequency());
			preparedStatement
					.setString(8, rssChannel.getGenerator().toString());
			preparedStatement.executeUpdate();
		} else {
			System.out.println("MYSQLDOA : Insert Channel : Connection Failed");
		}
		if (connection != null) {
			connection.close();
		}
	}

	public boolean insertItem(RSSItem rssItem) throws SQLException {
		if (this.checkConnection()) {

			preparedStatement = connection
					.prepareStatement("insert into mbchb.Item"
							+ "(Title,Link,PubDate,Creator,Category,Description,CommentRSS,Year)"
							+ " VALUES (?,?,?,?,?,?,?,?)");

			preparedStatement.setString(1, rssItem.getTitle());
			preparedStatement.setString(2, rssItem.getLink().toString());
			preparedStatement.setTimestamp(3, new java.sql.Timestamp(rssItem
					.getPubDate().getTime()));
			preparedStatement.setString(4, rssItem.getCreator());
			preparedStatement.setString(5, rssItem.getCategory());
			preparedStatement.setString(6, rssItem.getDescription());
			preparedStatement.setString(7, rssItem.getComments().toString());
			preparedStatement.setInt(8, rssItem.getYear());

			preparedStatement.executeUpdate();

			if (connection != null) {
				connection.close();
			}
			return true;
		} else {
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
			preparedStatement = connection
					.prepareStatement("select * from mbchb.Channel");
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				RSSChannel channel = new RSSChannel();
				URL url = new URL(resultSet.getString("Link"));
				URL generator = new URL(resultSet.getString("URLGenerator"));
				channel.setTitle(resultSet.getString("Title"));
				channel.setLink(url);
				channel.setDescription(resultSet.getString("Description"));
				channel.setLastBuildDate(resultSet.getTimestamp("LastBuild"));
				channel.setLanguage(resultSet.getString("Language"));
				channel.setUpdatePeriod(resultSet.getString("UpdatePeriod"));
				channel.setUpdateFrequency(resultSet.getInt("UpdateFrequency"));
				channel.setGenerator(generator);

				channelList.add(channel);
			}

			if (connection != null) {
				connection.close();
			}
		}
		return channelList;
	}

	public ArrayList<RSSItem> selectItemAllYears() throws SQLException,
			MalformedURLException {
		ArrayList<RSSItem> itemList = new ArrayList<RSSItem>();

		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("select * from mbchb.Item GROUP BY pubDate DESC limit 0,10");
			ResultSet resultSet = null;
			try {
				resultSet = preparedStatement.executeQuery();
			} catch (Exception e) {
				System.out
						.println("Problem with selecting all items from year");
				e.printStackTrace();
			}
			while (resultSet.next()) {
				RSSItem item = new RSSItem();
				URL link = new URL(resultSet.getString("Link"));
				item.setTitle(resultSet.getString("Title"));
				item.setLink(link);
				item.setSlashComments(resultSet.getInt("Comments"));
				item.setPubDate(resultSet.getTimestamp("PubDate"));
				item.setCreator(resultSet.getString("Creator"));
				item.setCategory(resultSet.getString("Category"));
				item.setDescription(resultSet.getString("Description"));
				item.setYear(resultSet.getInt("Year"));
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
			preparedStatement = connection
					.prepareStatement("select * from mbchb.Item WHERE Year = "
							+ yearID + ";");

			try{
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				RSSItem item = new RSSItem();
				URL link = new URL(resultSet.getString("Link"));

				item.setTitle(resultSet.getString("Title"));
				item.setLink(link);
				item.setSlashComments(resultSet.getInt("Comments"));
				item.setPubDate(resultSet.getTimestamp("PubDate"));
				item.setCreator(resultSet.getString("Creator"));
				item.setCategory(resultSet.getString("Category"));
				item.setDescription(resultSet.getString("Description"));
				item.setYear(resultSet.getInt("Year"));
				itemList.add(item);
			}
			}catch(CommunicationsException e){
				e.printStackTrace();
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
			preparedStatement = connection
					.prepareStatement("insert into mbchb.FlashCards"
							+ "(CardID, SubjectID, QuestionNum,Question, Answer)"
							+ " values  (?,?,?,?,?)");

			preparedStatement.setInt(1, cardID);
			preparedStatement.setInt(2, subjectID);
			preparedStatement.setInt(3, questionNumber);
			preparedStatement.setString(4, question);
			preparedStatement.setString(5, answer);

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
							+ "Flashcards.Question, Flashcards.Answer , 'Subject'.SubjectID,'Subject'.CardSubject,"
							+ "'Subject'.YearStudied"
							+ "FROM Flashcards"
							+ "INNER JOIN 'Subject'"
							+ "ON Flashcards.SubjectID='Subject'.SubjectID"
							+ "ORDER BY 'Subject'.SubjectID;");

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
		if (connection != null) {
			connection.close();
		}
		return flashCardList;
	}

	public ArrayList<FlashCard> selectFlashCardBySubject(int subject)
			throws SQLException {

		ArrayList<FlashCard> flashCardList = new ArrayList<FlashCard>();
		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("SELECT * "
							+ "FROM Flashcards INNER JOIN Subject ON Flashcards.SubjectID=Subject.SubjectID "
							+ "WHERE Subject.SubjectID = " + subject + " "
							+ "ORDER BY Subject.SubjectID LIMIT 0,10;");

			ResultSet rs = null;
			try {
				rs = preparedStatement.executeQuery();
			} catch (CommunicationsException e) {
				e.printStackTrace();
			}

			while (rs.next()) {
				FlashCard card = new FlashCard();
				card.setCardID(rs.getInt("CardID"));
				card.setSubjectID(rs.getInt("SubjectID"));
				card.setAnswer(rs.getString("Answer"));
				card.setCardSubject(rs.getString("CardSubject"));
				card.setLectureNumber(rs.getInt("LectureNum"));
				card.setQuestion(rs.getString("Question"));
				card.setCardSubject(rs.getString("CardSubject"));

				flashCardList.add(card);
			}
		}
		if (connection != null) {
			connection.close();
		}
		return flashCardList;
	}
	
	public ArrayList<FlashCard> selectRandomFlashCardBySubject(int subject)
			throws SQLException {
		ArrayList<FlashCard> cardList = new ArrayList<FlashCard>();
		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("SELECT * "
							+ "FROM Flashcards INNER JOIN Subject ON Flashcards.SubjectID=Subject.SubjectID "
							+ "WHERE Subject.SubjectID = " + subject + " "
							+ "ORDER BY RAND() LIMIT 1;");

			ResultSet rs = null;
			try {
				rs = preparedStatement.executeQuery();
			} catch (CommunicationsException e) {
				e.printStackTrace();
			}

			while (rs.next()) {
				FlashCard card = new FlashCard();
				card.setCardID(rs.getInt("CardID"));
				card.setSubjectID(rs.getInt("SubjectID"));
				card.setAnswer(rs.getString("Answer"));
				card.setCardSubject(rs.getString("CardSubject"));
				card.setLectureNumber(rs.getInt("LectureNum"));
				card.setQuestion(rs.getString("Question"));
				card.setCardSubject(rs.getString("CardSubject"));
				cardList.add(card);
			}
		}
		if (connection != null) {
			connection.close();
		}
		return cardList;
	}

	public boolean addiosKey(String key) throws SQLException {
		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("insert into mbchb.subs_ios" + "(ID)"
							+ "values (?)");

			preparedStatement.setString(1, key);
			preparedStatement.executeUpdate();
			if (connection != null) {
				connection.close();
			}
			return true;
		}

		if (connection != null) {
			connection.close();
		}
		return false;
	}

	public boolean addAndroidKey(String key) throws SQLException {
		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("insert into mbchb.subs_android" + "(ID)"
							+ "values (?)");

			preparedStatement.setString(1, key);
			preparedStatement.executeUpdate();
			if (connection != null) {
				connection.close();
			}
			return true;
		}

		if (connection != null) {
			connection.close();
		}
		return false;
	}

	public ArrayList<String> getAndroidIDs() throws SQLException {
		ArrayList<String> androidIDs = new ArrayList<String>();
		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("SELECT * FROM subs_android;");

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String ID = rs.getString("ID");
				androidIDs.add(ID);
			}

		}

		return androidIDs;
	}

	public ArrayList<String> getIOSIDs() throws SQLException {
		ArrayList<String> iOSIDs = new ArrayList<String>();
		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("SELECT * FROM subs_ios;");

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String ID = rs.getString("ID");
				iOSIDs.add(ID);
			}

		}
		if (connection != null) {
			connection.close();
		}
		return iOSIDs;
	}

	public boolean deleteIOSKey(String token) {
		if (this.checkConnection()) {

			try {
				preparedStatement = connection
						.prepareStatement("Delete FROM subs_ios WHERE ID = '"
								+ token + "';");
				preparedStatement.execute();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}

	public boolean deleteAndroidKey(String token) throws SQLException {
		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("Delete FROM subs_android WHERE ID = '" + token + "';");
			preparedStatement.execute();
			return true;
		}
		if (connection != null) {
			connection.close();
			return false;
		}
		return false;
	}

	public boolean isItemExist(String title, Date pudDate) throws SQLException {
		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("SELECT Title,PubDate FROM Item WHERE Title = ? AND pubDate = ?;");
			preparedStatement.setString(1, title);
			preparedStatement.setTimestamp(2, new Timestamp(pudDate.getTime()));
			ResultSet rs = preparedStatement.executeQuery();

			return rs.first();
		}
		return false;

	}

	public boolean removeTokenFromIOS(String token) {
		if (this.checkConnection()) {

		}
		return true;

	}

	public ArrayList<RSSItem> selectItemsFromSpecificYears(String[] years,
			int limit, int offset) {

		String whereClause = "";
		for (int i = 0; i < years.length; i++) {
			whereClause = whereClause + "Item.Year = ? OR ";
		}
		whereClause = whereClause.substring(0, whereClause.length() - 4);

		ArrayList<RSSItem> itemList = new ArrayList<RSSItem>();

		if (this.checkConnection()) {
			try {
				preparedStatement = connection
						.prepareStatement("select * from mbchb.Item WHERE "
								+ whereClause + " GROUP BY pubDate DESC limit "
								+ offset + "," + limit + ";");

				for (int i = 0; i < years.length; i++) {
					preparedStatement.setInt(i + 1, Integer.parseInt(years[i]));
				}
				ResultSet rs = preparedStatement.executeQuery();

				while (rs.next()) {
					RSSItem item = new RSSItem();
					URL link = new URL(rs.getString("Link"));
					item.setTitle(rs.getString("Title"));
					item.setLink(link);
					item.setSlashComments(rs.getInt("Comments"));
					item.setPubDate(rs.getTimestamp("PubDate"));
					item.setCreator(rs.getString("Creator"));
					item.setCategory(rs.getString("Category"));
					item.setDescription(rs.getString("Description"));
					item.setYear(rs.getInt("Year"));
					itemList.add(item);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return itemList;
	}
	
}
