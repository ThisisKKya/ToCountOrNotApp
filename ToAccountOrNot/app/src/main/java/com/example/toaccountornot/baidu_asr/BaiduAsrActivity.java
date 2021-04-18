package com.example.toaccountornot.baidu_asr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.aip.asrwakeup3.core.mini.AutoCheck;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.example.toaccountornot.R;

import com.baidu.aip.asrwakeup3.core.mini.ActivityMiniRecog;
import com.lxj.xpopupext.bean.JsonBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;




public class BaiduAsrActivity extends ActivityMiniRecog {

    protected TextView startTv;
    protected Button startBtn;
    protected Button endBtn;
    private static String DESC_TEXT = "请读出你的要账单：   类别（比如运动） 金额（比如3.68 读作三点六八）\n 结果会返回给你 类型和金额";

    private EventManager asr;

    private boolean logTime = true;

    protected boolean enableOffline = false; // 测试离线命令词，需要改成true

    /**
     * 基于SDK集成2.2 发送开始事件
     * 点击开始按钮
     * 测试参数填在这里
     */
    private void start() {
        startTv.setText("");
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String event = null;
        event = SpeechConstant.ASR_START; // 替换成测试的event

        if (enableOffline) {
            params.put(SpeechConstant.DECODER, 2);
        }
        // 基于SDK集成2.1 设置识别参数
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        (new AutoCheck(getApplicationContext(), new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainErrorMessage(); // autoCheck.obtainAllMessage();
                        startTv.append(message + "\n");
                        ; // 可以用下面一行替代，在logcat中查看代码
                        // Log.w("AutoCheckMessage", message);
                    }
                }
            }
        }, enableOffline)).checkAsr(params);
        String json = null; // 可以替换成自己的json
        json = new JSONObject(params).toString(); // 这里可以替换成你需要测试的json
        asr.send(event, json, null, 0, 0);
        printLog("输入参数：" + json);
    }

    /**
     * 点击停止按钮
     * 基于SDK集成4.1 发送停止事件
     */
    private void stop() {
        printLog("停止识别：ASR_STOP");
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0); //
    }

    /**
     * enableOffline设为true时，在onCreate中调用
     * 基于SDK离线命令词1.4 加载离线资源(离线时使用)
     */
    private void loadOfflineEngine() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(SpeechConstant.DECODER, 2);
        params.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "assets://baidu_speech_grammar.bsg");
        asr.send(SpeechConstant.ASR_KWS_LOAD_ENGINE, new JSONObject(params).toString(), null, 0, 0);
    }

    /**
     * enableOffline为true时，在onDestory中调用，与loadOfflineEngine对应
     * 基于SDK集成5.1 卸载离线资源步骤(离线时使用)
     */
    private void unloadOfflineEngine() {
        asr.send(SpeechConstant.ASR_KWS_UNLOAD_ENGINE, null, null, 0, 0); //
    }

    private String getParams(String params) throws JSONException {
        JSONObject getparmas = new JSONObject(params);
        String results_recognize = getparmas.getString("results_recognition");
        JSONArray getresult = new JSONArray(results_recognize);
        String result = (String) getresult.get(0);
        return result;
    }


    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        //String logTxt = "name: " + name;
        String logTxt = "";

        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
            // 识别相关的结果都在这里
            if (params == null || params.isEmpty()) {
                return;
            }
            if (params.contains("\"nlu_result\"")) {
                // 一句话的语义解析结果
                if (length > 0 && data.length > 0) {
                    //logTxt += ", 语义解析结果：" + new String(data, offset, length);
                }
            } else if (params.contains("\"partial_result\"")) {
                // 一句话的临时识别结果
                //logTxt += ", 临时识别结果：" + params;
            }  else if (params.contains("\"final_result\""))  {
                // 一句话的最终识别结果
                //logTxt +=  params;
                logTxt = params.toString();
                try {
                    String result = getParams(params.toString());
                    printOutcome(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //printOutcome(logTxt);
            }  else {
                // 一般这里不会运行
                logTxt += " ;params :" + params;
                if (data != null) {
                    logTxt += " ;data length=" + data.length;
                }
            }
        } else {
            // 识别开始，结束，音量，音频数据回调
            if (params != null && !params.isEmpty()){
                //logTxt += " ;params :" + params;
            }
            if (data != null) {
                //logTxt += " ;data length=" + data.length;
            }
        }


        //printLog(logTxt);
    }


    protected void printLog(String text) {
        if (logTime) {
            text += "  ;time=" + System.currentTimeMillis();
        }
        text += "\n";
        Log.i(getClass().getName(), text);
        startTv.append(text + "\n");
    }


    /***
     * 返回最后结果展示
     */
    protected void printOutcome(String text){
//        text += "\n";
        StringDivide stringDivide = new StringDivide();
        stringDivide.DividetheString(text);
        startTv.append(text + "\n");
        startTv.append("类型："+stringDivide.getCostType()+"，金额:"+stringDivide.getCostDouble());
        //stringDivide.getCostDouble() 获取金额
        //stringDivide.getCostType()  获取类型
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_asr);
        initView();
        initPermission();

        asr = EventManagerFactory.create(this, "asr");
        // 基于sdk集成1.3 注册自己的输出事件类
        asr.registerListener(this); //  EventListener 中 onEvent方法
        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                start();
            }
        });
        endBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stop();
            }
        });
        if (enableOffline) {
            loadOfflineEngine(); // 测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
        }
    }

    private void initView() {
        startTv = (TextView) findViewById(R.id.tv_asr_start);
        startBtn = (Button) findViewById(R.id.bt_asr_start);
        endBtn = (Button) findViewById(R.id.bt_asr_end);
        startTv.setText(DESC_TEXT + "\n");
    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.

            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }


}