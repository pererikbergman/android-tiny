 [ ![Download](https://api.bintray.com/packages/pererikbergman/maven/tiny/images/download.svg) ](https://bintray.com/pererikbergman/maven/tiny/_latestVersion) ![Download](https://travis-ci.org/pererikbergman/android-tiny.svg?branch=master)

# Tiny
A tiny network lib build on top of OkHttp. This is mostly for my own use but feel free to use it and come with suggestions. It is far from a complete framework a lot of missing features that I will add as I need them. 

## Project News 
 * Version 0.0.3.

## Features
 * Supported methids: GET, POST, PUT, PATCH, DELETE
 * Json support.

Android 4.0.3 and above support

## Usage
Import into your gradle project:
 ``` java
dependencies {
    ...
    compile 'com.rakangsoftware.tiny:tiny:0.0.2'
    ...
}
```

### How to use
This code snippet is the simplest way of making one get call fetching one item.
``` java
Tiny.fetch("https://jsonplaceholder.typicode.com/posts/1", Post.class).get(new TinyResult<Post>() {
    @Override
    public void onSuccess(final Post post) {
                
    }

    @Override
    public void onFail(final Throwable throwable) {

    }
});
```

This code snippet used a route.
``` java
 Tiny.fetch("https://jsonplaceholder.typicode.com", Post.class)
        .setRoute("/posts/1")
        .get(new TinyResult<User>() {
            @Override
            public void onSuccess(final Post post) {
         
            }

            @Override
            public void onFail(final Throwable throwable) {
         
            }
        });
```
 

## License

    Copyright 2017 Per-Erik Bergman

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
