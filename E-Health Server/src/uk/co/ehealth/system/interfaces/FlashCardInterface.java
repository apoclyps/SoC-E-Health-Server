package uk.co.ehealth.system.interfaces;

import uk.co.kyleharrison.ehealth.model.inmemory.flashcards.QuestionSet;

public interface FlashCardInterface {

	public QuestionSet CreateQuestionSet();
	public void AddQuestionSet();
	public void RemoveQuestionSet();
	public void UpdateQuestionSet();
	public void CountQuestionSet();
	
	
}
