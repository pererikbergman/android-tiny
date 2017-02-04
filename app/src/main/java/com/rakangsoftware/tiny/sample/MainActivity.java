
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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rakangsoftware.tiny.Tiny;
import com.rakangsoftware.tiny.TinyResult;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String URL = "https://jsonplaceholder.typicode.com";
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResult = (TextView) findViewById(R.id.result);
    }

    public void onGet(View view) {
        Tiny.fetch("https://jsonplaceholder.typicode.com/posts/{id}", Post.class)
                .setTemplateValue("id", "1")
                .addQueryParameter("api_key", "585570452763830e4c6149")
                .addHeader("access-token", "asda322rsdfgdffq3")
                .get(
                        new TinyResult<Post>() {
                            @Override
                            public void onSuccess(final Post result) {
                                mResult.setText(result.toString());
                            }

                            @Override
                            public void onFail(final Throwable throwable) {
                                Log.d(TAG, "onFail() called with: throwable = [" + throwable + "]");
                            }
                        }
                );
    }

    public void onPost(View view) {
        Tiny.fetch(URL, Id.class)
                .setBody(new Object())
                .setRoute("/posts")
                .post(
                        new TinyResult<Id>() {
                            @Override
                            public void onSuccess(final Id result) {
                                mResult.setText(result.toString());
                            }

                            @Override
                            public void onFail(final Throwable throwable) {
                                Log.d(TAG, "onFail() called with: throwable = [" + throwable + "]");
                            }
                        }
                );
    }


    public void onPut(View view) {
        Tiny.fetch(URL, Post.class)
                .setRoute("/posts/2")
                .addQueryParameter("api_key", "585570452763830e4c6149")
                .addHeader("access-token", "asda322rsdfgdffq3")
                .setBody(new Post())
                .put(
                        new TinyResult<Post>() {
                            @Override
                            public void onSuccess(final Post result) {
                                mResult.setText(result.toString());
                            }

                            @Override
                            public void onFail(final Throwable throwable) {
                                Log.d(TAG, "onFail() called with: throwable = [" + throwable + "]");
                            }
                        }
                );
    }


    public void onPatch(View view) {
        Post post = new Post();
        post.setTitle("My special title");

        Tiny.fetch(URL, Post.class)
                .setRoute("/posts/2")
                .addQueryParameter("api_key", "585570452763830e4c6149")
                .addHeader("access-token", "asda322rsdfgdffq3")
                .setBody(post)
                .patch(
                        new TinyResult<Post>() {
                            @Override
                            public void onSuccess(final Post result) {
                                mResult.setText(result.toString());
                            }

                            @Override
                            public void onFail(final Throwable throwable) {
                                Log.d(TAG, "onFail() called with: throwable = [" + throwable + "]");
                            }
                        }
                );
    }

    public void onDelete(View view) {
        Post post = new Post();
        Tiny.fetch(URL, Post.class)
                .setRoute("/posts/2")
                .addQueryParameter("api_key", "585570452763830e4c6149")
                .addHeader("access-token", "asda322rsdfgdffq3")
                .setBody(post)
                .delete(
                        new TinyResult<Post>() {
                            @Override
                            public void onSuccess(final Post result) {
                                mResult.setText(result.toString());
                            }

                            @Override
                            public void onFail(final Throwable throwable) {
                                Log.d(TAG, "onFail() called with: throwable = [" + throwable + "]");
                            }
                        }
                );
    }


}
