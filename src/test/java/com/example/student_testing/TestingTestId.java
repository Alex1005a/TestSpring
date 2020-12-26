package com.example.student_testing;

import com.example.student_testing.domain.TestId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestingTestId {
    @Test
    public void testEquals() {
        Assertions.assertNotEquals(TestId.generate(), TestId.generate());
        UUID uuid = UUID.randomUUID();
        assertEquals(new TestId(uuid), new TestId(uuid));
    }
}
