package uk.co.kyleharrison.ehealth.service.jackson.construct;

//import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import uk.co.kyleharrison.ehealth.model.pojo.RSSChannel;

public class JSONChannel {

	private String channelJSON = null;
	
	public void writeToJson(RSSChannel channel)
	{
		ObjectMapper mapper = new ObjectMapper();
		try {
			setChannelJSON(mapper.writeValueAsString(channel));
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
}
