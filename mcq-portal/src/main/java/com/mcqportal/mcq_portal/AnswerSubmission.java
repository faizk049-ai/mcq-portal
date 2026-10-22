package com.mcqportal.mcq_portal;

public class AnswerSubmission {

    private Long questionId;
    private String selectedOption;

    public AnswerSubmission() {
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }
}
