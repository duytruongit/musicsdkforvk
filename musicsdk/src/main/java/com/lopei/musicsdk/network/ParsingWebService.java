package com.lopei.musicsdk.network;

import android.text.TextUtils;
import android.webkit.CookieManager;


import com.lopei.musicsdk.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alan on 15.12.16.
 */

public interface ParsingWebService {
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

    ParsingWebService service = retrofit.create(ParsingWebService.class);

    @Multipart
    @POST("https://m.vk.com/audio?act=search")
    Observable<ResponseBody> searchAudio(@Part("_ajax") String ajax,
                                         @Query("q") String searchQuery,
                                         @Query("offset") int offset);

    @Multipart
    @POST("https://m.vk.com/audios{id}")
    Observable<ResponseBody> loadUserAudioJson(@Path("id") String usedId,
                                               @Part("_ajax") int ajax,
                                               @Part("offset") int offset);


    class MyInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Request.Builder builder = request.newBuilder();

            String id = request.url().toString().replace("https://m.vk.com/audios", "");

            String storedCookie = CookieManager.getInstance().getCookie("https://m.vk.com");
            builder.addHeader("authority", "m.vk.com"); // +
            builder.addHeader("method", "POST"); // +
            builder.addHeader("path", request.url().toString().replace("https://m.vk.com", "")); // ?
            builder.addHeader("scheme", "https"); // +
            builder.addHeader("accept-language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4,uk;q=0.2"); // +
            builder.addHeader("cache-control", "no-cache"); // +
            builder.addHeader("content-length", "18"); // ?

            if (!TextUtils.isEmpty(storedCookie)) {
                builder.addHeader("Cookie", storedCookie); // +
            }
            builder.addHeader("origin", "https://m.vk.com"); // +
            builder.addHeader("pragma", "no-cache");
            if (request.url().toString().contains("act=search")) {
                builder.addHeader("referer", request.url().toString()); // +
            } else {
                builder.addHeader("referer", "https://m.vk.com/audios" + id); // +
            }
            builder.addHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36"); // +
            builder.addHeader("x-requested-with", "XMLHttpRequest"); // +

            Request authorized = builder.build();


            long t1 = System.nanoTime();
            System.out.println(
                    String.format("Sending request %s on %n%s", authorized.url(), authorized.headers()));

            Response response = chain.proceed(authorized);
            long t2 = System.nanoTime();

            System.out.println(
                    String.format("Received response for %s in %.1fms%n%s", response.request().url(),
                            (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }

}