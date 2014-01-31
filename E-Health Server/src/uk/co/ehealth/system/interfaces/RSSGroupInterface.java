package uk.co.ehealth.system.interfaces;

import uk.co.kyleharrison.ehealth.model.inmemory.rss.RSSChannel;

public interface RSSGroupInterface {

	public void AddChannel();
	public void RemoveChannel();
	public RSSChannel SearchChannel();
	public void UpdateChannel();
	public int CountChannels();
	public RSSChannel returnChannel();
}
