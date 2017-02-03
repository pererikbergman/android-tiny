package com.rakangsoftware.tiny;

import com.google.gson.Gson;

import java.io.IOException;
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

public class Tiny {

    private static Tiny sTiny;

    private final OkHttpClient mClient;

    private Tiny() {
        mClient = new OkHttpClient();
    }

    private void setConfig() {

    }

    public OkHttpClient getClient() {
        return mClient;
    }

    private static Tiny get() {
        if (sTiny == null) {
            synchronized (Tiny.class) {
                if (sTiny == null) {
                    sTiny = new Tiny();
                }
            }
        }

        return sTiny;
    }

    public static void init() {
        get().setConfig();
    }

    public static <K> RequestBuilder<K> fetch(String url, Class<K> cls) {
        return new RequestBuilder(get().getClient(), url, cls);
    }

    public static class RequestBuilder<K> {
        public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        private final OkHttpClient mClient;

        private final String   mUrl;
        private final Class<K> mCls;
        private       String   mRoute;
        private       Object   mBody;

        private final Map<String, String> header = new HashMap<>();
        private final Map<String, String> query  = new HashMap<>();

        public RequestBuilder(final OkHttpClient client, final String url, final Class<K> cls) {
            mClient = client;
            mUrl = url;
            mCls = cls;
        }

        public RequestBuilder<K> addHeader(String key, String value) {
            header.put(key, value);
            return this;
        }

        public RequestBuilder<K> addQueryParameter(String key, String value) {
            query.put(key, value);
            return this;
        }

        public RequestBuilder<K> setBody(final Object body) {
            mBody = body;
            return this;
        }

        public RequestBuilder<K> setRoute(final String route) {
            mRoute = route;
            return this;
        }

        public RequestBuilder<K> get(final Result<K> result) {
            method(result, "GET", null, mCls);
            return this;
        }

        public RequestBuilder<K> post(final Result<K> result) {
            method(result, "POST", mBody, mCls);
            return this;
        }

        public RequestBuilder<K> put(final Result<K> result) {
            method(result, "PUT", mBody, mCls);
            return this;
        }

        public RequestBuilder<K> patch(final Result<K> result) {
            method(result, "PATCH", mBody, mCls);
            return this;
        }

        public RequestBuilder<K> delete(final Result<K> result) {
            method(result, "DELETE", mBody, mCls);
            return this;
        }

        private <K> void method(final Result<K> result, final String method, final Object body, final Class<K> type) {
            String url = buildUrl(mUrl, mRoute, query);

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
                            result.onFail(e);
                        }

                        @Override
                        public void onResponse(final Call call, final Response response) throws IOException {
                            if (!response.isSuccessful()) {
                                throw new IOException("Unexpected code " + response);
                            } else {
                                result.onSuccess(
                                        new Gson().fromJson(
                                                response.body().string(),
                                                type
                                        )
                                );
                            }
                        }
                    }
            );
        }

        private String buildUrl(final String url, final String route, final Map<String, String> query) {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

            if (route != null) {
                String[] routes = route.split("/");
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

    public interface Result<K> {
        void onSuccess(K result);

        void onFail(Throwable throwable);
    }

}
