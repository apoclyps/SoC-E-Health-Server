package uk.co.kyleharrison.ehealth.service.xml.deconstruct;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.proxy.RSSChannelProxy;
import uk.co.kyleharrison.ehealth.service.xml.util.XMlDocumentBuilder;

public class XMLChannel extends XMlDocumentBuilder {

	RSSChannelProxy rcp = new RSSChannelProxy();
	
	public XMLChannel() {
		super();
	}
	
	public void getChannelList(Document doc){
		parseChannelList(doc);
	}

	public RSSChannel parseChannelList(Document doc) {
		NodeList nList2 = doc.getElementsByTagName("channel");
		for (int i = 0; i < nList2.getLength(); i++) {
			Node node = nList2.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				System.out.println(getTagValue("title", eElement));
				
				//Create RSS Item
				//Add Item to ArrayList
				//Add Arraylist to Channel
			}
		}
		return null;
	}
	
	
}
