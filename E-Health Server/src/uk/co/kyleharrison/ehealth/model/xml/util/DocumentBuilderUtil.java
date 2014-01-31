package uk.co.kyleharrison.ehealth.model.xml.util;

import java.io.StringReader;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class DocumentBuilderUtil {

	static DocumentBuilderFactory dbFactory;
	static DocumentBuilder dBuilder;
	
	public static Document buildXMLDocument(String xmlContent) {
		try {
			String xmlFile = xmlContent;

			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(	xmlFile)));
			doc.getDocumentElement().normalize();
			
			return doc;
		} catch(IllegalStateException ise){
			System.out.println("Document Builder Error"+ise.getMessage() +" : "+new Date().toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
