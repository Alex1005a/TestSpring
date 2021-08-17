package com.example.student_testing.application;

import an.awesome.pipelinr.Command;
import com.example.student_testing.domain.Test;
import com.example.student_testing.domain.TestId;
import com.example.student_testing.domain.TestRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

public class GetTestById implements Command<Optional<Test>> {

    private final TestId id;

    public GetTestById(UUID id) {
        this.id = new TestId(id);
    }

    public TestId getId() {
        return id;
    }
}

@Component
class GetTestByIdHandler implements Command.Handler<GetTestById, Optional<Test>> {

    private final TestRepository testRepository;

    public GetTestByIdHandler(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public Optional<Test> handle(GetTestById getTestById) {
        return testRepository.testOfId(getTestById.getId());
    }
}
