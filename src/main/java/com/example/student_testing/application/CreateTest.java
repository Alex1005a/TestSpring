package com.example.student_testing.application;

import an.awesome.pipelinr.Command;
import com.example.student_testing.domain.Question;
import com.example.student_testing.domain.Test;
import com.example.student_testing.domain.TestId;
import com.example.student_testing.domain.TestRepository;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;

public class CreateTest implements Command<TestId> {

    private final String name;

    private final String description;

    private final LinkedHashSet<Question> questions;

    private final int hoursToEnd;

    public CreateTest(String name, String description, LinkedHashSet<Question> questions, int hoursToEnd) {
        this.name = name;
        this.description = description;
        this.questions = questions;
        this.hoursToEnd = hoursToEnd;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LinkedHashSet<Question> getQuestions() {
        return questions;
    }


    public int getHoursToEnd() {
        return hoursToEnd;
    }
}

@Component
class CreateTestHandler implements Command.Handler<CreateTest, TestId> {

    private final TestRepository testRepository;

    public CreateTestHandler(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public TestId handle(CreateTest createTest) {
        Test test = new Test(
                TestId.generate(),
                createTest.getName(),
                createTest.getDescription(),
                createTest.getQuestions(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(createTest.getHoursToEnd())
                );
        testRepository.save(test);

        return test.Id();
    }
}
