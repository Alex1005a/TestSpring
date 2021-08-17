package com.example.student_testing.domain;

import com.example.student_testing.domain.seed_work.AggregateRoot;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Test extends AggregateRoot<TestId> {

    private String name;

    private String description;

    private LinkedHashSet<Question> questions;

    private LocalDateTime createdTime;

    private LocalDateTime endTime;

    private Test() {
        super();
    }

    public Test(TestId testId,
                String name,
                String description,
                LinkedHashSet<Question> questions,
                LocalDateTime createdTime,
                LocalDateTime endTime) {

        super(testId);

        setName(name);
        setDescription(description);
        setQuestions(questions);

        if(createdTime.isAfter(endTime)){
            throw new IllegalArgumentException("The created time must be before end time");
        }

        this.createdTime = createdTime;
        this.endTime = endTime;
    }

    private void setName(String name) {
        if(name == null || name.length() < 3
                || name.length() > 25)
            throw new IllegalArgumentException("The name length must be from 3 to 25");

        this.name = name;
    }

    private void setDescription(String description) {
        if(description == null || description.length() < 20
                || description.length() > 500)
            throw new IllegalArgumentException("The description length must be from 20 to 500");

        this.description = description;
    }

    private void setQuestions(LinkedHashSet<Question> questions) {
        if(questions.size() < 3 || questions.size() > 20)
            throw new IllegalArgumentException("The number of questions must be from 3 to 20");

        this.questions = questions;
    }

    public boolean isFinish() {
        return LocalDateTime.now().isAfter(endTime);
    }

    public void moveFinishDate(int minutes, int hours, int days) {
        if(minutes < 0 || hours < 0 || days < 0)
            throw new IllegalArgumentException("The minutes, hours and days must be from 0");

        this.endTime = endTime.plusMinutes(minutes).plusHours(hours).plusDays(days);
    }

    public void moveFinishDate(LocalDateTime localDateTime) {
        if(endTime.isAfter(localDateTime))
            throw new IllegalArgumentException("The time to which the end must after current end time");

        this.endTime = localDateTime;
    }


    public LocalDateTime finishTime() {
        return this.endTime;
    }

    public Set<Question> questions() {
        return Collections.unmodifiableSet(this.questions);
    }
}

