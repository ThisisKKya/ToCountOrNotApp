package com.example.toaccountornot.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.toaccountornot.baidu_ocr.BaiduJson;
import com.example.toaccountornot.baidu_ocr.BaiduOcrActivity;
import com.example.toaccountornot.baidu_ocr.Baidu_boom;
import com.example.toaccountornot.baidu_ocr.FileUtil;
import com.example.toaccountornot.utils.Day;
import com.example.toaccountornot.utils.DayAdapter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopupext.listener.TimePickerListener;
import com.lxj.xpopupext.popup.TimePickerPopup;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import org.json.JSONException;
import org.litepal.LitePal;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



/**
 * 明细
 */
public class DetailFragment extends Fragment {

    private View view;
    private TextView label_year;
    private TextView label_month;
    private TextView label_out;
    private TextView label_in;
    private RecyclerView rec_day;
    private List<Day> dayList = new ArrayList<>();
    private String year;
    private String month;
    private BasePopupView datePicker;
    private BoomMenuButton boomMenuButton;
    private Baidu_boom baiduboom = new Baidu_boom();
    private static final int REQUEST_CODE_REGNIZE_WORD = 105;
    private static final int REQUEST_CODE_VAT_INVOCIE = 106;
    private static final int REQUEST_CODE_TRAIN_TICKET = 107;
    private static final int REQUEST_CODE_TAXI_TICKET = 108;



    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        initView(inflater, container);
        initAccessTokenWithAkSk();
        initPicker();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("hello","11Resume");
        initDayList();
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_detail, container, false);

        label_year = view.findViewById(R.id.label_year);
        label_month = view.findViewById(R.id.label_month);
        label_out = view.findViewById(R.id.label_out);
        label_in = view.findViewById(R.id.label_in);
        LinearLayout choose_date = view.findViewById(R.id.choose_date);
        rec_day = view.findViewById(R.id.mainlist);
        LinearLayout budget_layout = view.findViewById(R.id.budget_layout);
        ImageView camera = view.findViewById(R.id.photo);
        label_year.setText(year);
        label_month.setText(month);
        rec_day.setLayoutManager(new LinearLayoutManager(getContext()));

        boomMenuButton = view.findViewById(R.id.bmb);
        boomMenuButton.setNormalColor(R.color.white);


        for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            click(index);
                            //Toast.makeText(getContext(), "Clicked " + index, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .unable(false)
                    .normalImageRes(getImageResource())
                    .normalText(getext());
            boomMenuButton.addBuilder(builder);
            change(i);
        }



        choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });
