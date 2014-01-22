package uk.co.kyleharrison.ehealth.service.jackson.construct;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;

public class JSONItem {

	
private String itemJSON = null;
	
	public void writeToJson(RSSItem item)
	{
		ObjectMapper mapper = new ObjectMapper();
		try {
			setItemJSON(mapper.writeValueAsString(item));
		//	mapper.writeValue(new File("uni-json.json"), item);
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

	public String getItemJSON() {
		return itemJSON;
	}

	public void setItemJSON(String itemJSON) {
		this.itemJSON = itemJSON;
	}

}
