package com.example.student_testing.domain;

import com.example.student_testing.domain.seed_work.ValueObject;

import java.util.Objects;
import java.util.UUID;

public class TestId extends ValueObject {

    private final UUID value;

    public TestId(UUID value) {
        this.value = value;
    }

    public UUID value() {
        return this.value;
    }

    public static TestId generate() {
        return new TestId(UUID.randomUUID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) return false;
        TestId t = (TestId) o;
        return this.value.equals(t.value);
    }
}
