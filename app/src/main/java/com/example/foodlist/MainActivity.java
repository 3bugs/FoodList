package com.example.foodlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    //private ArrayList<Food> mFoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FoodList foodList = FoodList.getInstance(this);

        ListView foodListView = (ListView) findViewById(R.id.food_list_view);
        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        FoodListAdapter adapter = new FoodListAdapter(
                this,
                R.layout.list_item,
                foodList.getData()
        );

        foodListView.setAdapter(adapter);
    }

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
