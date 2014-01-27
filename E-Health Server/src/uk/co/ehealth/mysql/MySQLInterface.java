package uk.co.ehealth.mysql;
import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;

public interface MySQLInterface {

	public boolean insertChannel(RSSChannel rssChannel);
	
	public RSSChannel selectChannel();
	public RSSItem selectItem();	
	public RSSChannel [] selectChannels();
	public RSSItem [] selectItems();

	boolean insertItem(RSSItem rssItem);

}
