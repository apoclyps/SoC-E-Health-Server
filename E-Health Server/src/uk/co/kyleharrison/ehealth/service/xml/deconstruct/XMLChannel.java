package uk.co.kyleharrison.ehealth.service.xml.deconstruct;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.co.jackgraham.ehealth.services.HTMLParser;
import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.proxy.RSSChannelProxy;
import uk.co.kyleharrison.ehealth.service.xml.util.XMlDocumentBuilder;

public class XMLChannel extends XMlDocumentBuilder {

	RSSChannelProxy rcp = new RSSChannelProxy();
	RSSChannel rc = new RSSChannel();
	private String[] tags = {"title","link","description","lastBuildDate",
			"language","sy:updatePeriod","sy:updateFrequency","generator"};
	private String[] results = new String[8];
	private HTMLParser htmlParser = null;
	
	public XMLChannel() {
		super();
		this.setHtmlParser(new HTMLParser());
	}
	
	public void getChannelList(Document doc){
		CreateChannelList(doc);
	}

	public String[] getElements() {
		return tags;
	}

	public void setElements(String[] elements) {
		this.tags = elements;
	}
	
	public RSSChannel CreateChannelList(Document doc){

		DeconstructXML(doc);

		return null;
	}
	
	public void DeconstructXML(Document doc){
		RSSChannel rc = new RSSChannel();
		NodeList nList2 = doc.getElementsByTagName("channel");
		
		for (int i = 0; i < nList2.getLength(); i++) {
			Node node = nList2.item(i);
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				for(int j=0; j<tags.length; j++){
					try{
						// Debugging Comment
						System.out.println(tags[j] + " \t\t" +getTagValue(tags[j], eElement));
						results[j] = getTagValue(tags[j], eElement);
						
					}catch(NullPointerException e){
						e.getMessage();
					}
				}
				MapToPojo(results[0],results[1],results[2],results[3],results[4],results[5],results[6],results[7]);
				//Create RSS Item
				//Add Item to ArrayList
				//Add Arraylist to Channel
			}
		}
	}
	
	public void MapToPojo(String title, String link, String description ,
			String lastBuildDate, String language, String updatePeriod,
			String updateFrequency, String generator){

		RSSChannelProxy rc = new RSSChannelProxy();
		
		// String to URL
		URL _link = null;
		try{
			_link = new URL("https://mbchb.dundee.ac.uk");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			System.out.println( " " +e1.getMessage());
		}
		//Clean Description
		String _description = null;
		if(description!=null){
			_description = this.htmlParser.cleanHTML(description);
		}
		//Convert to Date
		Date _lastBuildDate = StringToDate(lastBuildDate);
		//Convert lastBuildDate to Date
		//convert updateFrequency to int
		
		int _updateFrequency=0;
		try{
			_updateFrequency = Integer.parseInt(updateFrequency);
		}catch(Exception e){
			_updateFrequency =0;
		}
		
		//Convert generator to URL
		URL _generator = null;
		try {
			_generator = new URL(generator);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.rc = rc.CreateChannel(title, _link, _description, _lastBuildDate, language, updatePeriod, _updateFrequency, _generator);
		testRC();
	}
	
	private void testRC(){
		System.out.println("Testing RC");
		System.out.println(""+rc.getTitle());
	}
	
	private Date StringToDate(String lastBuildDate){
		// Format 2008-11-11 13:23:44 - Mon, 20 Jan 2014 13:43:29 +0000
		String subLastBuildData = lastBuildDate.substring(0, 25);
	//	System.out.println("TEst "+test);
	//	System.out.println("Last Build = "+lastBuildDate);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
		java.util.Date date = null;
		try {
			date =  simpleDateFormat.parse(subLastBuildData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Date "+date.toString());
		return date;
	}

	public HTMLParser getHtmlParser() {
		return htmlParser;
	}

	public void setHtmlParser(HTMLParser htmlParser) {
		this.htmlParser = htmlParser;
	}
	
}
