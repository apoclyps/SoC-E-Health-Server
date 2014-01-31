package uk.co.kyleharrison.ehealth.controller.rest.flashcards;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.ehealth.storage.mysql.MySQLFacade;
import uk.co.kyleharrison.ehealth.model.inmemory.flashcards.FlashCard;
import uk.co.kyleharrison.ehealth.model.inmemory.flashcards.QuestionSet;
import uk.co.kyleharrison.ehealth.model.json.JSONFlashCard;

public class FlashCardControllerUtil extends FlashCardController implements
		FlashCardInterface {

	private static final long serialVersionUID = 1L;

	protected uk.co.kyleharrison.ehealth.model.json.JSONFlashCard flashcardItem;
	protected QuestionSet questionSet;
	protected FlashCard flashCard;
	protected JSONObject[] jsonItemArray;
	protected MySQLFacade mysqlFacade;

	public FlashCardControllerUtil() {
		super();
		this.flashcardItem = new JSONFlashCard();
		this.questionSet = new QuestionSet();
		this.flashCard = new FlashCard();
		this.mysqlFacade = new MySQLFacade();
		this.jsonItemArray = new JSONObject[10];
	}

	public FlashCardControllerUtil(JSONFlashCard flashcardItem,
			QuestionSet questionSet, FlashCard flashCard,
			JSONObject[] jsonItemArray) {
		super();
		this.flashcardItem = flashcardItem;
		this.questionSet = questionSet;
		this.flashCard = flashCard;
		this.jsonItemArray = jsonItemArray;
		this.mysqlFacade = new MySQLFacade();
	}

	public void ResponseBuilder(String callback,int subjectID, HttpServletResponse response) {
		try {
			JSONObject responseObject = new JSONObject();
			JSONObject[] jsonItemsArray = ConstructJSONArray(subjectID);

			responseObject.put("quantity", this.questionSet.getQuestionSet().size());
			responseObject.put("items", jsonItemsArray);

			JSONResponse(response, responseObject, callback);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public JSONObject[] ConstructJSONArray(int subjectID) throws JSONException {
		this.flashcardItem = new JSONFlashCard();

		ArrayList<FlashCard> fcItems = mysqlFacade.selectRandomFlashCardBySubject(subjectID);

		try {
			this.jsonItemArray = new JSONObject[fcItems.size()];
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		for (int x = 0; x < fcItems.size(); x++) {
			this.flashCard = fcItems.get(x);
			try {
				this.jsonItemArray[x] = this.flashcardItem
						.writeToJson(this.flashCard);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return jsonItemArray;
	}

	public void JSONResponse(HttpServletResponse response,
			JSONObject jsonResponse, String callback) {
		if (jsonResponse != null) {
			response.setContentType("text/x-json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = null;
			response.setContentType("application/json");

			try {
				out = response.getWriter();
				out.print(callback + "(" + jsonResponse + ");");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
