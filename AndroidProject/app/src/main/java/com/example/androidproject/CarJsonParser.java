package com.example.androidproject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarJsonParser {
    public static List<Car> getObjectFromJson(String json) {
        List<Car> students;
        try {
            JSONArray jsonArray = new JSONArray(json);
            students = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = (JSONObject) jsonArray.get(i);
                Car student = new Car();
                student.setId(jsonObject.getInt("id"));
                student.setType(jsonObject.getString("type"));
                students.add(student);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return students;
    }
}