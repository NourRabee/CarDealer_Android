package com.example.androidproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {
    Activity activity;

    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpManager.getData(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (s != null) {
            List<Car> cars = CarJsonParser.getObjectFromJson(s);

            assert cars != null;
            Car.cars.addAll(cars);
            Car.fillCarsWithData();

            Toast.makeText(activity, "Connected", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(activity, LoginAndRegActivity.class);
            activity.startActivity(intent);
        } else
            Toast.makeText(activity, "Connection error", Toast.LENGTH_SHORT).show();

    }
}