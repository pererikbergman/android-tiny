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

package com.rakangsoftware.tiny;

import android.os.Handler;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TinyRequestBuilder {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient mClient;

    private final String mUrl;
    private final Type mTypeOfK;
    private String mRoute;
    private Object mBody;

    private final Map<String, String> header = new HashMap<>();
    private final Map<String, String> query = new HashMap<>();
    private final Map<String, String> template = new HashMap<>();

    <K> TinyRequestBuilder(final OkHttpClient client, final String url, final Class<K> classOfK) {
        mClient = client;
        mUrl = url;
        mTypeOfK = (Type) classOfK;
    }

    TinyRequestBuilder(final OkHttpClient client, final String url, final Type typeOfK) {
        mClient = client;
        mUrl = url;
        mTypeOfK = typeOfK;
    }

    public TinyRequestBuilder addHeader(String key, String value) {
        header.put(key, value);
        return this;
    }

    public TinyRequestBuilder addQueryParameter(String key, String value) {
        query.put(key, value);
        return this;
    }

    public TinyRequestBuilder setTemplateValue(String key, String value) {
        template.put(key, value);
        return this;
    }

    public TinyRequestBuilder setBody(final Object body) {
        mBody = body;
        return this;
    }

    public TinyRequestBuilder setRoute(final String route) {
        mRoute = route;
        return this;
    }

    public <K extends TinyResult> void get(final K result) {
        method(result, "GET", null, mTypeOfK);
    }

    public <K> void post(final TinyResult<K> result) {
        method(result, "POST", mBody, mTypeOfK);
    }

    public <K> void put(final TinyResult<K> result) {
        method(result, "PUT", mBody, mTypeOfK);
    }

    public <K> void patch(final TinyResult<K> result) {
        method(result, "PATCH", mBody, mTypeOfK);
    }

    public <K> void delete(final TinyResult<K> result) {
        method(result, "DELETE", mBody, mTypeOfK);
    }

    private <K> void method(final TinyResult<K> result, final String method, final Object body, final Type classOfT) {
        final Handler handler = new Handler();
        String url = buildUrl(mUrl, mRoute, query, template);
        System.out.println("url = " + url);

        RequestBody requestBody = null;
        if (body != null) {
            requestBody = RequestBody.create(JSON, new Gson().toJson(body));
        }

        final Request.Builder builder = new Request.Builder()
                .url(url)
                .method(method, requestBody);

        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }

        final Request request = builder.build();

        mClient.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        e.printStackTrace();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                result.onFail(e);
                            }
                        });

                    }

                    @Override
                    public void onResponse(final Call call, final Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        result.onSuccess(
                                                (K) new Gson().fromJson(
                                                        response.body().string(),
                                                        classOfT
                                                )
                                        );
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    }
                }
        );
    }

    private String template(String str, Map<String, String> template) {
        for (Map.Entry<String, String> entry : template.entrySet()) {
            String key = "{" + entry.getKey() + "}";
            String value = entry.getValue();
            str = str.replace(key, value);
        }

        return str;
    }

    private String buildUrl(final String url, final String route, final Map<String, String> query, final Map<String, String> template) {
        String fixedUrl = template(url, template);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(fixedUrl).newBuilder();

        if (route != null) {
            String fixedRoute = template(route, template);
            String[] routes = fixedRoute.split("/");
            for (String routeSegment : routes) {
                urlBuilder.addPathSegment(routeSegment);
            }
        }

        for (Map.Entry<String, String> entry : query.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }

        return urlBuilder.build().toString();
    }
}