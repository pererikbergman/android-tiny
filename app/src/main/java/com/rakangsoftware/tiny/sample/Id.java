package com.rakangsoftware.tiny.sample;

public class Id {
    private int id;

    public int getId() {
        return id;
    }

    public Id setId(final int id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Id{" +
                "id=" + id +
                '}';
    }
}
