package uk.co.kyleharrison.ehealth.model.json;

//import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.kyleharrison.ehealth.model.inmemory.rss.RSSChannel;

public class JSONChannel {

	private String channelJSON = null;
	private JSONObject channelOut;
	
	public void writeToJson(RSSChannel channel) throws JSONException
	{
		ObjectMapper mapper = new ObjectMapper();
		try {
			setChannelJSON(mapper.writeValueAsString(channel));
			setChannelOut(getChannelJSON());
		//	mapper.writeValue(new File("uni-json.json"), channel);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getChannelJSON() {
		return channelJSON;
	}

	public void setChannelJSON(String channelJSON) {
		this.channelJSON = channelJSON;
	}

	public JSONObject getChannelOut() {
		return channelOut;
	}

	public void setChannelOut(String channelOutString) throws JSONException {
		this.channelOut = new JSONObject(channelOutString);
	}
}