//        budget_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), BudgetActivity.class);
//                intent.putExtra("outcome", label_out.getText());
//                startActivity(intent);
//            }
//        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进相册or拍照
                //Intent intent = new Intent(getContext(), PhotoActivity.class);
                Intent intent = new Intent(getContext(), BaiduOcrActivity.class);
                startActivity(intent);
                }
        });

    }

    private void change(int index) {
        HamButton.Builder builder = (HamButton.Builder) boomMenuButton.getBuilder(index);
        if(index == 0) {
            builder.normalColorRes(R.color.piecolor1);
        }
        else if(index == 1) {
            builder.normalColorRes(R.color.piecolor2);
            builder.normalTextColorRes(R.color.boomtest);
        }
        else if(index == 2) {
            builder.normalColorRes(R.color.piecolor5);
        }
        else {
            builder.normalColorRes(R.color.piecolor6);
        }
        builder.normalTextColorRes(R.color.boomtest);
    }

    // 菜单的点击事件
    private void click(int index) {
        //Intent intent = new Intent(getContext(), CameraActivity.class);
        if(index == 0) {
            Intent intent = new Intent(getContext(), CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    FileUtil.getSaveFile(getActivity()).getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                    CameraActivity.CONTENT_TYPE_GENERAL);
            startActivityForResult(intent, 106);
        }
        else if(index == 1) {
            Intent intent = new Intent(getContext(), CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    FileUtil.getSaveFile(getActivity()).getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                    CameraActivity.CONTENT_TYPE_GENERAL);
            startActivityForResult(intent, REQUEST_CODE_TRAIN_TICKET);
        }
        else if(index == 2) {
            Intent intent = new Intent(getContext(), CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    FileUtil.getSaveFile(getActivity()).getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                    CameraActivity.CONTENT_TYPE_GENERAL);
            startActivityForResult(intent, REQUEST_CODE_TAXI_TICKET);
        }

    }

    private static int index = 0;
    static String getext() {
        if (index >= text.length) index = 0;
        return text[index++];

    }
    private static String [] text = new String[]{
            "发票识别",
            "火车票识别",
            "出租车票识别",
            "语音识别"

    };

    private static int imageResourceIndex = 0;

    static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }

    private static int[] imageResources = new int[]{
            R.drawable.boom_1,
            R.drawable.boom_2,
            R.drawable.boom_3,
            R.drawable.boom_4
    };



    private void initDayList() {
        dayList.clear();
        // 因为LitePal不支持group by, 故使用SQL语句查询
        Cursor cursor = LitePal.findBySQL("select date from Accounts where date_year=?" +
                        "and date_month=? group by date order by date desc",
                label_year.getText().toString(),
                label_month.getText().toString());
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(0);
                dayList.add(new Day(date));
                DayAdapter dayAdapter = new DayAdapter(dayList, getContext());
                rec_day.setAdapter(dayAdapter);
            } while (cursor.moveToNext());

            double outcome = 0;
            double income = 0;
            for (Day day : dayList) {
                outcome += day.getOutcome_day();
                income += day.getIncome_day();
                DecimalFormat df = new DecimalFormat("#.##");

                label_out.setText(df.format(outcome));
                label_in.setText(df.format(income));
            }
        } else {
            DayAdapter dayAdapter = new DayAdapter(dayList, getContext());
            rec_day.setAdapter(dayAdapter);
            label_out.setText("0");
            label_in.setText("0");
        }
    }

    private void initPicker() {
        TimePickerPopup timePickerPopup = new TimePickerPopup(getContext())
                .setTimePickerListener(new TimePickerListener() {
                    @Override
                    public void onTimeChanged(Date date) {

                    }

                    @Override
                    public void onTimeConfirm(Date date, View view) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        year = String.valueOf(calendar.get(Calendar.YEAR));
                        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                        label_year.setText(year);
                        label_month.setText(month);
                        initDayList();
                    }
                });
        timePickerPopup.setMode(TimePickerPopup.Mode.YM);
        datePicker = new XPopup.Builder(getContext()).asCustom(timePickerPopup);
    }

    public void initAccessTokenWithAkSk() {
        OCR.getInstance(getContext()).initAccessTokenWithAkSk(
                new OnResultListener<AccessToken>() {
                    @Override
                    public void onResult(AccessToken result) {

                        // 本地自动识别需要初始化
                        initLicense();

                        Log.d("MainActivity", "onResult: " + result.toString());
                        baiduboom.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "初始化认证成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(OCRError error) {
                        error.printStackTrace();
                        Log.e("MainActivity", "onError: " + error.getMessage());
                        baiduboom.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //mContent.setText("初始化失败！");
                                Toast.makeText(getContext(), "初始化认证失败,请检查 key", Toast.LENGTH_SHORT).show();
                                onDestroy();
                            }
                        });
                    }
                }, getActivity(),
                // 需要自己配置 https://console.bce.baidu.com
                "Uq48vOb5xpDtryKC3L61FrOB",
                // 需要自己配置 https://console.bce.baidu.com
                "bfnopp7wMo9e4QERORxYcLSFia2Gtvqv");
    }


    private void initLicense() {
        CameraNativeHelper.init(getContext(), OCR.getInstance(getContext()).getLicense(),
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
                        baiduboom.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(),
                                        "本地质量控制初始化错误，错误原因： " + msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_REGNIZE_WORD && resultCode == Activity.RESULT_OK){
            String filePath = FileUtil.getSaveFile(getContext()).getAbsolutePath();
            //recVehicleCard(filePath);
            recWord(filePath);
        }

        if (requestCode == REQUEST_CODE_VAT_INVOCIE && resultCode == Activity.RESULT_OK){
            String filePath = FileUtil.getSaveFile(getContext()).getAbsolutePath();
            recvatInvoice(filePath);
        }

        if (requestCode == REQUEST_CODE_TRAIN_TICKET && resultCode == Activity.RESULT_OK){
            String filePath = FileUtil.getSaveFile(getContext()).getAbsolutePath();
            recTrainTicket(filePath);
        }

        if (requestCode == REQUEST_CODE_TAXI_TICKET && resultCode == Activity.RESULT_OK){
            String filePath = FileUtil.getSaveFile(getContext()).getAbsolutePath();
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
        OCR.getInstance(getContext()).recognizeTaxireceipt(param, new OnResultListener<OcrResponseResult>() {
            @Override
            public void onResult(OcrResponseResult result) {
                //listener.onResult(result.getJsonRes());
                if(result!= null){
                    BaiduJson taxijson = new BaiduJson();
                    try {
                        taxijson.ReturnTaxiTicket(result.getJsonRes());
                        Log.d("helloha_出租车票",taxijson.toString());
                        //结果展示
                        //mContent.setText(taxijson.toString());
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
        OCR.getInstance(getContext()).recognizeTrainticket(param, new OnResultListener<OcrResponseResult>() {
            @Override
            public void onResult(OcrResponseResult result) {
                //listener.onResult(result.getJsonRes());
                if(result!= null){
                    BaiduJson trainjson = new BaiduJson();
                    try {
                        trainjson.ReturnTrainTicket(result.getJsonRes());
                        Log.d("helloha_火车票",trainjson.toString());
                        //mContent.setText(trainjson.toString());
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
        OCR.getInstance(getContext()).recognizeVatInvoice(param, new OnResultListener<OcrResponseResult>() {
            @Override
            public void onResult(OcrResponseResult result) {
                //listener.onResult(result.getJsonRes());
                if(result != null){
                    BaiduJson vatbaiduJson = new BaiduJson();
                    try {
                        vatbaiduJson.ReturnVatInvoice(result.getJsonRes());
                        //结果展示
                        //mContent.setText(taxijson.toString());
                        //如果想要amout和date,调用get函数就好了
                        //taxijson.getBaiduAmount();
                        //taxijson.getBaiduDate();
                        Log.d("helloha_发票",vatbaiduJson.toString());
                        //Log.d("helloha",vatbaiduJson.getBaiduDate());
                        //mContent.setText(vatbaiduJson.toString());
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
        OCR.getInstance(getContext()).recognizeGeneralBasic(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {

                // 调用成功，返回GeneralResult对象
                if (result != null ){
                    for (WordSimple wordSimple : result.getWordList()) {
                        //mContent.setText(wordSimple.toString());
                    }
                }
                else{
                    Toast.makeText(getContext(), "啥都没有", Toast.LENGTH_SHORT).show();
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
    public void onDestroy() {
        CameraNativeHelper.release();
        // 释放内存资源
        OCR.getInstance(getContext()).release();
        super.onDestroy();

    }

    public void returnRecError(OCRError error){
        // 调用失败，返回OCRError对象
        Toast.makeText(getContext(), "识别出错,请查看log错误代码", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onError: " + error.getMessage());
    }



}