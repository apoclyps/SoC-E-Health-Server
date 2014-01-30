package uk.co.kyleharrison.ehealth.controller.rest.flashcards;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public interface FlashCardInterface {
	public void JSONResponse(HttpServletResponse response, JSONObject jsonResponse,String callback);
	public JSONObject[] ConstructJSONArray(int subjectID) throws JSONException;
	public void ResponseBuilder(String yearID,String pageID,String callback, int subjectID,HttpServletResponse response);
}
