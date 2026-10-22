package com.mcqportal.mcq_portal;

public class StudentQuestion 
{
	private Long id;
	private String QuestionText;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	
	public StudentQuestion() {
		
	}
	 public StudentQuestion(Question question) {
	        this.id = question.getId();
	        this.QuestionText = question.getQuestionText();
	        this.optionA = question.getOptionA();
	        this.optionB = question.getOptionB();
	        this.optionC = question.getOptionC();
	        this.optionD = question.getOptionD();
	    }
	 public Long getId() {
		 return id;
	 }
	 public String getQuestiontext() {
		 return QuestionText;
	 }
	 public String getOptionA() {
		 return optionA;
	 }
	 public String getOptionB() {
		 return optionB;
	 }
	 public String getOptionC() {
		 return optionC;
	 }
	 public String getOptionD() {
		 return optionD;
	 }

	

	
	

}
