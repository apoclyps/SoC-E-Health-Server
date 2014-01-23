package uk.co.kyleharrison.ehealth.service.jackson.util;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;
import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;
import uk.co.kyleharrison.ehealth.service.jackson.construct.JSONChannel;
import uk.co.kyleharrison.ehealth.service.jackson.construct.JSONGroup;
import uk.co.kyleharrison.ehealth.service.jackson.construct.JSONItem;

public class JSONDocumentBuilder {

	private String JSONContent=null;
	private JSONChannel JSONChannel;
	private JSONItem JSONItem;
	private JSONGroup JSONGroup;
	private JSONObject channelJsonData;
	private RSSChannel rssChannel;

	public JSONDocumentBuilder() {
		super();
		this.channelJsonData = new JSONObject();
		this.rssChannel = new RSSChannel();
	}
	
	public JSONDocumentBuilder(RSSChannel rssChannel) {
		super();
		this.channelJsonData = new JSONObject();
		this.rssChannel = rssChannel;
	}
	
	public JSONObject BuildJSONDocument(RSSChannel rssChannel){
		
		this.rssChannel=rssChannel;

		//JSONArray jsa = generateJSONItemArray();
		
		return this.channelJsonData;
	}
	
	public JSONArray generateJSONItemArray(){
		//ArrayList<RSSItem> rssItemArrayList = this.rssChannel.getItem_list();
		
		//Convert ArrayList to JsonArray
		
		
		RSSItem user = new RSSItem();
		ObjectMapper mapper = new ObjectMapper();
	 
		try {
	 
			// convert user object to json string, and save to a file
			mapper.writeValue(new File("c:\\user.json"), user);
	 
			// display to console
			System.out.println(mapper.writeValueAsString(user));
	 
		} catch (JsonGenerationException e) {
	 
			e.printStackTrace();
	 
		} catch (JsonMappingException e) {
	 
			e.printStackTrace();
	 
		} catch (IOException e) {
	 
			e.printStackTrace();
	 
		}
		
		
		JSONArray jsa = new JSONArray();
		
		return jsa;
	}

	public String getJSONContent() {
		return JSONContent;
	}

	public void setJSONContent(String jSONContent) {
		JSONContent = jSONContent;
	}

	public JSONChannel getJSONChannel() {
		return JSONChannel;
	}

	public void setJSONChannel(JSONChannel jSONChannel) {
		JSONChannel = jSONChannel;
	}

	public JSONItem getJSONItem() {
		return JSONItem;
	}

	public void setJSONItem(JSONItem jSONItem) {
		JSONItem = jSONItem;
	}

	public JSONGroup getJSONGroup() {
		return JSONGroup;
	}

	public void setJSONGroup(JSONGroup jSONGroup) {
		JSONGroup = jSONGroup;
	}

	public JSONObject getChannelJsonData() {
		return channelJsonData;
	}

	public void setChannelJsonData(JSONObject channelJsonData) {
		this.channelJsonData = channelJsonData;
	}

	public RSSChannel getRssChannel() {
		return rssChannel;
	}

	public void setRssChannel(RSSChannel rssChannel) {
		this.rssChannel = rssChannel;
	}
	
}
