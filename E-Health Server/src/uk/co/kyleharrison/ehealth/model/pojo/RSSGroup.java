package uk.co.kyleharrison.ehealth.model.pojo;

import java.util.ArrayList;

public class RSSGroup {

	private ArrayList<RSSChannel> rss_channel_groups = new ArrayList<RSSChannel>();

	public RSSGroup() {
		super();
	}

	public RSSGroup(ArrayList<RSSChannel> rss_channel_groups) {
		super();
		this.rss_channel_groups = rss_channel_groups;
	}

	public ArrayList<RSSChannel> getRss_channel_groups() {
		return rss_channel_groups;
	}

	public void setRss_channel_groups(ArrayList<RSSChannel> rss_channel_groups) {
		this.rss_channel_groups = rss_channel_groups;
	}
	
}
