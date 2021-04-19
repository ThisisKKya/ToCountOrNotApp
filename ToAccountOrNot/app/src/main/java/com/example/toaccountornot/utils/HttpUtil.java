package com.example.toaccountornot.utils;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static final String url_year_pie = "http://42.193.103.76:8888/stat/first/year";
    public static final String url_month_pie = "http://42.193.103.76:8888/stat/first/month";
    public static final String url_week_pie = "http://42.193.103.76:8888/stat/first/week";
    public static final String url_date_pie = "http://42.193.103.76:8888/stat/first/date";
    public static final String url_year_bar = "http://42.193.103.76:8888/stat/member/year";
    public static final String url_month_bar = "http://42.193.103.76:8888/stat/member/month";
    public static final String url_week_bar = "http://42.193.103.76:8888/stat/member/week";
    public static final String url_date_bar = "http://42.193.103.76:8888/stat/member/date";
    public static final String url_year_member = "http://42.193.103.76:8888/stat/first/member/year";
    public static final String url_month_member = "http://42.193.103.76:8888/stat/first/member/month";
    public static final String url_week_member = "http://42.193.103.76:8888/stat/first/member/week";
    public static final String url_date_member = "http://42.193.103.76:8888/stat/first/member/date";
    public static final String url_year_firstname = "http://42.193.103.76:8888/stat/second/year";
    public static final String url_month_firstname = "http://42.193.103.76:8888/stat/second/month";
    public static final String url_week_firstname = "http://42.193.103.76:8888/stat/second/week";
    public static final String url_date_firstname = "http://42.193.103.76:8888/stat/second/date";


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

    public static void sendGETRequestWithToken_Pie_Bar(String url, Map<String, String> params,String member,String firstname,Callback callback) {

        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        switch (url) {
            case url_year_pie :
            case url_year_bar:
            case url_year_member:
            case url_year_firstname:
                builder.addPathSegment(params.get("year"));
                break;
            case url_month_pie:
            case url_month_bar:
            case url_month_member:
            case url_month_firstname:
                builder.addPathSegment(params.get("year"));
                builder.addPathSegment(params.get("month"));
                break;
            case url_week_pie:
            case url_week_bar:
            case url_week_member:
            case url_week_firstname:
                builder.addPathSegment(params.get("year"));
                builder.addPathSegment(params.get("week"));
                break;
            case url_date_pie:
            case url_date_bar:
            case url_date_member:
            case url_date_firstname:
                builder.addPathSegment(params.get("date"));
                break;
        }
        builder.addPathSegment(params.get("type"));
        if(member != "null")    builder.addPathSegment(params.get("member"));
        if(firstname != "null")     builder.addPathSegment(params.get("first"));

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
