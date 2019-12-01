package com.example.myfirstapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;

@Entity(tableName = "note_table")
public class Note {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_col")
    private int id;

    @TypeConverters({Converters.class})
    @ColumnInfo(name = "checks_col")
    private ArrayList<Checks> checks = new ArrayList<>();

    @ColumnInfo(name = "res_col")
    private String result;

    @ColumnInfo(name = "Health_col")
    private String Hresult;

    @ColumnInfo(name = "Social_col")
    private String Sresult;

    @ColumnInfo(name = "Personal_col")
    private String Presult;

    @ColumnInfo(name = "Date_col")
    private String date;


    public Note(int id , String result, ArrayList<Checks> checks ,String Hresult ,String Sresult ,String Presult ,String date) {
        this.id = id;
        this.checks = checks;
        this.result = result;
        this.Hresult = Hresult;
        this.Sresult = Sresult;
        this.Presult = Presult;
        this.date = date;

    }

    @Ignore
    public Note() {

    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }


    public void setResult(String result){
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    @TypeConverters({Converters.class})
    public void setChecks(ArrayList<Checks> checks){
        this.checks = checks;
    }

    @TypeConverters({Converters.class})
    public ArrayList<Checks> getChecks() {
        return checks;
    }

    public void setHresult(String Hresult){
        this.Hresult = Hresult;
    }

    public String getHresult() {
        return Hresult;
    }

    public void setSresult(String Sresult){
        this.Sresult = Sresult;
    }

    public String getSresult() {
        return Sresult;
    }

    public void setPresult(String Presult){
        this.Presult = Presult;
    }

    public String getPresult() {
        return Presult;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate() {
        return date;
    }


}

