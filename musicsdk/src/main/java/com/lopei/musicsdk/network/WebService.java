package com.lopei.musicsdk.network;

import com.lopei.musicsdk.Constants;
import com.lopei.musicsdk.model.AuthInfo;
import com.lopei.musicsdk.model.FriendsResponse;
import com.lopei.musicsdk.model.GroupsResponse;
import com.lopei.musicsdk.model.Profile;
import com.lopei.musicsdk.model.ResponseArrayWrapper;
import com.lopei.musicsdk.model.ResponseWrapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alan on 15.12.16.
 */

public interface WebService {
    OkHttpClient client = new OkHttpClient
            .Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(new MyInterceptor()).build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    WebService service = retrofit.create(WebService.class);

    @GET("/method/users.get?fields=photo_100")
    Observable<ResponseArrayWrapper<Profile>> loadUserProfile(@Query("user_ids") String userId);

    @GET("/method/groups.get?extended=1")
    Observable<ResponseWrapper<GroupsResponse>> loadUserGroups();

    @GET("/method/friends.get?fields=name&order=hints")
    Observable<ResponseWrapper<FriendsResponse>> loadFriends();

    class MyInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            HttpUrl url = request
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_token", AuthInfo.getAccessToken())
                    .addQueryParameter("v", "5.53")
                    .build();
            request = request.newBuilder().url(url).build();


            long t1 = System.nanoTime();
            System.out.println(
                    String.format("Sending request %s on %n%s", request.url(), request.headers()));

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            System.out.println(
                    String.format("Received response for %s in %.1fms%n%s", response.request().url(),
                            (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }

}