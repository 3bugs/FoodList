package com.example.foodlist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.foodlist.adapter.FoodListAdapter;
import com.example.foodlist.model.Food;
import com.example.foodlist.model.FoodList;
import com.example.foodlist.model.ResponseStatus;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView mFoodListView;
    private FoodList mFoodList;

    private static final OkHttpClient mClient = new OkHttpClient();

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

        Button connectServerButton = (Button) findViewById(R.id.connect_server_button);
        connectServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectServer();
            }
        });
    } // ปิด onCreate

    private void connectServer() {
        Request request = new Request.Builder()
                .url("http://10.0.3.2/foodlist/get_food_list.php")
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.i(TAG, result);

                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                Toast t = Toast.makeText(
                                        MainActivity.this,
                                        result,
                                        Toast.LENGTH_LONG
                                );
                                t.show();
                            }
                        }
                );
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    } // ปิด onStart

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");

        final ProgressDialog dialog = ProgressDialog.show(
                this,
                "Food List",
                "Loading"
        );

        mFoodList.getDataFromWebService(new FoodList.GetDataCallback() {
            @Override
            public void onSuccess(ArrayList<Food> foodList) {
                dialog.dismiss();

                FoodListAdapter adapter = new FoodListAdapter(
                        MainActivity.this,
                        R.layout.list_item,
                        foodList
                );

                mFoodListView.setAdapter(adapter);
            }

            @Override
            public void onError(ResponseStatus responseStatus) {
                dialog.dismiss();
            }
        });
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
