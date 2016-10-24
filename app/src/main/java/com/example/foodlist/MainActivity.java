package com.example.foodlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.foodlist.adapter.FoodListAdapter;
import com.example.foodlist.model.FoodList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView mFoodListView;
    private FoodList mFoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFoodList = FoodList.getInstance(this);

        mFoodListView = (ListView) findViewById(R.id.food_list_view);
        mFoodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast t = Toast.makeText(
                        MainActivity.this,
                        String.valueOf(position),
                        Toast.LENGTH_SHORT
                );
                t.show();

                Intent i = new Intent(MainActivity.this, FoodDetailActivity.class);
                //Food food = foodList.getData().get(position);
                //i.putExtra("food_name", food.getFoodName());
                //i.putExtra("food_image", food.getFoodImage());
                i.putExtra("position", position);
                startActivity(i);
            }
        });

    } // ปิด onCreate

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    } // ปิด onStart

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");

        FoodListAdapter adapter = new FoodListAdapter(
                this,
                R.layout.list_item,
                mFoodList.getData()
        );

        mFoodListView.setAdapter(adapter);
    } // ปิด onResume

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    } // ปิด onPause

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    } // ปิด onStop

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    } // ปิด onDestroy

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    } // ปิด onRestart

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            Intent i = new Intent(this, AddFoodActivity.class);
            startActivity(i);
        }
        return true;
    }
}
