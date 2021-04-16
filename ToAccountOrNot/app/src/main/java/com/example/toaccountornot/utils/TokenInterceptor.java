package com.example.toaccountornot.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePalApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        SharedPreferences sharedPreferences = LitePalApplication.getContext().getSharedPreferences("myConfig", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        Request request = chain.request().newBuilder().addHeader("token", token).build();
        Response response = chain.proceed(request);

        return response;
    }
}
