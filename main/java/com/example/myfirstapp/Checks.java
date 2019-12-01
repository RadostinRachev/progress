package com.example.myfirstapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;

public class Checks  {


    @ColumnInfo(name = "checks_col")
    public String name = "";

    @Ignore
    public Checks() {
    }

    public Checks(String name){
        this.name = name;
    }

    public String getItemName() {
        return name;
    }

    public void setItemName(String name) {
        this.name = name;
    }

}
