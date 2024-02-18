package com.example.androidproject;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button connectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseHelper databaseHelper = new DataBaseHelper(MainActivity.this, "db", null, 1);

        connectButton = findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
                connectionAsyncTask.execute("https://658582eb022766bcb8c8c86e.mockapi.io/api/mock/rest-apis/encs5150/car-types");


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this, "db", null, 1);
                        dataBaseHelper.seedWithCars();
                    }
                }, 10000);
            }
        });
    }
}


