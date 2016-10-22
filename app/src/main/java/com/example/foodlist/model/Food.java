package com.example.foodlist.model;

/**
 * Created by Promlert on 10/22/2016.
 */

public class Food {

    private String mFoodName;
    private String mFoodImage;

    public Food(String foodName, String foodImage) {
        this.mFoodName = foodName;
        this.mFoodImage = foodImage;
    }

    public String getFoodName() {
        return mFoodName;
    }

    public String getFoodImage() {
        return mFoodImage;
    }
}
