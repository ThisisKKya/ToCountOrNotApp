package com.example.toaccountornot.baidu_ocr;

import org.json.JSONException;
import org.json.JSONObject;

public class BaiduJson {

    private String date ;
    private double amount ;


    public void ReturnVatInvoice(String result) throws JSONException {

        JSONObject resultJson=new JSONObject(result);//将string转为jsonobject
        String wordresult = resultJson.getString("words_result");
        JSONObject wordresultJson=new JSONObject(wordresult);
        try{
            //"TotalAmount": "94339.62",
            String str_amo = wordresultJson.getString("TotalAmount");
            this.amount = Double.parseDouble(str_amo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try{
            //"InvoiceDate": "2016年06月02日"
            String str_date = wordresultJson.getString("InvoiceDate");
            str_date = str_date.replace("年","-");
            str_date = str_date.replace("月","-");
            str_date = str_date.replace("日","-");
            this.date = str_date;
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void ReturnTaxiTicket(String result) throws JSONException {

        JSONObject resultJson=new JSONObject(result);//将string转为jsonobject
        String wordresult = resultJson.getString("words_result");
        JSONObject wordresultJson=new JSONObject(wordresult);
        try {
            //"TotalFare":"¥155.30元"
            String str_amo = wordresultJson.getString("TotalFare");
            if(str_amo.length() != 0){
                str_amo = str_amo.substring(1);
                str_amo = str_amo.replace("元","");
                this.amount = Double.parseDouble(str_amo);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try{
            //"Date":"2017-11-26"
            this.date = wordresultJson.getString("Date");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void ReturnTrainTicket(String result) throws JSONException {

        JSONObject resultJson=new JSONObject(result);//将string转为jsonobject
        String wordresult = resultJson.getString("words_result");
        JSONObject wordresultJson=new JSONObject(wordresult);

        try {

            //"ticket_rates": "￥54.5元",
            String str_amo = wordresultJson.getString("ticket_rates");
            if (str_amo.length() != 0){
                str_amo = str_amo.substring(1);
                str_amo = str_amo.replace("元","");
                this.amount = Double.parseDouble(str_amo);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        try{
            //"date": "2019年04月03日"
            String str_date = wordresultJson.getString("date");
            str_date = str_date.replace("年","-");
            str_date = str_date.replace("月","-");
            str_date = str_date.replace("日","-");
            this.date = str_date;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public String toString(){

        return "date:"+this.date+",amount:"+this.amount;
    }
}
