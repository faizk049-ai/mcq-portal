
package com.mcqportal.mcq_portal;

public class AnswerResult {

    private Long questionId;
    private String selectedOption;
    private boolean correct;
    private String message;

    public AnswerResult() {
    }

    public AnswerResult(Long questionId, String selectedOption,
                        boolean correct, String message) {
        this.questionId = questionId;
        this.selectedOption = selectedOption;
        this.correct = correct;
        this.message = message;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getMessage() {
        return message;
    }
}
