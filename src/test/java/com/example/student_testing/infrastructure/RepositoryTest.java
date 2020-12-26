package com.example.student_testing.infrastructure;

import com.basho.riak.client.RiakException;
import com.example.student_testing.domain.Question;
import com.example.student_testing.domain.TestId;
import com.example.student_testing.infratstructure.RiakTestRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest {

    private final String validName = "Name";

    private final String validDescription = "It is description for Test";

    private LinkedHashSet<Question> validQuestions() {
        LinkedHashSet<String> optionsAnswer = new LinkedHashSet<>();
        optionsAnswer.add("answer1");
        optionsAnswer.add("answer2");
        optionsAnswer.add("answer3");
        optionsAnswer.add("answer4");

        LinkedHashSet<Question> questions = new LinkedHashSet<>();

        questions.add(new Question("question1", optionsAnswer, 0));
        questions.add(new Question("question2", optionsAnswer, 0));
        questions.add(new Question("question3", optionsAnswer, 0));

        return questions;
    }

    private com.example.student_testing.domain.Test fixtureTest() {
        return new com.example.student_testing.domain.Test(TestId.generate(),
                validName,
                validDescription,
                validQuestions(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1));
    }

    @Test
    public void testEquals() throws RiakException {
        RiakTestRepository repository = new RiakTestRepository();
        com.example.student_testing.domain.Test test = fixtureTest();

        repository.save(test);

        var testRiak = repository.testOfId(test.Id());

        assertTrue( testRiak.isPresent() );
        assertEquals(testRiak.get(), test);

        repository.delete(testRiak.get());

        assertTrue( repository.testOfId(test.Id()).isEmpty() );
    }

    @Test
    public void testSave() throws RiakException {
        RiakTestRepository repository = new RiakTestRepository();
        var test = fixtureTest();

        repository.save(test);

        var testRiak = repository.testOfId(test.Id()).get();
        testRiak.moveFinishDate(0, 2, 0);

        repository.save(testRiak);

        var testRiak1 = repository.testOfId(testRiak.Id()).get();

        assertEquals(testRiak.finishTime(), testRiak1.finishTime());

        repository.delete(testRiak1);
    }
}
