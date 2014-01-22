package uk.co.kyleharrison.ehealth.unneeded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class xmlTest {

	public static void main(String[] arguments) {
		buildXMLDocument();
	}

	public static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static String readxmlFromUrl(String url) throws IOException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String xmlText = readAll(rd);

			// System.out.println(xmlText);
			return xmlText;
		} finally {
			is.close();
		}
	}

	public static void buildXMLDocument() {
		try {
			String xmlFile = readxmlFromUrl("https://mbchb.dundee.ac.uk/category/year1/feed");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(
					xmlFile)));
			doc.getDocumentElement().normalize();

			parseChannelList(doc);
			System.out.println("");
			parseItemList(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void parseChannelList(Document doc) {
		NodeList nList2 = doc.getElementsByTagName("channel");

		for (int i = 0; i < nList2.getLength(); i++) {

			Node node = nList2.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) node;

				System.out.println(getTagValue("title", eElement));
			}
		}
	}

	private static void parseItemList(Document doc) {
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
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

}
