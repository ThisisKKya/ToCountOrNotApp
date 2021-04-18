package com.example.toaccountornot.baidu_asr;

import android.util.Log;

public class StringDivide {
    private String costType ;
    private String costAmount ;
    private double costDouble;
    //private double costAmount;

    public StringDivide(){
        this.costType = "type";
        this.costAmount = "amount";
        this.costDouble = 0;
    }

    public void DividetheString(String text){
        String type = "类型";
        String money = "金额";

        text = text.replace("，","");
        text = text.replace("。","");

        int typeindex = text.indexOf(type);
        int moneyindex = text.indexOf(money);
        //类型XXXXXX金额XXXXXX
        Log.d("GETindex","typeindex"+typeindex+",moneyindex"+moneyindex);

        if ((typeindex != -1)&( moneyindex != -1 )){
            try {

                this.costType = text.substring(typeindex+2,moneyindex);
                this.costAmount = text.substring(moneyindex+2);
                this.costDouble = Double.parseDouble(getStringNum(this.costAmount));


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private String getStringNum(String text){
        String str;
        str=text.trim();
        String str2="";
        if(str != null && !"".equals(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    str2 += str.charAt(i);
                }
            }
        }
        return str2;
    }

    public double getCostDouble() {
        return costDouble;
    }

    public String getCostAmount() {
        return costAmount;
    }

    public String getCostType() {
        return costType;
    }
}
