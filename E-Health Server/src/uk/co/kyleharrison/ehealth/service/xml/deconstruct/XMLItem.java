package uk.co.kyleharrison.ehealth.service.xml.deconstruct;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.model.proxy.RSSItemProxy;
import uk.co.kyleharrison.ehealth.service.xml.util.XMlDocumentBuilder;

public class XMLItem extends XMlDocumentBuilder {

	private RSSItemProxy rip = new RSSItemProxy();
	private RSSItem ri = new RSSItem();
	private String[] tags ={"title","link","comments","pubDate","dc:creator",
			"catergory","description","content:encoded","wfw:commentRss","slash:comments"};
	private String [] results = new String[10];
	private ArrayList <RSSItem> rssIA;

	public XMLItem() {
		super();
		this.rssIA = new ArrayList <RSSItem>();
	}
	
	public ArrayList<RSSItem> CreateItemList(Document doc) {
		DeconstruxtXML(doc);
		return this.rssIA;
	}
	
	private void DeconstruxtXML(Document doc){
		this.setRi(new RSSItem());
		NodeList nList2 = doc.getElementsByTagName("item");
		
		System.out.println("Deconstructing XML");
		
		// Change back to nList2.getLength()
		for (int i = 0; i < nList2.getLength(); i++) {
			Node node = nList2.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				for(int j=0; j<tags.length; j++){
					try{
						// Debugging Comment
						//System.out.println(tags[j] + " \t\t" +getTagValue(tags[j], eElement));
						results[j] = getTagValue(tags[j], eElement);	
					//	System.out.println(tags[j] + " : "+results[j]);
					}catch(NullPointerException e){
						e.getMessage();
					}
				}
				RSSItem nRI = MapToPojo(results[0],results[1],results[2],results[3],results[4],results[5],results[6],
						results[7],results[8],results[9]);
				//Adding Pojo to ArrayList
				rssIA.add(nRI);
				
				//Create RSS Item
				//Add Item to ArrayList
				//Add Arraylist to Channel
			}
		}
	}
	
	public RSSItem MapToPojo(String title, String link, String comments, String pubDate, String creator, 
			String catergory, String description, String contentEncoded, String commentRss, String slashComments){
		
		this.rip = new RSSItemProxy();
		String _description = null;
		String _contentEncoded = null;
		int _slashComments = 0;
		URL _link=null;
		Date _pubDate = null;
		URL _comments = null;
		
		try{
			_link = this.castStringToURL(link);
			_pubDate = this.DC.convertStringToDate(pubDate);
			_comments = this.castStringToURL(comments);
			
			if(description!=null){
				_description = this.getHtmlParser().cleanHTML(description);
			}
			if(contentEncoded!=null){
				_contentEncoded = this.getHtmlParser().cleanHTML(contentEncoded);
			}
			
			try{
				_slashComments = Integer.parseInt(slashComments);
			}catch(Exception e){
				e.getMessage();
				_slashComments=0;
			}
		}catch(Exception e){
			e.getMessage();
		}
		
		return this.ri = rip.CreateItem(title, _link, _comments, _pubDate, creator, catergory, _description, 
				_contentEncoded, _slashComments);
		
	}

	public RSSItem getRi() {
		return ri;
	}

	public void setRi(RSSItem ri) {
		this.ri = ri;
	}

	public ArrayList <RSSItem> getRssIA() {
		return rssIA;
	}

	public void setRssIA(ArrayList <RSSItem> rssIA) {
		this.rssIA = rssIA;
	}
	
	
}
