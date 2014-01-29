package uk.co.kyleharrison.ehealth.service.xml;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.w3c.dom.Document;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.services.xml.util.URLReader;

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

	public RSSChannel DeconstructXMLToPojo(){
		if(this.url==null){
			return null;
		}
		this.urlReader = new URLReader();
		this.urlReader.setUrl(this.url.toString());
		
		try {
			this.response = urlReader.readxmlFromUrl();
		} catch (IOException e) {
			System.out.println("Exception in XMLFacade trying to DeconstructXMLToPojo whilst reading XML from URL");
			e.printStackTrace();
		}

		// Creating Document from XML Data
		XMlDocumentBuilder xmlDocB = new XMlDocumentBuilder(this.response.toString());
		doc = xmlDocB.buildXMLDocument();
		if(doc!=null){
			xmlDocB.extractXMLData(doc);
		}
		return xmlDocB.getRc();
	}

}
