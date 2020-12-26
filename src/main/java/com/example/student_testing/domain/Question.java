package com.example.student_testing.domain;

import com.example.student_testing.domain.seed_work.ValueObject;

import java.util.LinkedHashSet;
import java.util.Objects;

public class Question extends ValueObject {

    private final String questionText;

    private LinkedHashSet<String> answerOptions;

    private int correctAnswerIndex;

    public Question(String questionText, LinkedHashSet<String> answerOptions, int correctAnswerIndex) {
        this.questionText = questionText;
        setAnswerOptions(answerOptions);
        setCorrectAnswerIndex(correctAnswerIndex);
    }

    public boolean isCorrectAnswer(String answer) {
        if (answerOptions.contains(answer))
            return answerOptions.toArray()[correctAnswerIndex].toString().equals(answer);
        return false;
    }

    private void setAnswerOptions(LinkedHashSet<String> answerOptions) {
        if ((long) answerOptions.size() != 4)
            throw new IllegalArgumentException("The number of answer options must be 4");

        this.answerOptions = answerOptions;
    }

    private void setCorrectAnswerIndex(int correctAnswerIndex) {
        if (correctAnswerIndex < 0 || correctAnswerIndex >= answerOptions.size())
            throw new IllegalArgumentException("The correct answer index must be within the array");

        this.correctAnswerIndex = correctAnswerIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionText, answerOptions, correctAnswerIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) return false;
        Question q = (Question) o;
        return this.questionText.equals(q.questionText) &&
                this.answerOptions.equals(q.answerOptions) &&
                this.correctAnswerIndex == q.correctAnswerIndex;
    }
}
