package uk.co.kyleharrison.ehealth.model.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.kyleharrison.ehealth.model.inmemory.flashcards.FlashCard;

public class JSONFlashCard {

	private JSONObject jsonFlashcard;
	private String flashCardString;

	public JSONFlashCard() {
		super();
		jsonFlashcard = null;
		flashCardString = null;
	}

	public JSONObject writeToJson(FlashCard flashCard) throws JSONException {

		ObjectMapper mapper = new ObjectMapper();
		try {
			setFlashCardString(mapper.writeValueAsString(flashCard));
			setFlashCardOut(getFlashCardString());
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
		return getFlashCardOut();
	}

	private JSONObject getFlashCardOut() {
		// TODO Auto-generated method stub
		return jsonFlashcard;
	}

	public void setFlashCardOut(String flashCardString2) throws JSONException {
		// TODO Auto-generated method stub
		this.jsonFlashcard = new JSONObject(flashCardString2);
	}

	public JSONObject getJsonFlashcard() {
		return jsonFlashcard;
	}

	public void setJsonFlashcard(JSONObject jsonFlashcard) {
		this.jsonFlashcard = jsonFlashcard;
	}

	public String getFlashCardString() {
		return flashCardString;
	}

	public void setFlashCardString(String flashCardString) {
		this.flashCardString = flashCardString;
	}

}
