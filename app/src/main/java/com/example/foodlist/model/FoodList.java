package com.example.foodlist.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.foodlist.db.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Promlert on 10/22/2016.
 */

public class FoodList {

    private static final String TAG = "FoodList";

    private Context mContext;
    private ArrayList<Food> mFoodList;
    private static FoodList mInstance;

    private static final OkHttpClient mClient = new OkHttpClient();
    private ResponseStatus mResponseStatus;

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

    public interface GetDataCallback {
        void onSuccess(ArrayList<Food> foodList);
        void onError(ResponseStatus responseStatus);
    }
    public interface AddFoodCallback {
        void onSuccess();
        void onError(String errorMessage);
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

    public void getDataFromWebService(final GetDataCallback callback) {
        mFoodList.clear();

        Request request = new Request.Builder()
                .url("http://10.0.3.2/foodlist/get_food_list.php")
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Network Error!");
                mResponseStatus = new ResponseStatus(false, "Network Error");
                callback.onError(mResponseStatus);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonResult = response.body().string();
                Log.i(TAG, jsonResult);

                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        // อ่านข้อมูลจาก server สำเร็จ
                        mResponseStatus = new ResponseStatus(true, null);
                        JSONArray jsonArrayFoodData = jsonObject.getJSONArray("food_data");

                        for (int i = 0; i < jsonArrayFoodData.length(); i++) {
                            JSONObject jsonFood = jsonArrayFoodData.getJSONObject(i);
                            String name = jsonFood.getString("name");
                            String image = jsonFood.getString("image");

                            Food food = new Food(name, image);
                            mFoodList.add(food);
                        }

                        new Handler(Looper.getMainLooper()).post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onSuccess(mFoodList);
                                    }
                                }
                        );

                    } else if (success == 0) {
                        // อ่านข้อมูลจาก server ไม่สำเร็จ
                        mResponseStatus = new ResponseStatus(false, jsonObject.getString("message"));
                        callback.onError(mResponseStatus);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Error parsing JSON!");
                }
            }
        });
    }

    public void addFood(String name, final AddFoodCallback callback) {
        Request request = new Request.Builder()
                .url("http://10.0.3.2/foodlist/add_food.php?name=" + name)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError("Network Error!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonResult = response.body().string();

                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);
                    int success = jsonObject.getInt("success");

                    if (success == 1) {
                        new Handler(Looper.getMainLooper()).post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onSuccess();
                                    }
                                }
                        );
                    } else if (success == 0) {
                        final String errorMessage = jsonObject.getString("message");

                        new Handler(Looper.getMainLooper()).post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onError(errorMessage);
                                    }
                                }
                        );
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
