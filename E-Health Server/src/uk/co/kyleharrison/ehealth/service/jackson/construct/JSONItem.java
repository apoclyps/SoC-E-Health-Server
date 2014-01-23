package uk.co.kyleharrison.ehealth.service.jackson.construct;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;

public class JSONItem {

	private String itemJSON = null;
	private JSONObject itemOut;

	public JSONObject writeToJson(RSSItem item) throws JSONException {
		
		ObjectMapper mapper = new ObjectMapper();
		try {
				setItemJSONString(mapper.writeValueAsString(item));
				setItemOut(getItemJSONString());
				
			// mapper.writeValue(new File("uni-json.json"), item);
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
		return getItemOut();
	}

	public String getItemJSONString() {
		return itemJSON;
	}

	public void setItemJSONString(String itemJSON) {
		this.itemJSON = itemJSON;
	}

	public JSONObject getItemOut() {
		return itemOut;
	}

	public void setItemOut(String itemJSONString) throws JSONException {
		this.itemOut = new JSONObject(itemJSONString);
	}

}
