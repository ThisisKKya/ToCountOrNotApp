package com.example.toaccountornot.utils;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;

public class First extends LitePalSupport {
    private String name;
    private int image;
    private ArrayList<String> second;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public ArrayList getSecond() {
        return second;
    }

    public void setSecond(String extra){
        second.add(extra);
    }
}



