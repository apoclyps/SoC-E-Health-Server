package uk.co.ehealth.system.interfaces;

import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public interface RestInterface {

	public JSONObject[] getFeed(String feedID) throws JSONException;
	public JSONObject[] getFeedJSON(URL url) throws JSONException;
}
