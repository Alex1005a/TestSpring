package com.example.student_testing.domain;

import java.util.Optional;

public interface TestRepository {

    void save(Test test);

    Optional<Test> testOfId(TestId testId);

    void delete(Test test);
}
