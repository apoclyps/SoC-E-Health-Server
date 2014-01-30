package uk.co.kyleharrison.ehealth.services.xml.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class DocumentBuilderUtil {

	public static Document buildXMLDocument(String xmlContent) {
		try {
			String xmlFile = xmlContent;

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
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
	
}
