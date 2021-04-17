package com.example.toaccountornot.ui.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.OcrRequestParams;
import com.baidu.ocr.sdk.model.OcrResponseResult;
import com.example.toaccountornot.AccountActivity;
import com.example.toaccountornot.baidu_ocr.BaiduJson;

import org.json.JSONException;
import org.litepal.LitePalApplication;

import java.io.File;

public class BaiduocrFragment extends Fragment {

//    /**
//     * 解析出租车票
//     * date和amount在taxijson实例里
//     * */
//    private double amount;
//    private String date;
//
//    void recTaxiTicket(String filePath){
//        // 出租车票识别参数设置
//        OcrRequestParams param = new OcrRequestParams();
//        // 设置image参数
//        param.setImageFile(new File(filePath));
//        // 调用出租车发票识别服务
//        OCR.getInstance(getContext()).recognizeTaxireceipt(param, new OnResultListener<OcrResponseResult>() {
//            @Override
//            public void onResult(OcrResponseResult result) {
//                //listener.onResult(result.getJsonRes());
//                if(result!= null){
//                    BaiduJson taxijson = new BaiduJson();
//                    try {
//                        taxijson.ReturnTaxiTicket(result.getJsonRes());
//                        //结果展示
////                        mContent.setText(taxijson.toString());
//                        //如果想要amout和date,调用get函数就好了
//                        amount = taxijson.getBaiduAmount();
//                        date = taxijson.getBaiduDate();
//                        passResult("交通");
//                        //结果展示
//                        //mContent.setText(taxijson.toString());
//                        //如果想要amout和date,调用get函数就好了
//                        //taxijson.getBaiduAmount();
//                        //taxijson.getBaiduDate();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//
//            @Override
//            public void onError(OCRError error) {
//                // 调用失败，返回OCRError对象
//                returnRecError(error);
//            }
//        });
//    }
//
//    /**
//     * 解析火车票
//     * */
//     void recTrainTicket(String filePath){
//        // 火车票识别参数设置
//        OcrRequestParams param = new OcrRequestParams();
//        // 设置image参数
//        param.setImageFile(new File(filePath));
//        // 调用火车票识别服务
//        OCR.getInstance(getContext()).recognizeTrainticket(param, new OnResultListener<OcrResponseResult>() {
//            @Override
//            public void onResult(OcrResponseResult result) {
//                //listener.onResult(result.getJsonRes());
//                if(result!= null){
//                    BaiduJson trainjson = new BaiduJson();
//                    try {
//                        trainjson.ReturnTrainTicket(result.getJsonRes());
//                        Log.d("helloha_火车票",trainjson.toString());
//                        //结果展示
////                        mContent.setText(taxijson.toString());
//                        //如果想要amout和date,调用get函数就好了
//                        amount = trainjson.getBaiduAmount();
//                        date = trainjson.getBaiduDate();
//                        passResult("交通");
//                        //mContent.setText(trainjson.toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    //mContent.setText(result.getJsonRes());
//                }
//            }
//
//            @Override
//            public void onError(OCRError error) {
//                // 调用失败，返回OCRError对象
//                returnRecError(error);
//            }
//        });
//    }
//
//
//    /***
//     * 解析发票
//     * ***/
//     void recvatInvoice(String filePath){
//        // 增值税发票识别参数设置
//        OcrRequestParams param = new OcrRequestParams();
//        // 设置image参数
//        param.setImageFile(new File(filePath));
//        OCR.getInstance(getContext()).recognizeVatInvoice(param, new OnResultListener<OcrResponseResult>() {
//            @Override
//            public void onResult(OcrResponseResult result) {
//                //listener.onResult(result.getJsonRes());
//                if(result != null){
//                    BaiduJson vatbaiduJson = new BaiduJson();
//                    try {
//                        vatbaiduJson.ReturnVatInvoice(result.getJsonRes());
//                        amount = vatbaiduJson.getBaiduAmount();
//                        date = vatbaiduJson.getBaiduDate();
//                        passResult("日用");
//                        //结果展示
//                        //mContent.setText(taxijson.toString());
//                        //如果想要amout和date,调用get函数就好了
//                        //taxijson.getBaiduAmount();
//                        //taxijson.getBaiduDate();
//                        Log.d("helloha_发票",vatbaiduJson.toString());
//                        //Log.d("helloha",vatbaiduJson.getBaiduDate());
//                        //mContent.setText(vatbaiduJson.toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onError(OCRError error) {
//                // 调用失败，返回OCRError对象
//                returnRecError(error);
//            }
//        });
//    }
//
//
//    public void returnRecError(OCRError error){
//        // 调用失败，返回OCRError对象
//        Toast.makeText(getContext(), "识别出错,请查看log错误代码", Toast.LENGTH_SHORT).show();
//        Log.d("MainActivity", "onError: " + error.getMessage());
//    }
//
//    /**
//     * 传递解析数据
//     */
//    private void passResult(String first) {
//        System.out.println("==========passResult==========");
//        System.out.println("amount:"+amount);
//        System.out.println("date:"+date);
//        if(amount == 0.0||date == null) {
//            System.out.println("invaliddddddddddddddddd");
//            Toast.makeText(getContext(), "未识别出有效信息", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            System.out.println("=========sharepreference========================");
//            try{
//                SharedPreferences imageparse = DetailFragment.getMyContext().getSharedPreferences("imageparse", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = imageparse.edit();
//                editor.putString("first", first);
//                editor.putString("amount", String.valueOf(amount));
//                editor.putString("date", date);
//                editor.commit();
//                amount = 0.0;
//                date = null;
//                Intent intent = new Intent(getContext(), AccountActivity.class);
//                startActivity(intent);
//            }
//            catch (NullPointerException e) {
//                Toast.makeText(getContext(), "context为空", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
