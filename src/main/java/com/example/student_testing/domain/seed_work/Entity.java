package com.example.student_testing.domain.seed_work;

import java.util.Objects;

public abstract class Entity<ID extends ValueObject> {

    private ID id;

    protected Entity() {
    }

    public Entity(ID id) {
        this.id = id;
    }

    public ID Id() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Entity<ID> entity = (Entity<ID>) o;
        return Objects.equals(this.id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
