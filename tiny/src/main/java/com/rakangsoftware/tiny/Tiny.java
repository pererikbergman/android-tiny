package com.rakangsoftware.tiny;

import okhttp3.OkHttpClient;

@SuppressWarnings("WeakerAccess")
public class Tiny {

    private static Tiny sTiny;

    private final OkHttpClient mClient;

    private Tiny() {
        mClient = new OkHttpClient();
    }

    private OkHttpClient getClient() {
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

    public static <K> TinyRequestBuilder<K> fetch(String url, Class<K> cls) {
        return new TinyRequestBuilder<>(get().getClient(), url, cls);
    }

}
