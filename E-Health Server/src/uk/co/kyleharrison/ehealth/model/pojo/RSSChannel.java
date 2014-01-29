package uk.co.kyleharrison.ehealth.model.pojo;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class RSSChannel  {
	
	private String title =null;
	private URL link;
	private String description;
	private Date lastBuildDate;
	private String language;
	private String updatePeriod;
	private int updateFrequency;
	private URL generator;
	private ArrayList <RSSItem> item_list = new ArrayList<RSSItem>();
	
	public RSSChannel() {
		super();
	}

	public RSSChannel(String title, URL link, String description,
			Date lastBuildDate, String language, String updatePeriod,
			int updateFrequency, URL generator) {
		super();
		this.title = title;
		this.link = link;
		this.description = description;
		this.lastBuildDate = lastBuildDate;
		this.language = language;
		this.updatePeriod = updatePeriod;
		this.updateFrequency = updateFrequency;
		this.generator = generator;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(Date lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUpdatePeriod() {
		return updatePeriod;
	}

	public void setUpdatePeriod(String updatePeriod) {
		this.updatePeriod = updatePeriod;
	}

	public int getUpdateFrequency() {
		return updateFrequency;
	}

	public void setUpdateFrequency(int updateFrequency) {
		this.updateFrequency = updateFrequency;
	}

	public URL getGenerator() {
		return generator;
	}

	public void setGenerator(URL generator) {
		this.generator = generator;
	}

	public ArrayList<RSSItem> getItem_list() {
		return item_list;
	}

	public void setItem_list(ArrayList<RSSItem> item_list) {
		this.item_list = item_list;
	}

}
