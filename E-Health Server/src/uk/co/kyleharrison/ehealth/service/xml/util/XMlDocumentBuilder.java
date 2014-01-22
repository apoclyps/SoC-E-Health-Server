package uk.co.kyleharrison.ehealth.service.xml.util;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import uk.co.jackgraham.ehealth.services.HTMLParser;
import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.service.xml.deconstruct.XMLChannel;
import uk.co.kyleharrison.ehealth.service.xml.deconstruct.XMLGroup;
import uk.co.kyleharrison.ehealth.service.xml.deconstruct.XMLItem;
import uk.co.kyleharrison.ehealth.services.util.DateConverter;

public class XMlDocumentBuilder {

	private String xmlContent=null;
	private XMLChannel xmlChannel;
	private XMLItem xmlItem;
	private XMLGroup xmlGroup;
	private HTMLParser htmlParser;
	protected DateConverter DC;

	public XMlDocumentBuilder() {
		super();
		this.htmlParser = new HTMLParser();
		this.DC = new DateConverter();
	}
	
	public XMlDocumentBuilder(String xmlContent) {
		super();
		this.xmlContent = xmlContent;
		this.htmlParser = new HTMLParser();
		this.DC = new DateConverter();
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
		XMLChannel xmlC = new XMLChannel();
		XMLItem xmlI = new XMLItem();
		xmlC.CreateChannelList(doc);
		RSSChannel rc = xmlC.getRSSChannel();
		System.out.println("");

		ArrayList<RSSItem> rsiA = xmlI.CreateItemList(doc);
		System.out.println("\n\nSize of Item Array results = " +rsiA.size());
		rc.setItem_list(rsiA);
		System.out.println("Size of RSS Channel results = " +rc.getItem_list().get(0).getTitle());
		
	}
	
	// Gets string value from an element tag
	protected String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

	public HTMLParser getHtmlParser() {
		return htmlParser;
	}

	public void setHtmlParser(HTMLParser htmlParser) {
		this.htmlParser = htmlParser;
	}
	
	public URL castStringToURL(String url){
		
		URL _link = null;
		if(url!=null){
			try{
				_link = new URL(url);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				System.out.println( " " +e1.getMessage());
			}
		}
		return _link;
	}


	
}
