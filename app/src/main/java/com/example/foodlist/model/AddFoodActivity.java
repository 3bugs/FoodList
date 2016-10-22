package com.example.foodlist.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.foodlist.R;
import com.example.foodlist.db.DatabaseHelper;

public class AddFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        final EditText foodNameEditText = (EditText) findViewById(R.id.food_name_edit_text);
        Button addButton = (Button) findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodName = foodNameEditText.getText().toString();

                DatabaseHelper helper = new DatabaseHelper(AddFoodActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues cv = new ContentValues();
                cv.put(DatabaseHelper.COL_NAME, foodName);
                db.insert(DatabaseHelper.TABLE_NAME, null, cv);

                finish();
            }
        });
    }
}
