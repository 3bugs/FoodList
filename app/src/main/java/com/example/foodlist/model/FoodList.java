package com.example.foodlist.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.foodlist.db.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Promlert on 10/22/2016.
 */

public class FoodList {

    private Context mContext;
    private ArrayList<Food> mFoodList;
    private static FoodList mInstance;

    public static FoodList getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FoodList(context);
        }
        return mInstance;
    }

    private FoodList(Context context) {
        mContext = context;
        mFoodList = new ArrayList<>();

/*
        mFoodList.add(new Food("ข้าวไข่เจียว", "kao_kai_jeaw.jpg"));
        mFoodList.add(new Food("ข้าวหมูแดง", "kao_moo_dang.jpg"));
        mFoodList.add(new Food("ข้าวมันไก่", "kao_mun_kai.jpg"));
        mFoodList.add(new Food("ข้าวหน้าเป็ด", "kao_na_ped.jpg"));
        mFoodList.add(new Food("ข้าวผัด", "kao_pad.jpg"));
        mFoodList.add(new Food("ผัดซีอิ๊ว", "pad_sie_eew.jpg"));
        mFoodList.add(new Food("ผัดไทย", "pad_thai.jpg"));
        mFoodList.add(new Food("ราดหน้า", "rad_na.jpg"));
        mFoodList.add(new Food("ส้มตำ ไก่ย่าง", "som_tum_kai_yang.jpg"));
*/
    }

    public ArrayList<Food> getData() {
        mFoodList.clear();

        DatabaseHelper helper = new DatabaseHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        //db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE _id = ?", new String[]{"1"});

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME));
            String image = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_IMAGE));

            Food food = new Food(name, image);
            mFoodList.add(food);
        }
        cursor.close();
        return mFoodList;
    }
}
