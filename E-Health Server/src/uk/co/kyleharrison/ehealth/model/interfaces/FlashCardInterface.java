package uk.co.kyleharrison.ehealth.model.interfaces;

import uk.co.kyleharrison.ehealth.model.flashcards.QuestionSet;

public interface FlashCardInterface {

	public QuestionSet CreateQuestionSet();
	public void AddQuestionSet();
	public void RemoveQuestionSet();
	public void UpdateQuestionSet();
	public void CountQuestionSet();
	
	
}
