package uk.co.kyleharrison.ehealth.model.xml.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import uk.co.ehealth.system.interfaces.HTMLParserInterface;

public class HTMLParser implements HTMLParserInterface {

	private Document doc = null;
	private String cleanText = null;
	private String dirtyText = null; //How Kyle likes it
	
	public HTMLParser()
	{
		super();
	}
	
	public HTMLParser(String html)
	{
		super();
		setDirtyHTML(html);
		doc = null;
		cleanText = null;
	}
	
	@Override
	public String cleanHTML(String html) {
		// TODO Auto-generated method stub		
		doc = Jsoup.parse(html);
		
		String cleanText = doc.body().text();
		
		return cleanText; 
	}
	
	public void setCleanHTML(String cleanText)
	{
		this.cleanText=cleanText;
	}
	
	public String getCleanHTML()
	{
		return cleanText;
	}
	
	public void setDirtyHTML(String dirtyText)
	{
		this.dirtyText=dirtyText;
	}
	
	public String getDirtyHTML()
	{
		return dirtyText;
	}

}
