package com.mcqportal.mcq_portal;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;
    

@GetMapping("/{id}")
public Question getQuestionById(@PathVariable Long id) {
    return questionRepository.findById(id)
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Question not found: " + id
                    ));
}

@GetMapping("/student")
public List<StudentQuestion> getStudentQuestions() {
    return questionRepository.findAll()
            .stream()
            .map(StudentQuestion::new)
            .collect(Collectors.toList());
}


@DeleteMapping("/{id}")
public String deleteQuestion(@PathVariable Long id) {
	
    if (!questionRepository.existsById(id)) {
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Question not found: " + id
        );
    }

    questionRepository.deleteById(id);
    return "Question deleted successfully: " + id;
}

@PutMapping("/{id}")
public Question updateQuestion(
        @PathVariable Long id,
        @RequestBody Question updatedQuestion) {

    Question existingQuestion = questionRepository.findById(id)
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Question not found: " + id
                    ));

    existingQuestion.setQuestionText(updatedQuestion.getQuestionText());
    existingQuestion.setOptionA(updatedQuestion.getOptionA());
    existingQuestion.setOptionB(updatedQuestion.getOptionB());
    existingQuestion.setOptionC(updatedQuestion.getOptionC());
    existingQuestion.setOptionD(updatedQuestion.getOptionD());
    existingQuestion.setCorrectedOption(updatedQuestion.getCorrectedOption());

    return questionRepository.save(existingQuestion);
}




    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping
    public Question addQuestion(@RequestBody Question question) {
        return questionRepository.save(question);
    }

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

@PostMapping("/submit")
public AnswerResult submitAnswer(
        @RequestBody AnswerSubmission submission) {

    Question question = questionRepository
            .findById(submission.getQuestionId())
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Question not found: "
                                    + submission.getQuestionId()
                    ));

    String selectedOption = submission.getSelectedOption();

    if (selectedOption == null ||
            !selectedOption.matches("(?i)[ABCD]")) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Selected option must be A, B, C, or D"
        );
    }

    boolean correct = selectedOption.equalsIgnoreCase(
            question.getCorrectedOption()
    );

    String message = correct
            ? "Correct answer!"
            : "Incorrect answer.";

    return new AnswerResult(
            question.getId(),
            selectedOption.toUpperCase(),
            correct,
            message
    );
}

}
