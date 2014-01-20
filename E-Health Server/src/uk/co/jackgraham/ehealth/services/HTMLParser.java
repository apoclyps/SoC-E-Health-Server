package uk.co.jackgraham.ehealth.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import uk.co.jackgraham.ehealth.controller.interfaces.HTMLParserInterface;

public class HTMLParser implements HTMLParserInterface {

	@Override
	public String parseHTML(String html) {
		// TODO Auto-generated method stub		
		Document doc = Jsoup.parse(html);
		
		String cleanText = doc.body().text();
		
		return cleanText;
	}

}
