package uk.co.kyleharrison.ehealth.service.xml.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import uk.co.kyleharrison.ehealth.service.xml.deconstruct.XMLChannel;
import uk.co.kyleharrison.ehealth.service.xml.deconstruct.XMLGroup;
import uk.co.kyleharrison.ehealth.service.xml.deconstruct.XMLItem;

public class XMlDocumentBuilder {

	private String xmlContent=null;
	private XMLChannel xmlChannel;
	private XMLItem xmlItem;
	private XMLGroup xmlGroup;

	public XMlDocumentBuilder() {
		super();
	}
	
	public XMlDocumentBuilder(String xmlContent) {
		super();
		this.xmlContent = xmlContent;
	}
	
	public String getXmlContent() {
		return xmlContent;
	}

	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	public XMLChannel getXmlChannel() {
		return xmlChannel;
	}

	public void setXmlChannel(XMLChannel xmlChannel) {
		this.xmlChannel = xmlChannel;
	}

	public XMLItem getXmlItem() {
		return xmlItem;
	}

	public void setXmlItem(XMLItem xmlItem) {
		this.xmlItem = xmlItem;
	}

	public XMLGroup getXmlGroup() {
		return xmlGroup;
	}

	public void setXmlGroup(XMLGroup xmlGroup) {
		this.xmlGroup = xmlGroup;
	}


	public Document buildXMLDocument() {
		try {
			String xmlFile = this.xmlContent;

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(
					xmlFile)));
			doc.getDocumentElement().normalize();
			
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Extracts information from XML Document
	public void extractXMLData(Document doc){
		new XMLChannel().CreateChannelList(doc);
		System.out.println("");
		new XMLItem().parseItemList(doc);
	}
	
	// Gets string value from an element tag
	protected String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}


	
}
