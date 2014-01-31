package uk.co.ehealth.storage.mysql;
import java.util.ArrayList;

import uk.co.kyleharrison.ehealth.model.inmemory.rss.RSSChannel;
import uk.co.kyleharrison.ehealth.model.inmemory.rss.RSSItem;

public interface MySQLInterface {

	public boolean insertChannel(RSSChannel rssChannel);
	
	public RSSChannel selectChannel();
	public RSSItem selectItem();	
	public RSSChannel [] selectChannels();
	public ArrayList<RSSItem> selectItems();

	boolean insertItem(RSSItem rssItem);

	public ArrayList<RSSItem> selectItemsFromYear(String yearID);

}
