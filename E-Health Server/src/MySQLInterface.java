import java.net.URL;
import java.sql.SQLException;
import java.util.Date;

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
