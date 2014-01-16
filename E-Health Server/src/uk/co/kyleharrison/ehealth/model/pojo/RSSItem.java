package uk.co.kyleharrison.ehealth.model.pojo;

import java.sql.Date;

import org.apache.tomcat.util.net.URL;

public class RSSItem {
	
	private String title;
	private URL link;
	private Date pubDate;
	private String creator;
	private String catergory;
	private String description;
	private URL commentRss;
	private int comments;
	
	public RSSItem() {
		super();
	}

	public RSSItem(String title, URL link, Date pubDate, String creator,
			String catergory, String description, URL commentRss, int comments) {
		super();
		this.title = title;
		this.link = link;
		this.pubDate = pubDate;
		this.creator = creator;
		this.catergory = catergory;
		this.description = description;
		this.commentRss = commentRss;
		this.comments = comments;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public URL getLink() {
		return link;
	}

	public void setLink(URL link) {
		this.link = link;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCatergory() {
		return catergory;
	}

	public void setCatergory(String catergory) {
		this.catergory = catergory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public URL getCommentRss() {
		return commentRss;
	}

	public void setCommentRss(URL commentRss) {
		this.commentRss = commentRss;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}
	
}
