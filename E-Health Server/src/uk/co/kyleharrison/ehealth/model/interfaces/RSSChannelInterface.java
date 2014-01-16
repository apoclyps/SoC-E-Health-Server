package uk.co.kyleharrison.ehealth.model.interfaces;

import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;

public interface RSSChannelInterface {
	
	public void AddItem();
	public void RemoveItem();
	public RSSItem SearchItem();
	public void UpdateItem();
	public int CountItems();
	public RSSItem returnItem();
	
}
