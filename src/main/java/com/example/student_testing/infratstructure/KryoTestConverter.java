package com.example.student_testing.infratstructure;

import com.basho.riak.client.http.util.Constants;
import com.basho.riak.client.builders.RiakObjectBuilder;
import com.basho.riak.client.IRiakObject;
import com.basho.riak.client.cap.VClock;
import com.basho.riak.client.convert.ConversionException;
import com.basho.riak.client.convert.Converter;

import com.basho.riak.client.convert.NoKeySpecifedException;

import com.example.student_testing.domain.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class KryoTestConverter implements Converter<Test> {

    private final String bucket;

    public KryoTestConverter(String bucket)
    {
        this.bucket = bucket;
    }

    public IRiakObject fromDomain(Test domainObject, VClock vclock) throws ConversionException
    {
        String key = domainObject.Id().value().toString();

        if (key == null)
        {
            throw new NoKeySpecifedException(domainObject);
        }

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        return RiakObjectBuilder.newBuilder(bucket, key)
                .withValue(gson.toJson(domainObject))
                .withVClock(vclock)
                .withContentType(Constants.CTYPE_JSON_UTF8)
                .build();

    }

    public Test toDomain(IRiakObject riakObject) throws ConversionException
    {
        if (riakObject == null)
            return null;

        Gson gson = new Gson();

        return gson.fromJson(riakObject.getValueAsString(), Test.class);
    }
}
