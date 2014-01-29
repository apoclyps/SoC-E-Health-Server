package uk.co.kyleharrison.ehealth.model.flashcards;

public class FlashCard {

	private int cardID;
	private int subjectID;
	private String cardSubject;
	private int yearStudied;
	private int lectureNumber;
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
	
	public FlashCard(int cardID, int subjectID, String cardSubject,
			int yearStudied, int lectureNumber, int questionNumber,
			String question, String answer) {
		super();
		this.cardID = cardID;
		this.subjectID = subjectID;
		this.cardSubject = cardSubject;
		this.yearStudied = yearStudied;
		this.lectureNumber = lectureNumber;
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

	public int getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(int subjectID) {
		this.subjectID = subjectID;
	}

	public int getYearStudied() {
		return yearStudied;
	}

	public void setYearStudied(int yearStudied) {
		this.yearStudied = yearStudied;
	}

	public int getLectureNumber() {
		return lectureNumber;
	}

	public void setLectureNumber(int lectureNumber) {
		this.lectureNumber = lectureNumber;
	}

}
