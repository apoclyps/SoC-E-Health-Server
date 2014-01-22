package uk.co.kyleharrison.ehealth.service.xml.deconstruct;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.model.proxy.RSSItemProxy;
import uk.co.kyleharrison.ehealth.service.xml.util.XMlDocumentBuilder;

public class XMLItem extends XMlDocumentBuilder {

	private RSSItemProxy RIP = new RSSItemProxy();
	

	public XMLItem() {
		super();
	}
	
	public  void parseItemList(Document doc) {
		NodeList nList = doc.getElementsByTagName("item");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				// Get values
				System.out.println(getTagValue("title", eElement));
			}
		}
	}

	public ArrayList<RSSItem> CreateItemList(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void DeconstruxtXML(Document doc){
		
	}
	
	public void MapToPojo(){
		
	}
	
	public void RSSItemToString(){
		
	}
	
	
}
