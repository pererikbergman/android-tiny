package com.rakangsoftware.tiny.sample;

public class User {

    private int    userId;
    private int    id;
    private String title;
    private String body;

    public int getUserId() {
        return userId;
    }

    public User setUserId(final int userId) {
        this.userId = userId;
        return this;
    }

    public int getId() {
        return id;
    }

    public User setId(final int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public User setTitle(final String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body;
    }

    public User setBody(final String body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
