package uk.co.kyleharrison.ehealth.service.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class xmlTest {

	public static void main(String [] arguments){
		buildXMLDocument();
	}
	
	public static void buildXMLDocument(){
		 try {

		        File fXmlFile = new File("c:\\Users\\Apoclyps\\JavaProjectsServer\\SoC-E-Health-Server\\E-Health Server\\src\\test.xml");
		        DocumentBuilderFactory dbFactory = DocumentBuilderFactory
		                .newInstance();
		        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		        Document doc = dBuilder.parse(fXmlFile);
		        doc.getDocumentElement().normalize();

		        parseChannelList(doc);
		        System.out.println("");
		        parseItemList(doc);
		       
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	}
	
	@SuppressWarnings("unused")
	private static void parseChannelList(Document doc){
		 NodeList nList2 = doc.getElementsByTagName("channel");

	        for (int i = 0; i < nList2.getLength(); i++) {

	            Node node = nList2.item(i);
	            if (node.getNodeType() == Node.ELEMENT_NODE) {

	                Element eElement = (Element) node;

	                System.out.println(getTagValue("title", eElement));
	            }
	        }
	}
	
	@SuppressWarnings("unused")
	private static void parseItemList(Document doc){
		NodeList nList = doc.getElementsByTagName("item");

        for (int i = 0; i < nList.getLength(); i++) {

            Node node = nList.item(i);
   
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                
                // Get values
                System.out.println(getTagValue("title", eElement));
             
            }
        }
	}

	 // Gets string value from an element tag
	 private static String getTagValue(String sTag, Element eElement) {
	        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

	            Node nValue = (Node) nlList.item(0);

	        return nValue.getNodeValue();
	      }
	 
}
