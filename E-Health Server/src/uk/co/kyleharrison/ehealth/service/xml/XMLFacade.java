package uk.co.kyleharrison.ehealth.service.xml;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.w3c.dom.Document;

import uk.co.kyleharrison.ehealth.service.xml.util.URLReader;
import uk.co.kyleharrison.ehealth.service.xml.util.XMlDocumentBuilder;

public class XMLFacade {

	private URL url = null;
	private String response = null;
	private URLReader urlReader =null;
	private Document doc = null;
	
	public XMLFacade() {
		super();
	}

	public XMLFacade(String url) {
		super();
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public URL getUrl() {
		return url;
	}

	public void setUrl(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public URLReader getUrlReader() {
		return urlReader;
	}

	public void setUrlReader(URLReader urlReader) {
		this.urlReader = urlReader;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public boolean DeconstructXMLToPojo(){
		if(this.url==null){
			return false;
		}
		this.urlReader = new URLReader();
		this.urlReader.setUrl(this.url.toString());
		
		try {
			this.response = urlReader.readxmlFromUrl();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception in XMLFacade trying to DeconstructXMLToPojo whilst reading XML from URL");
			e.printStackTrace();
		}

		// Creating Document from XML Data
		XMlDocumentBuilder xmlDocB = new XMlDocumentBuilder(this.response.toString());
		doc = xmlDocB.buildXMLDocument();
		if(doc!=null){
			xmlDocB.extractXMLData(doc);
		}
		return true;
	}
	

	public static void main(String [] arugments){
		
		//Store in Init function perhaps? 
		//private  XMLFacade to null in controller
		// XMLFacade = new XMLFacade() in init method
		//then set URL and call Deconstruct in a method.
		XMLFacade xmlf = new XMLFacade("https://mbchb.dundee.ac.uk/category/year1/feed");
		xmlf.DeconstructXMLToPojo();
		
		//New Query
		xmlf.setUrl("https://mbchb.dundee.ac.uk/category/year2/feed");
		xmlf.DeconstructXMLToPojo();
		
	}
	
}
