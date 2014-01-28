package uk.co.kyleharrison.ehealth.controller.rest;

import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public interface RequestControllerInterface {
	public void JSONResponse(HttpServletResponse response, JSONObject jsonResponse);
	public JSONObject[] ConstructJSONArray(URL url) throws JSONException;
	public void ResponseBuilder(String yearID,String pageID,HttpServletResponse response);
	public int ParseYearValue(String yearID);
	public void ResponsePresistentStorage(String yearID, String pageID);
}
