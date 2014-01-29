package uk.co.kyleharrison.ehealth.controller.rest.flashcards;

import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class FlashCardControllerUtil extends FlashCardController implements FlashCardInterface  {

	private static final long serialVersionUID = 1L;

	@Override
	public void JSONResponse(HttpServletResponse response,
			JSONObject jsonResponse, String callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject[] ConstructJSONArray(URL url) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ResponseBuilder(String yearID, String pageID, String callback,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int ParseYearValue(String yearID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void ResponsePresistentStorage(String yearID, String pageID) {
		// TODO Auto-generated method stub
		
	}

	

}
