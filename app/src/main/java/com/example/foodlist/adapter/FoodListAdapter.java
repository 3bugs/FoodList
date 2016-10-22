package com.example.foodlist.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodlist.R;
import com.example.foodlist.model.Food;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Promlert on 10/22/2016.
 */

public class FoodListAdapter extends ArrayAdapter<Food> {

    private Context mContext;
    private int mLayoutResId;
    private ArrayList<Food> mFoodList;

    public FoodListAdapter(Context context, int resource, ArrayList<Food> foodList) {
        super(context, resource, foodList);

        mContext = context;
        mLayoutResId = resource;
        mFoodList = foodList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemLayout = View.inflate(mContext, mLayoutResId, null);
        ImageView foodImageView = (ImageView) itemLayout.findViewById(R.id.food_image_view);
        TextView foodTextView = (TextView) itemLayout.findViewById(R.id.food_text_view);

        Food food = mFoodList.get(position);

        AssetManager am = mContext.getAssets();
        try {
            InputStream stream = am.open(food.getFoodImage());
            Drawable drawable = Drawable.createFromStream(stream, null);
            foodImageView.setImageDrawable(drawable);
        } catch (Exception e) {
            Log.e("FoodList", "Error loading drawable from inputstream: " + e.getMessage());
            e.printStackTrace();
        }

        foodTextView.setText(food.getFoodName());

        return itemLayout;
    }
}
