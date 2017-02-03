package com.rakangsoftware.tiny;

public interface TinyResult<K> {
    void onSuccess(K result);

    void onFail(Throwable throwable);
}