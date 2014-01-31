package uk.co.kyleharrison.ehealth.model.inmemory.proxy;

import java.net.URL;
import java.util.Date;

import uk.co.ehealth.system.interfaces.RSSItemInterface;
import uk.co.kyleharrison.ehealth.model.inmemory.rss.RSSItem;

public class RSSItemProxy implements RSSItemInterface {

	RSSItem ri = null;
	
	@Override
	public String generateItemUID(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String parseDescription(String description) {
		// TODO Auto-generated method stub
		return null;
	}

	public RSSItem CreateItem(String title, URL link, URL comments,
			Date pubDate, String creator, String catergory,
			String description, String contentEncoded, int slashComments) {
		
		this.ri = new RSSItem(title,link,comments, pubDate,creator, catergory,description, contentEncoded,slashComments);
		//System.out.println("Item : " +ri.getTitle());
		//System.out.println(ri.getDescription());
		return this.ri;
		
	}

}
