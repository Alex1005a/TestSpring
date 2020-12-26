package com.example.student_testing.domain.seed_work;

import an.awesome.pipelinr.Notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot<ID extends ValueObject> extends Entity<ID> {

    private final transient List<Notification> domainEvents = new ArrayList<>();

    protected AggregateRoot(){
        super();
    }

    public AggregateRoot(ID id) {
        super(id);
    }

    protected void registerEvent(Notification event) {
        this.domainEvents.add(event);
    }

    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    public List<Notification> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
}
