package com.example.toaccountornot.utils;

public class CreateCard {
    private String name;
    private int imageId;
    private int signalId;

    public CreateCard(String name, int imageId, int signalId) {
        this.name = name;
        this.imageId = imageId;
        this.signalId = signalId;
    }
    public  String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public int getSignalId() {
        return signalId;
    }
}
