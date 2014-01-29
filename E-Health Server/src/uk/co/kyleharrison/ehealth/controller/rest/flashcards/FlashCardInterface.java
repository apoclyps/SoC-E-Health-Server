package uk.co.kyleharrison.ehealth.controller.rest.flashcards;

import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public interface FlashCardInterface {
	public void JSONResponse(HttpServletResponse response, JSONObject jsonResponse,String callback);
	public JSONObject[] ConstructJSONArray() throws JSONException;
	public void ResponseBuilder(String yearID,String pageID,String callback,HttpServletResponse response);
}