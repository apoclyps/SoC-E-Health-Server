package uk.co.kyleharrison.ehealth.model.flashcards;

import java.util.ArrayList;

import uk.co.kyleharrison.ehealth.model.pojo.FlashCard;

public class QuestionSet {

	private ArrayList<FlashCard> questionSet = new ArrayList<FlashCard>();

	public QuestionSet() {
		super();
	}
	
	public QuestionSet(ArrayList<FlashCard> questionSet) {
		super();
		this.questionSet = questionSet;
	}

	public ArrayList<FlashCard> getQuestionSet() {
		return questionSet;
	}

	public void setQuestionSet(ArrayList<FlashCard> questionSet) {
		this.questionSet = questionSet;
	}
	
}
