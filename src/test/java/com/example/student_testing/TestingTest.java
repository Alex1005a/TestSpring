package com.example.student_testing;

import com.example.student_testing.domain.Question;
import com.example.student_testing.domain.TestId;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TestingTest {

    private final String validName = "Name";

    private final String validDescription = "It is description for Test";

    private final LocalDateTime validCreatedTime = LocalDateTime.now();

    private final LocalDateTime validEndTime = LocalDateTime.now().plusMinutes(1);

    private LinkedHashSet<Question> validQuestions() {
        LinkedHashSet<String> optionsAnswer = new LinkedHashSet<>();
        optionsAnswer.add("answer1");
        optionsAnswer.add("answer2");
        optionsAnswer.add("answer3");
        optionsAnswer.add("answer4");

        LinkedHashSet<Question> questions = new LinkedHashSet<>();

        questions.add(new Question("Question1", optionsAnswer, 0));
        questions.add(new Question("Question2", optionsAnswer, 0));
        questions.add(new Question("Question3", optionsAnswer, 0));

        return questions;
    }

    private com.example.student_testing.domain.Test fixtureTest() {
        return new com.example.student_testing.domain.Test(TestId.generate(),
                validName,
                validDescription,
                validQuestions(),
                validCreatedTime,
                validEndTime);
    }

    private com.example.student_testing.domain.Test fixtureTest(TestId testId) {
        return new com.example.student_testing.domain.Test(testId,
                validName,
                validDescription,
                validQuestions(),
                validCreatedTime,
                validEndTime);
    }

    @Test
    public void testExpectedException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new com.example.student_testing.domain.Test(TestId.generate(),
                    validName,
                    validDescription,
                    new LinkedHashSet<>(),
                    validCreatedTime,
                    validEndTime);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new com.example.student_testing.domain.Test(TestId.generate(),
                    "a",
                    validDescription,
                    validQuestions(),
                    validCreatedTime,
                    validEndTime);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new com.example.student_testing.domain.Test(TestId.generate(),
                    validName,
                    "not valid desc",
                    validQuestions(),
                    validCreatedTime,
                    validEndTime);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new com.example.student_testing.domain.Test(TestId.generate(),
                    validName,
                    validDescription,
                    validQuestions(),
                    LocalDateTime.now(),
                    LocalDateTime.now().minusMinutes(1));
        });
    }

    @Test
    public void testTestIsFinish() {
        com.example.student_testing.domain.Test test = fixtureTest();

        assertFalse(test.isFinish());

        try {
            Field field = test.getClass().getDeclaredField("endTime");
            field.setAccessible(true);
            field.set(test, LocalDateTime.now().minusMinutes(1));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        assertTrue(test.isFinish());
    }

    @Test
    public void testEqual() {
        assertFalse( fixtureTest().equals(fixtureTest()) );

        UUID testId = UUID.randomUUID();

        assertEquals( fixtureTest(new TestId(testId)), fixtureTest(new TestId(testId)) );
    }
}
