package uk.co.kyleharrison.ehealth.model.pojo;

public class FlashCard {

	private int cardID;
	private String cardSubject;
	private int questionNumber;
	private String question;
	private String answer;
	
	public FlashCard() {
		super();
		this.cardID = 0;
		this.cardSubject = null;
		this.questionNumber = 0;
		this.question = null;
		this.answer = null;
	}
	
	public FlashCard(int cardID, String cardSubject, int questionNumber,
			String question, String answer) {
		super();
		this.cardID = cardID;
		this.cardSubject = cardSubject;
		this.questionNumber = questionNumber;
		this.question = question;
		this.answer = answer;
	}

	public int getCardID() {
		return cardID;
	}

	public void setCardID(int cardID) {
		this.cardID = cardID;
	}

	public String getCardSubject() {
		return cardSubject;
	}

	public void setCardSubject(String cardSubject) {
		this.cardSubject = cardSubject;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
