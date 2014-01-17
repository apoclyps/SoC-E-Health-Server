package uk.co.kyleharrison.ehealth.service.xml;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSGroup;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.model.proxy.RSSChannelProxy;

public class XStreamDecodeBuilder {

	RSSItemXStreamFacade rixf = new RSSItemXStreamFacade();
	
	
	public XStreamDecodeBuilder() {
		super();
	}

	public void decodeXML(String xml){
		
		RSSChannelProxy rcp = new RSSChannelProxy();
	
		//Accepts all XML and maps to Group
		RSSGroup rg = this.rixf.decodeRSSGroup(xml);
		for(RSSChannel rc : rg.getRss_channel_groups()){
			//Removes Channel XMl and creates instances for each channel
			rc = this.rixf.decodeRSSChannel(rg.getChannelXML());
			
			// Do for each channel
			rcp.AddItem();
			//Creates Items XMl for each item in that channel
			RSSItem ri = this.rixf.decodeRSSItem(rc.getItemsXML());
		}

	}
	
	
}
