package com.example.foodlist;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodlist.model.Food;
import com.example.foodlist.model.FoodList;

import java.io.InputStream;

public class FoodDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        Intent i = getIntent();
        //String foodName = i.getStringExtra("food_name");
        //int foodImage = i.getIntExtra("food_image", -1);
        int position = i.getIntExtra("position", -1);

        ImageView foodImageView = (ImageView) findViewById(R.id.food_image_view);
        TextView foodTextView = (TextView) findViewById(R.id.food_text_view);

        FoodList foodList = FoodList.getInstance(this);
        Food food = foodList.getData().get(position);

        AssetManager am = getAssets();
        try {
            InputStream stream = am.open(food.getFoodImage());
            Drawable drawable = Drawable.createFromStream(stream, null);
            foodImageView.setImageDrawable(drawable);
        } catch (Exception e) {
            e.printStackTrace();
        }

        foodTextView.setText(food.getFoodName());
    }
}
