package com.example.toaccountornot.baidu_ocr;

import org.json.JSONException;
import org.json.JSONObject;

public class VatInvoice {

    private String time;
    private String amount;

    /*JSONObject kindJson=new JSONObject(json);//将string转为jsonobject
    String menuJson=kindJson.getString("menus");//获取到menus
    JSONArray menus=new JSONArray(menuJson);//再将menuJson转为jsonarray*/
    public VatInvoice(String result) throws JSONException {
        JSONObject resultJson=new JSONObject(result);//将string转为jsonobject
        String wordresult = resultJson.getString("words_result");
        JSONObject wordresultJson=new JSONObject(wordresult);
        this.amount = wordresultJson.getString("TotalAmount");
        this.time = wordresultJson.getString("InvoiceDate");

    }
    public String getTime(){
        return time;
    }
    public String getAmount(){
        return amount;
    }
}
