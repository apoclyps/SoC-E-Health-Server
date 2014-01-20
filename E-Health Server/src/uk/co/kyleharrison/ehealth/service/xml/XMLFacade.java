package uk.co.kyleharrison.ehealth.service.xml;

import java.io.IOException;

import uk.co.kyleharrison.ehealth.service.xml.util.URLReader;
import uk.co.kyleharrison.ehealth.service.xml.util.XMlDocumentBuilder;

public class XMLFacade {

	public static void main(String [] arugments){
		String response=null;
		
		URLReader urlReader = new URLReader();
		urlReader.setUrl("https://mbchb.dundee.ac.uk/category/year1/feed");
		
		try {
			response = urlReader.readxmlFromUrl();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		XMlDocumentBuilder xmlDocB = new XMlDocumentBuilder(response.toString());
		xmlDocB.buildXMLDocument();
		
	}
	
}
