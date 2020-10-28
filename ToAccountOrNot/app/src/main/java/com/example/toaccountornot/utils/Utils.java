package com.example.toaccountornot.utils;

import android.widget.ImageView;

import com.example.toaccountornot.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

/**
 * 工具类
 */
public class Utils {
    // utility function
    private static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    // generate a hash
    public static String sha256(String s) {
        MessageDigest digest;
        String hash;

        try {
            digest = MessageDigest.getInstance("SHA-256");
            digest.update(s.getBytes());

            hash = bytesToHexString(digest.digest());

            return hash;
        } catch (NoSuchAlgorithmException e1) {
            return s;
        }
    }

    public static void imageSwitch(String first, ImageView imageProperty) {
        switch (first)
        {
            case"餐饮":
                imageProperty.setImageResource(R.drawable.food);
                break;
            case "早饭" :
                imageProperty.setImageResource(R.drawable.second_breakfast);
                break;
            case "中饭" :
                imageProperty.setImageResource(R.drawable.second_lunch);
                break;
            case "晚饭" :
                imageProperty.setImageResource(R.drawable.second_dinner);
                break;
            case "购物":
                imageProperty.setImageResource(R.drawable.shopping);
                break;
            case "服饰":
                imageProperty.setImageResource(R.drawable.second_cloth);
                break;
            case "化妆品":
                imageProperty.setImageResource(R.drawable.second_cosmetics);
                break;
            case "日用" :
                imageProperty.setImageResource(R.drawable.daily);
                break;
            case "洗衣液" :
                imageProperty.setImageResource(R.drawable.second_laundry);
                break;
            case "纸巾" :
                imageProperty.setImageResource(R.drawable.second_paper);
                break;
            case "学习":
                imageProperty.setImageResource(R.drawable.study);
                break;
            case "课本":
                imageProperty.setImageResource(R.drawable.second_book);
                break;
            case "文具":
                imageProperty.setImageResource(R.drawable.second_stationery);
                break;
            case "交通":
                imageProperty.setImageResource(R.drawable.transport);
                break;
            case "地铁":
                imageProperty.setImageResource(R.drawable.second_metro);
                break;
            case "公交":
                imageProperty.setImageResource(R.drawable.second_bus);
                break;
            case "水果":
                imageProperty.setImageResource(R.drawable.fruit);
                break;
            case "零食":
                imageProperty.setImageResource(R.drawable.snacks);
                break;
            case "饮料":
                imageProperty.setImageResource(R.drawable.second_drink);
                break;
            case "运动":
                imageProperty.setImageResource(R.drawable.sport);
                break;
            case "健身房":
                imageProperty.setImageResource(R.drawable.second_gym);
                break;
            case "娱乐":
            case "电影" :
                imageProperty.setImageResource(R.drawable.entertainment);
                break;
            case "住房":
            case "名宿":
                imageProperty.setImageResource(R.drawable.house);
                break;
            case "租金":
                imageProperty.setImageResource(R.drawable.second_rent);
                break;
            case "房贷":
                imageProperty.setImageResource(R.drawable.second_mortgage);
                break;
            case "聚会":
                imageProperty.setImageResource(R.drawable.dating);
                break;
            case "旅行":
                imageProperty.setImageResource(R.drawable.travel);
                break;
            case "医疗":
                imageProperty.setImageResource(R.drawable.doctor);
                break;
            case "药":
                imageProperty.setImageResource(R.drawable.second_medicine);
                break;
            case "宠物":
                imageProperty.setImageResource(R.drawable.pet);
                break;
            case "食物":
                imageProperty.setImageResource(R.drawable.second_petfood);
                break;
            case "工资":
                imageProperty.setImageResource(R.drawable.salary);
                break;
            case "兼职":
                imageProperty.setImageResource(R.drawable.parttime);
                break;
            case "礼金":
                imageProperty.setImageResource(R.drawable.gift);
                break;
            case "转账":
                imageProperty.setImageResource(R.drawable.transfer);
                break;
            case "爸爸":
                imageProperty.setImageResource(R.drawable.father);
                break;
            case "妈妈":
                imageProperty.setImageResource(R.drawable.mother);
                break;
            case "我":
                imageProperty.setImageResource(R.drawable.me);
                break;
            default:
                imageProperty.setImageResource(R.drawable.setting);
                break;
        }
    }

    public static Date getMonthAgo(Date date, int distanceDay) {
        Calendar calendar = Calendar.getInstance();
        //calendar.set(2017, 0, 7);
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, distanceDay);
        Date endDate = calendar.getTime();
        return endDate;
    }
}
