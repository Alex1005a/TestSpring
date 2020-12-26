package com.example.student_testing.infratstructure;


import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.RiakRetryFailedException;
import com.basho.riak.client.bucket.Bucket;
import com.example.student_testing.domain.Test;
import com.example.student_testing.domain.TestRepository;
import com.example.student_testing.domain.TestId;

import java.util.Optional;

public class RiakTestRepository implements TestRepository {

    static final String BUCKET_NAME = "tests";
    protected IRiakClient client;

    public RiakTestRepository() throws RiakException {
        this.client = RiakFactory.httpClient();
    }

    @Override
    public void save(Test test) {
        try {
            Bucket bucket = client.fetchBucket(BUCKET_NAME).execute();
            bucket.store(test.Id().value().toString(), test)
                    .withConverter(new KryoTestConverter(BUCKET_NAME)).execute();
        } catch (RiakRetryFailedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Test> testOfId(TestId testId) {
        try {
            Bucket bucket = client.fetchBucket(BUCKET_NAME).execute();
            Test result = bucket.fetch(testId.value().toString(), Test.class)
                    .withConverter(new KryoTestConverter(BUCKET_NAME)).execute();
            return Optional.ofNullable(result);
        } catch (RiakRetryFailedException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Test test) {
        try {
            Bucket bucket = client.fetchBucket(BUCKET_NAME).execute();
            bucket.delete(test.Id().value().toString()).execute();
        } catch (RiakException e) {
            e.printStackTrace();
        }
    }
}
