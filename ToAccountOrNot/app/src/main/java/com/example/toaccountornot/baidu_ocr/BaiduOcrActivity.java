package com.example.toaccountornot.baidu_ocr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.GeneralBasicParams;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.OcrRequestParams;
import com.baidu.ocr.sdk.model.OcrResponseResult;
import com.baidu.ocr.sdk.model.WordSimple;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.example.toaccountornot.R;

import org.json.JSONException;

import java.io.File;

public class BaiduOcrActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CAMERA = 102;
    private static final int REQUEST_CODE_DRIVING_LICENSE = 103;
    private static final int REQUEST_CODE_VEHICLE_LICENSE = 104;
    private static final int REQUEST_CODE_REGNIZE_WORD = 105;
    private static final int REQUEST_CODE_VAT_INVOCIE = 106;
    private static final int REQUEST_CODE_TRAIN_TICKET = 107;
    private static final int REQUEST_CODE_TAXI_TICKET = 108;
    private TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu);

        mContent = (TextView) findViewById(R.id.tv_show_word_content);

        // 识别文字
        findViewById(R.id.bt_regonize_word).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaiduOcrActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_REGNIZE_WORD);
            }
        });


        // 识别发票
        findViewById(R.id.bt_vatinvoice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaiduOcrActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_VAT_INVOCIE);
            }
        });

        // 识别车票
        findViewById(R.id.bt_train_ticket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaiduOcrActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_TRAIN_TICKET);
            }
        });

        // 识别车票
        findViewById(R.id.bt_taxi_ticket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaiduOcrActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_TAXI_TICKET);
            }
        });

        // 初始化
        initAccessTokenWithAkSk();
    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(
                new OnResultListener<AccessToken>() {
                    @Override
                    public void onResult(AccessToken result) {

                        // 本地自动识别需要初始化
                        initLicense();

                        Log.d("MainActivity", "onResult: " + result.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mContent.setText("初始化认证成功");
                                //Toast.makeText(MainActivity.this, "初始化认证成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(OCRError error) {
                        error.printStackTrace();
                        Log.e("MainActivity", "onError: " + error.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mContent.setText("初始化失败！");
                                Toast.makeText(BaiduOcrActivity.this, "初始化认证失败,请检查 key", Toast.LENGTH_SHORT).show();
                                onDestroy();
                            }
                        });
                    }
                }, getApplicationContext(),
                // 需要自己配置 https://console.bce.baidu.com
                "Uq48vOb5xpDtryKC3L61FrOB",
                // 需要自己配置 https://console.bce.baidu.com
                "bfnopp7wMo9e4QERORxYcLSFia2Gtvqv");
    }

    private void initLicense() {
        CameraNativeHelper.init(this, OCR.getInstance(this).getLicense(),
                new CameraNativeHelper.CameraNativeInitCallback() {
                    @Override
                    public void onError(int errorCode, Throwable e) {
                        final String msg;
                        switch (errorCode) {
                            case CameraView.NATIVE_SOLOAD_FAIL:
                                msg = "加载so失败，请确保apk中存在ui部分的so";
                                break;
                            case CameraView.NATIVE_AUTH_FAIL:
                                msg = "授权本地质量控制token获取失败";
                                break;
                            case CameraView.NATIVE_INIT_FAIL:
                                msg = "本地质量控制";
                                break;
                            default:
                                msg = String.valueOf(errorCode);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BaiduOcrActivity.this,
                                        "本地质量控制初始化错误，错误原因： " + msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_REGNIZE_WORD && resultCode == Activity.RESULT_OK){
            String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
            //recVehicleCard(filePath);
            recWord(filePath);
        }

        if (requestCode == REQUEST_CODE_VAT_INVOCIE && resultCode == Activity.RESULT_OK){
            String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
            recvatInvoice(filePath);
        }

        if (requestCode == REQUEST_CODE_TRAIN_TICKET && resultCode == Activity.RESULT_OK){
            String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
            recTrainTicket(filePath);
        }

        if (requestCode == REQUEST_CODE_TAXI_TICKET && resultCode == Activity.RESULT_OK){
            String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
            recTaxiTicket(filePath);
        }
    }

    /**
     * 解析出租车票
     * date和amount在taxijson实例里
     * */
    private void recTaxiTicket(String filePath){
        // 出租车票识别参数设置
        OcrRequestParams param = new OcrRequestParams();
        // 设置image参数
        param.setImageFile(new File(filePath));
        // 调用出租车发票识别服务
        OCR.getInstance(this).recognizeTaxireceipt(param, new OnResultListener<OcrResponseResult>() {
            @Override
            public void onResult(OcrResponseResult result) {
                //listener.onResult(result.getJsonRes());
                if(result!= null){
                    BaiduJson taxijson = new BaiduJson();
                    try {
                        taxijson.ReturnTaxiTicket(result.getJsonRes());
                        //结果展示
                        mContent.setText(taxijson.toString());
                        //如果想要amout和date,调用get函数就好了
                        //taxijson.getBaiduAmount();
                        //taxijson.getBaiduDate();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError对象
                returnRecError(error);
            }
        });
    }

    /**
     * 解析火车票
     * */
    private void recTrainTicket(String filePath){
        // 火车票识别参数设置
        OcrRequestParams param = new OcrRequestParams();
        // 设置image参数
        param.setImageFile(new File(filePath));
        // 调用火车票识别服务
        OCR.getInstance(this).recognizeTrainticket(param, new OnResultListener<OcrResponseResult>() {
            @Override
            public void onResult(OcrResponseResult result) {
                //listener.onResult(result.getJsonRes());
                if(result!= null){
                    BaiduJson trainjson = new BaiduJson();
                    try {
                        trainjson.ReturnTrainTicket(result.getJsonRes());
                        mContent.setText(trainjson.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //mContent.setText(result.getJsonRes());
                }
            }

            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError对象
                returnRecError(error);
            }
        });
    }


    /***
     * 解析发票
     * ***/
    private void recvatInvoice(String filePath){
        // 增值税发票识别参数设置
        OcrRequestParams param = new OcrRequestParams();
        // 设置image参数
        param.setImageFile(new File(filePath));
        OCR.getInstance(this).recognizeVatInvoice(param, new OnResultListener<OcrResponseResult>() {
            @Override
            public void onResult(OcrResponseResult result) {
                //listener.onResult(result.getJsonRes());
                if(result != null){
                    BaiduJson vatbaiduJson = new BaiduJson();
                    try {
                        vatbaiduJson.ReturnVatInvoice(result.getJsonRes());
                        mContent.setText(vatbaiduJson.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError对象
                returnRecError(error);
            }
        });
    }

    /***
     * 基础解析文字
     * ***/
    private void recWord(String filePath){
        // 通用文字识别参数设置
        GeneralBasicParams param = new GeneralBasicParams();
        param.setDetectDirection(true);
        param.setImageFile(new File(filePath));

        // 调用通用文字识别服务
        OCR.getInstance(this).recognizeGeneralBasic(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {

                // 调用成功，返回GeneralResult对象
                if (result != null ){
                    for (WordSimple wordSimple : result.getWordList()) {
                        mContent.setText(wordSimple.toString());
                    }
                }
                else{
                    Toast.makeText(BaiduOcrActivity.this, "啥都没有", Toast.LENGTH_SHORT).show();
                }

                // json格式返回字符串
                //listener.onResult(result.getJsonRes());
            }
            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError对象
                returnRecError(error);
            }
        });
    }



    @Override
    protected void onDestroy() {
        CameraNativeHelper.release();
        // 释放内存资源
        OCR.getInstance(this).release();
        super.onDestroy();

    }

    public void returnRecError(OCRError error){
        // 调用失败，返回OCRError对象
        Toast.makeText(BaiduOcrActivity.this, "识别出错,请查看log错误代码", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onError: " + error.getMessage());
    }
}
