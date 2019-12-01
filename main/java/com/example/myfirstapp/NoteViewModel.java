package com.example.myfirstapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.myfirstapp.Repository.NoteRepository;

import java.util.ArrayList;
import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    public NoteViewModel model;
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;
    public String NotesResult;
    public Checks NotesChecks;
    private String NotesHealth;
    private String NotesSocial;
    private String NotesPersonal;
    public List<String> Date;
    private int ddate;
    private int saved;

    private MutableLiveData<Integer> healthScore = new MutableLiveData<>();
    private MutableLiveData<Integer> socialScore = new MutableLiveData<>();
    private MutableLiveData<Integer> personalScore = new MutableLiveData<>();
    private MutableLiveData<String> date = new MutableLiveData<>();

    private ArrayList<Integer> healthArr = new ArrayList<>();
    private ArrayList<Integer> socialArr = new ArrayList<>();
    private ArrayList<Integer> personalArr = new ArrayList<>();

    private int sumHealth = 0 ;
    private int sumSocial = 0;
    private int sumPersonal = 0;

    private MutableLiveData<ArrayList<Checks>> arritem = new MutableLiveData<>();
    private MutableLiveData<Integer> currentScore = new MutableLiveData<>();
    private ArrayList<Integer> arrr = new ArrayList<>();
    private int sum = 0;

    public LiveData<Integer> getCurrentScore(){

        return currentScore;
    }

   // String string = getResultNote();

    public void setCurrentScore(Integer finito) {

        if (finito == 0) {
            sum = finito;
            currentScore.setValue(sum);
            arrr.clear();
        }
        else {
            arrr.add(finito);
            for (int i = 0; i < arrr.size(); i++) {

                sum += arrr.get(i);
            }
            currentScore.setValue(sum);
            sum = 0;
        }

    }

    public LiveData<ArrayList<Checks>> getMyItems(){

        return arritem;
    }

    public void setmyItems(ArrayList<Checks> finito) {
        arritem.setValue(finito);

    }
    public LiveData<String> getCurrDate() {
        return date;
    }

    public void setCurrDate(String dayDate) {
        date.setValue(dayDate);
    }

    public LiveData<Integer> getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(Integer healthScoree) {
        healthArr.add(healthScoree);
        for (int i = 0; i < healthArr.size(); i++) {

            sumHealth += healthArr.get(i);
        }
        healthScore.setValue(sumHealth);
        sumHealth = 0;

    }

    public LiveData<Integer> getSocialScore() {
        return socialScore;
    }

    public void setSocialScore(Integer socialScoree) {

        socialArr.add(socialScoree);
        for (int i = 0; i < socialArr.size(); i++) {

            sumSocial += socialArr.get(i);
        }
        socialScore.setValue(sumSocial);
        sumSocial = 0;
    }

    public LiveData<Integer> getPersonalScore() {
        return personalScore;
    }

    public void setPersonalScore(Integer personalScoree) {

        personalArr.add(personalScoree);
        for (int i = 0; i < personalArr.size(); i++) {

            sumPersonal += personalArr.get(i);
        }
        personalScore.setValue(sumPersonal);
        sumPersonal = 0;
    }

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
        //NotesResult = repository.getNotesResult();
    }

    public void insert(Note note) {

        repository.insert(note);

    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public String getResultNote(String daa) {
       NotesResult = repository.getNotesResult(daa);
        return NotesResult; }

    public String getHealthNote(String datata) {
        NotesHealth = repository.getHealthResult(datata);
        return NotesHealth; }

    public String getSocialNote(String datichka) {
        NotesSocial = repository.getSocialResult(datichka);
        return NotesSocial; }

    public String getPersonalNote(String datov) {
        NotesPersonal = repository.getPersonalResult(datov);
        return NotesPersonal; }

    public Checks getChecksNotes(String ad) {
        NotesChecks = repository.getNotesChecks(ad);
        return NotesChecks; }

    public List<String> getDateByRecords(String date){
        Date = repository.getDate(date);
        return Date;
    }

    public void UpdateSaved(String result , ArrayList<Checks> checked ,String health , String social , String personal,String dates){
        repository.updateSave(result,checked , health,social,personal,dates);
    }

    public void UpdateChanged(String dates,String result , String health , String social , String personal) {
        repository.updateChanged(dates,result,health,social,personal);
    }

    public int DaySaved(String dDate){
        ddate = repository.CheckDay(dDate);
        return ddate;
    }

    public int Saved(String datee){
        saved = repository.CheckSave(datee);
        return saved;
    }
}
