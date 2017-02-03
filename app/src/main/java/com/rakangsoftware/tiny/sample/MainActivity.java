package com.rakangsoftware.tiny.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rakangsoftware.tiny.Tiny;

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
        Tiny.fetch(URL, User.class)
                .setRoute("/posts/1")
                .addQueryParameter("api_key", "585570452763830e4c6149")
                .addHeader("access-token", "asda322rsdfgdffq3")
                .get(
                        new Tiny.Result<User>() {
                            @Override
                            public void onSuccess(final User result) {
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
                        new Tiny.Result<Id>() {
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
        Tiny.fetch(URL, User.class)
                .setRoute("/posts/2")
                .addQueryParameter("api_key", "585570452763830e4c6149")
                .addHeader("access-token", "asda322rsdfgdffq3")
                .setBody(new User())
                .put(
                        new Tiny.Result<User>() {
                            @Override
                            public void onSuccess(final User result) {
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
        User body = new User();
        body.setTitle("My special title");

        Tiny.fetch(URL, User.class)
                .setRoute("/posts/2")
                .addQueryParameter("api_key", "585570452763830e4c6149")
                .addHeader("access-token", "asda322rsdfgdffq3")
                .setBody(body)
                .patch(
                        new Tiny.Result<User>() {
                            @Override
                            public void onSuccess(final User result) {
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
        User body = new User();
        Tiny.fetch(URL, User.class)
                .setRoute("/posts/2")
                .addQueryParameter("api_key", "585570452763830e4c6149")
                .addHeader("access-token", "asda322rsdfgdffq3")
                .setBody(body)
                .delete(
                        new Tiny.Result<User>() {
                            @Override
                            public void onSuccess(final User result) {
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
