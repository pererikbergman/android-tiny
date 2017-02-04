/*
 * Copyright 2017 Per-Erik Bergman (bergman@uncle.se)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
