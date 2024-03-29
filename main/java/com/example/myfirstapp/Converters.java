package com.example.myfirstapp;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<Checks> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Checks>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Checks> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
