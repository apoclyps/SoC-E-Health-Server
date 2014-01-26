package uk.co.kyleharrison.ehealth.service.xml.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.proxy.RSSChannelProxy;
import uk.co.kyleharrison.ehealth.service.xml.XMlDocumentBuilder;
import uk.co.kyleharrison.ehealth.services.xml.util.HTMLParser;

public class XMLChannel extends XMlDocumentBuilder {

	private RSSChannelProxy rcp;
	private RSSChannel rc;
	private String[] tags = {"title","link","description","lastBuildDate",
			"language","sy:updatePeriod","sy:updateFrequency","generator"};
	private String[] results;
	
	public XMLChannel() {
		super();
		this.setHtmlParser(new HTMLParser());
		this.rcp = new RSSChannelProxy();
		this.rc = new RSSChannel();
		this.results = new String[8];
		
	}
	
	public RSSChannel getRSSChannel(){
		return this.rc;
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
		return this.rc;
	}
	
	private void DeconstructXML(Document doc){
		this.rc = new RSSChannel();
		NodeList nList2 = doc.getElementsByTagName("channel");
		
		for (int i = 0; i < nList2.getLength(); i++) {
			Node node = nList2.item(i);
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				for(int j=0; j<tags.length; j++){
					try{
						// Debugging Comment
						//System.out.println(tags[j] + " \t\t" +getTagValue(tags[j], eElement));
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

		rcp = new RSSChannelProxy();
		
		// String to URL
		URL _link = this.castStringToURL(link);

		//Clean Description
		String _description = null;
		if(description!=null){
			_description = this.getHtmlParser().cleanHTML(description);
			
		}
		//Convert to Date
		
		Date _lastBuildDate = this.DC.convertStringToDate(lastBuildDate);

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
			e.printStackTrace();
		}
		
		this.rc = rcp.CreateChannel(title, _link, _description, _lastBuildDate, language, updatePeriod, 
				_updateFrequency, _generator);
	}
}
