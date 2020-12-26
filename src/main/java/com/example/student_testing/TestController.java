package com.example.student_testing;

import an.awesome.pipelinr.Pipeline;
import com.example.student_testing.application.CreateTest;
import com.example.student_testing.domain.TestId;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tests")
public class TestController {
    private final Pipeline pipeline;

    public TestController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public String create(@RequestBody CreateTest createTest) {
        TestId id = pipeline.send(createTest);
        return id.value().toString();
    }
}
