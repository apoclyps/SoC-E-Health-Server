package uk.co.kyleharrison.ehealth.service.xml;

import java.net.MalformedURLException;

import org.apache.tomcat.util.net.URL;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSGroup;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;

import com.thoughtworks.xstream.XStream;

public class XstreamUtil {

	public static void main(String [] arguments){
		XStream xstream = new XStream();

		
		RSSItem joe = new RSSItem();
		joe.setTitle("Hello World");
		
		// Renames a class to a name
		xstream.aliasType("rssItem", RSSItem.class);
		xstream.aliasType("rssChannel",RSSChannel.class);
		xstream.aliasType("rssGroup",RSSGroup.class);
		
		//Rename field to something else
		xstream.aliasField("url", RSSItem.class, "link");
		
		//Setting variables of object
		try {
			joe.setLink(new URL("http://www.google.com"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		// Encoding
		String xml = xstream.toXML(joe);
		System.out.println(xml.toString());
		
		//Decoding
		RSSItem newJoe = (RSSItem)xstream.fromXML(xml);
		System.out.print("New Joe = "+newJoe.getTitle());
		
		
		
		
	}
	
}
