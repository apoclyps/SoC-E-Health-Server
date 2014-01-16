package uk.co.kyleharrison.ehealth.model.interfaces;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;

public interface RSSGroupInterface {

	public void AddChannel();
	public void RemoveChannel();
	public RSSChannel SearchChannel();
	public void UpdateChannel();
	public int CountChannels();
	public RSSChannel returnChannel();
}
