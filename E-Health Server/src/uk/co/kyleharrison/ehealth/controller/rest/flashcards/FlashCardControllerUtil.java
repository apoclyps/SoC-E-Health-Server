package uk.co.kyleharrison.ehealth.controller.rest.flashcards;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.ehealth.storage.mysql.MySQLFacade;
import uk.co.kyleharrison.ehealth.model.flashcards.FlashCard;
import uk.co.kyleharrison.ehealth.model.flashcards.QuestionSet;
import uk.co.kyleharrison.ehealth.service.jackson.model.JSONItem;

public class FlashCardControllerUtil extends FlashCardController implements FlashCardInterface  {

	private static final long serialVersionUID = 1L;

	protected JSONItem jsonItem;
	protected QuestionSet questionSet;
	protected FlashCard flashCard;
	protected JSONObject[] jsonItemArray;
	protected MySQLFacade mysqlFacade;

	public FlashCardControllerUtil() {
		super();
		this.jsonItem = new JSONItem();
		this.questionSet = new QuestionSet();
		this.flashCard = new FlashCard();
	//	this.jsonItemArray = new JSONObject [] () ;
	}
	
	
	public FlashCardControllerUtil(JSONItem jsonItem, QuestionSet questionSet,
			FlashCard flashCard, JSONObject[] jsonItemArray) {
		super();
		this.jsonItem = jsonItem;
		this.questionSet = questionSet;
		this.flashCard = flashCard;
		this.jsonItemArray = jsonItemArray;
	}

	public void ResponseBuilder(String year, String page,String callback,
			HttpServletResponse response) {
		try {
			// GET FLASHCARDS FROM DATABASE
			
		//	this.questionSet.setQuestionSet(questionSet);
			
			JSONObject responseObject = new JSONObject();
			JSONObject[] jsonItemsArray = ConstructJSONArray();

			Date now = new Date();

			// Additional fields can only be added once RSS Channel has been
			// updated "ConstructJsonArray"
			responseObject.put("quantity", this.questionSet.getQuestionSet().size());

			// Items array
			responseObject.put("items", jsonItemsArray);

			JSONResponse(response, responseObject,callback);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public JSONObject[] ConstructJSONArray() throws JSONException {
		this.jsonItem = new JSONItem();
	//	this.xmlFacade.setUrl(url.toString());
	//	this.questionSet = this.xmlFacade.DeconstructXMLToPojo();
/*
		for (int x = 0; x < this.jsonItemArray.length; x++) {
			this.flashCard = questionSet.getItem_list().get(x);
			this.jsonItemArray[x] = this.jsonItem.writeToJson(this.flashCard);
		}
		*/
		return jsonItemArray;
	}

	public void JSONResponse(HttpServletResponse response,
			JSONObject jsonResponse,String callback) {
		if (jsonResponse != null) {
			response.setContentType("text/x-json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = null;
			response.setContentType("application/json");

			try {
				out = response.getWriter();
			//	out.print(jsonResponse);
				out.print(callback+"(" + jsonResponse+ ");");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("JSON Response Returned");
	}

	

}
