package uk.co.kyleharrison.ehealth.unneeded;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

import com.thoughtworks.xstream.XStream;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;

public class XstreamUtil {

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
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String xmlText = readAll(rd);

	      System.out.println(xmlText);      
	      return xmlText;
	    } finally {
	      is.close();
	    }
	  }
	
	public static void main(String [] arguments) throws IOException{
		
		String url ="https://mbchb.dundee.ac.uk/category/year1/feed";
		xstreamparse(url);

	}
	
	public static String getURLContent(String p_sURL)
	{
	    URL oURL;
	    URLConnection oConnection;
	    BufferedReader oReader;
	    String sLine;
	    StringBuilder sbResponse;
	    String sResponse = null;

	    try
	    {
	        oURL = new URL(p_sURL);
	        oConnection = oURL.openConnection();
	        oReader = new BufferedReader(new InputStreamReader(oConnection.getInputStream()));
	        sbResponse = new StringBuilder();

	        while((sLine = oReader.readLine()) != null)
	        {
	            sbResponse.append(sLine);
	        }

	        sResponse = sbResponse.toString();
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    }

	    return sResponse;
	}
	
	public static void xstreamparse(String url){
		XStream xstream = new XStream();
		String xml = null;
		try {
			xml = readxmlFromUrl(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RSSChannel joe = new RSSChannel();
		joe.setTitle("Hello World");
		
		// Renames a class to a name
	//	xstream.aliasType("rssItem", RSSItem.class);
		//xstream.aliasType("rssChannel",RSSChannel.class);
		//xstream.aliasType("rssGroup",RSSGroup.class);
		xstream.aliasField("channel", RSSChannel.class, "channel");
	//	xstream.aliasField("item", RSSItem.class, "item");
		
		//Rename field to something else
	//	xstream.aliasField("url", RSSItem.class, "link");
		
		//Setting variables of object
		/*
		try {
			joe.setLink(new URL("http://www.google.com"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}*/
		
		
		RSSChannel newJoe = null;
		//Decoding
		try{
		newJoe = (RSSChannel)xstream.fromXML(xml);
		}catch(NullPointerException ex){
			ex.getMessage();
		}catch(Exception e){
			e.getMessage();
		}
		
		System.out.print("New Joe = "+newJoe.getTitle());
	}
	
}
