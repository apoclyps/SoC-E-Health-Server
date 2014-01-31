package uk.co.kyleharrison.ehealth.service.jackson.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.kyleharrison.ehealth.model.pojo.RSSGroup;

public class JSONGroup {

private String groupJSON = null;
private JSONObject groupOut;
	
	public void writeToJson(RSSGroup group) throws JSONException
	{
		ObjectMapper mapper = new ObjectMapper();
		try {
			setGroupJSON(mapper.writeValueAsString(group));
			setGroupOut(getGroupJSON());
		//	mapper.writeValue(new File("uni-json.json"), group);
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

	public String getGroupJSON() {
		return groupJSON;
	}

	public void setGroupJSON(String groupJSON) {
		this.groupJSON = groupJSON;
	}

	public JSONObject getGroupOut() {
		return groupOut;
	}

	public void setGroupOut(String groupOutString) throws JSONException {
		this.groupOut = new JSONObject(groupOut);
	}
	
}
