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
