package uk.co.kyleharrison.ehealth.model.inmemory.proxy;

import java.net.URL;
import java.util.Date;

import uk.co.ehealth.system.interfaces.RSSChannelInterface;
import uk.co.kyleharrison.ehealth.model.inmemory.rss.RSSChannel;
import uk.co.kyleharrison.ehealth.model.inmemory.rss.RSSItem;

public class RSSChannelProxy implements RSSChannelInterface {

	RSSChannel rc = null;
	
	public RSSChannel CreateChannel(String title, URL link, String description, Date lastBuildDate, String language, 
			String updatePeriod, int updateFrequency, URL generator) {
		this.rc = new RSSChannel(title,link,description, lastBuildDate,language, updatePeriod,updateFrequency, generator);
		return rc;
	}
	
	public void AddItem(RSSItem rsi) {
		// TODO Auto-generated method stub
	}

	@Override
	public void RemoveItem() {
		// TODO Auto-generated method stub
	}

	@Override
	public RSSItem SearchItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void UpdateItem() {
		// TODO Auto-generated method stub
	}

	@Override
	public int CountItems() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RSSItem returnItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RSSChannel CreateChannel(String title, URL link, String description,
			java.sql.Date lastBuildData, String language, String updatePeriod,
			int updateFrequency, URL generator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void AddItem(String title, URL _link, String _description,
			java.sql.Date _lastBuildDate, String language, String updatePeriod,
			int _updateFrequency, URL _generator) {
		// TODO Auto-generated method stub
		
	}

}
