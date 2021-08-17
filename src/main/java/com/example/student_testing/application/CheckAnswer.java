package com.example.student_testing.application;

import an.awesome.pipelinr.Command;
import com.example.student_testing.domain.Question;
import com.example.student_testing.domain.Test;
import com.example.student_testing.domain.TestId;
import com.example.student_testing.domain.TestRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CheckAnswer implements Command<Optional<Boolean>> {

    private final TestId id;

    private final int numOfQuestion;

    private final int numOfAnswer;

    public CheckAnswer(UUID id, int numOfQuestion, int numOfAnswer) {
        this.id = new TestId(id);
        this.numOfQuestion = numOfQuestion;
        this.numOfAnswer = numOfAnswer;
    }

    public TestId getId() {
        return id;
    }

    public int getNumOfQuestion() {
        return numOfQuestion;
    }

    public int getNumOfAnswer() {
        return numOfAnswer;
    }
}
@Component
class CheckAnswerHandler implements Command.Handler<CheckAnswer, Optional<Boolean>> {

    private final TestRepository testRepository;

    public CheckAnswerHandler(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public Optional<Boolean> handle(CheckAnswer checkAnswer) {
        Optional<Test> testOpt = testRepository.testOfId(checkAnswer.getId());
        return testOpt.map(test -> Arrays.copyOf(test.questions().toArray(), test.questions().size(), Question[].class))
               .map(questions -> questions[checkAnswer.getNumOfAnswer() - 1].isCorrectAnswer(checkAnswer.getNumOfAnswer()));

    }
}
