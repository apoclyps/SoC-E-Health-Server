package uk.co.kyleharrison.ehealth.model.interfaces;

import java.net.URL;
import java.sql.Date;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;

public interface RSSChannelInterface {
	
	public RSSChannel CreateChannel(String title, URL link, String description, Date lastBuildData,
			String language, String updatePeriod, int updateFrequency, URL generator);
	public void AddItem(String title, URL _link, String _description, Date _lastBuildDate, String language, String updatePeriod, int _updateFrequency, URL _generator);
	public void RemoveItem();
	public RSSItem SearchItem();
	public void UpdateItem();
	public int CountItems();
	public RSSItem returnItem();
	
}
