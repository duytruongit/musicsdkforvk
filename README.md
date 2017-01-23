# musicsdkforvk
SDK for fetching music from social network VK

## How to use?
Step 1. Add the JitPack repository to your build file

Gradle:

```
allprojects {
  repositories {
    maven { url "https://jitpack.io" }
  }
}
```

Maven:

```
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

```
Step 2. Add the dependency

Gradle:

```
dependencies {
  compile 'com.github.lopei:musicsdkforvk:0.0.2'
}
```

Maven:

```
<dependency>
  <groupId>com.github.lopei</groupId>
  <artifactId>musicsdkforvk</artifactId>
  <version>0.0.2</version>
</dependency>
  
```


Step 3. Add initialization(user authorization) on your Activity onCreate or Fragment onCreate:
```
VKMusicSDK.getInstance().logIn(this, null);
```

Step 4. Use calls to load user/group/search audio.

```
VKMusicSDK.getInstance().searchAudio("Bruno Mars", null, 0, new OnAudioLoadedListener() {
                @Override
                public void onAudioLoaded(List<AudioTrack> audioTracks) {
                    audioTracks.size();
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });

```

WARNING! Parameter Collection<AudioTrack> filterTracks is user to filter duplicated results. You should pass here your collection of already loaded files for the same request but different offset. (ex loading the same user's audio, the same group's audio, etc). This is used to prevent audio duplication, because this library works not via official API and returned audiotracks count depends on screen resolution. You may set up your own filter, just passing null here and filtering the results after receiving response from server.

## Contrubution

Contributions are always welcome

## Licence

Copyright 2016 lopei

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
