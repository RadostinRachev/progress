package com.example.myfirstapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT res_col FROM note_table WHERE Date_col =:dat")
    String getResultNote(String dat);

    @Query("SELECT Health_col FROM note_table WHERE Date_col =:datata")
    String getHealthNote(String datata);

    @Query("SELECT Social_col FROM note_table WHERE Date_col =:datichka")
    String getSocialNote(String datichka);

    @Query("SELECT Personal_col FROM note_table WHERE Date_col =:datov")
    String getPersonalNote(String datov);

    @TypeConverters({Converters.class})
    @Query("SELECT checks_col FROM note_table WHERE Date_col =:datch")
    Checks getChecksNotes(String datch);

    //@Query("SELECT res_col,Health_col ,Social_col,Personal_col,Date_col FROM note_table WHERE Date_col =:date")
    //List<String> getRecordsByDate(String date);

    @Query("UPDATE note_table SET res_col =:result, checks_col =:checks,Health_col =:h, Social_col =:s, Personal_col =:p WHERE Date_col =:dati")
    void updateSave(String result, ArrayList<Checks> checks ,String h, String s, String p ,String dati);

    @Query("UPDATE note_table SET res_col =:result, Health_col =:Hresult, Social_col =:Sresult, Personal_col =:Presult WHERE Date_col =:Date")
    void updateChanged(String Date ,String result, String Hresult ,String Sresult , String Presult);

    @Query("SELECT COUNT(checks_col) FROM note_table WHERE Date_col =:datee")
    int CheckSave(String datee);

    @Query("SELECT COUNT(Date_col) FROM note_table WHERE Date_col =:date ")
    int CheckDay(String date);
}
