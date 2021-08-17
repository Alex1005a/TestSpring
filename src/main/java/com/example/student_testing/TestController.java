package com.example.student_testing;

import an.awesome.pipelinr.Pipeline;
import com.example.student_testing.application.CreateTest;
import com.example.student_testing.application.GetTestById;
import com.example.student_testing.domain.Test;
import com.example.student_testing.domain.TestId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

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

    @RequestMapping(path = "/{id}")
    public ResponseEntity<Test> get(@PathVariable("id") UUID id) {
        Optional<Test> testOpt = pipeline.send(new GetTestById(id));
        if(testOpt.isEmpty()) { return ResponseEntity.notFound().build(); }
        else return ResponseEntity.ok(testOpt.get());
    }
}
