package com.example.student_testing;

import com.example.student_testing.domain.Question;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

    private LinkedHashSet<String> fixedOptionsAnswer() {
        String correctAnswer = "answer1";
        LinkedHashSet<String> optionsAnswer = new LinkedHashSet<>();
        optionsAnswer.add(correctAnswer);
        optionsAnswer.add("answer2");
        optionsAnswer.add("answer3");
        optionsAnswer.add("answer4");

        return optionsAnswer;
    }

    private Question fixedQuestion() {
        return new Question("Question", fixedOptionsAnswer(), 0);
    }

    @Test
    public void testIsCorrectAnswer() {
        String correctAnswer = "answer1";

        Question question = fixedQuestion();
        assertTrue(question.isCorrectAnswer(correctAnswer));
    }

    @Test
    public void testExpectedException() {
        assertThrows(IllegalArgumentException.class, () -> {
            LinkedHashSet<String> optionsAnswer = new LinkedHashSet<>();
            optionsAnswer.add("answer");

            new Question("Question", optionsAnswer, 0);
        });


        assertThrows(IllegalArgumentException.class, () -> new Question("Question", fixedOptionsAnswer(), 5));
    }

    @Test
    public void testHashCode() {
        String correctAnswer = "correct answer";
        LinkedHashSet<String> optionsAnswer = new LinkedHashSet<>();

        optionsAnswer.add(correctAnswer);
        optionsAnswer.add("answer2");
        optionsAnswer.add("answer3");
        optionsAnswer.add("answer4");
        Question question = new Question("Question", optionsAnswer, 0);

        assertNotEquals(fixedQuestion().hashCode(), question.hashCode());
    }

    @Test
    public void testEquals() {
        assertEquals(fixedQuestion(), fixedQuestion());

        String correctAnswer = "correct answer";
        LinkedHashSet<String> optionsAnswer = new LinkedHashSet<>();

        optionsAnswer.add(correctAnswer);
        optionsAnswer.add("answer2");
        optionsAnswer.add("answer3");
        optionsAnswer.add("answer4");
        Question question = new Question("Question", optionsAnswer, 0);

        assertNotEquals(question, fixedQuestion());
    }
}
