package com.example.toaccountornot.utils;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class ParseJsonUtil {

    public static final int FAILURE = 0;
    public static final int SUCCESS = 1;

    public static void parseJSONWithFastjson(String jsonData, Handler handler) {
        JSONObject object = JSON.parseObject(jsonData);
        Integer code = object.getInteger("code");
        String message = object.getString("message");
        String data = object.getString("data");
        System.out.println("=================parseJSONWithFastjson()===================");
        System.out.println("code:"+code);
        System.out.println("message:"+message);
        System.out.println("data:"+data);
        Message msg = new Message();
        if(message.equals("success")) {
            msg.what = SUCCESS;
        } else {
            msg.what = FAILURE;
        }
        handler.sendMessage(msg);
    }
    public static void parseJSONWithFastjson(String jsonData,String fromWhere) {
        JSONObject object = JSON.parseObject(jsonData);
        Integer code = object.getInteger("code");
        String message = object.getString("message");
        String data = object.getString("data");
        System.out.println("=================parseJSONWithFastjson()===================");
        System.out.println(fromWhere);
        System.out.println("code:"+code);
        System.out.println("message:"+message);
        System.out.println("data:"+data);
    }
}
