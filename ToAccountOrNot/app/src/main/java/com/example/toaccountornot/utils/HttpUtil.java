package com.example.toaccountornot.utils;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static void sendPOSTRequest(String requestData, String url, Callback callback) {

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestData);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(callback);

    }

    public static void sendPOSTRequestWithToken(String requestData, String url, Callback callback) {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new TokenInterceptor())
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestData);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(callback);

    }

    public static void sendGETRequestWithToken(String url, Map<String, String> params, Callback callback) {

        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        builder.addPathSegment(params.get("year"));
        builder.addPathSegment(params.get("month"));

        System.out.println("GET "+builder.build().toString());

        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new TokenInterceptor())
                .build();
        Request request = new Request.Builder()
                .url(builder.build())
                .method("GET", null)
                .build();
        client.newCall(request).enqueue(callback);

    }

    public static void sendGETRequestWithTokenCard(String url, Map<String, String> params, Callback callback) {

        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
//        builder.addPathSegment(params.get("year"));
//        builder.addPathSegment(params.get("month"));

        System.out.println("GET "+builder.build().toString());

        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new TokenInterceptor())
                .build();
        Request request = new Request.Builder()
                .url(builder.build())
                .method("GET", null)
                .build();
        client.newCall(request).enqueue(callback);

    }
}
