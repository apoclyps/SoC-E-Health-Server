package uk.co.kyleharrison.ehealth.service.xml;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSGroup;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;

import com.thoughtworks.xstream.XStream;

public class RSSItemXStreamFacade extends RSSItem {

	XStream xstream = null;
	private String rssItemName = "rssItem";

	public RSSItemXStreamFacade() {
		super();
		this.xstream = new XStream();
	}
	
	public RSSItemXStreamFacade(XStream xstream) {
		super();
		this.xstream = xstream;
	}
	
	public void setRSSItemAliasType(){
		xstream.aliasType(getRssItemName(), RSSItem.class);
	}
	
	public void setRSSChannelAliasType(){
		xstream.aliasType(getRssItemName(), RSSChannel.class);
	}
	
	public void setRSSGroupAliasType(){
		xstream.aliasType(getRssItemName(), RSSGroup.class);
	}

	public String getRssItemName() {
		return this.rssItemName;
	}

	public void setRssItemName(String rssItemName) {
		this.rssItemName = rssItemName;
	}
	
	public String encodeObject(Object object){
		return xstream.toXML(object);
	}
	
	public RSSItem decodeRSSItem(String xml){
		return (RSSItem)xstream.fromXML(xml);
	}

	public RSSChannel decodeRSSChannel(String xml){
		return (RSSChannel)xstream.fromXML(xml);
	}
	
	public RSSGroup decodeRSSGroup(String xml){
		return (RSSGroup)xstream.fromXML(xml);
	}
}
