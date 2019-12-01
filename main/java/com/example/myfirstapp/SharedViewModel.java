package com.example.myfirstapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public class SharedViewModel extends ViewModel {

    //public SharedViewModel model;
    private MutableLiveData<ArrayList<Checks>> arritem = new MutableLiveData<>();
    private MutableLiveData<Integer> currentScore = new MutableLiveData<>();
    private ArrayList<Integer> arrr = new ArrayList<>();
    private int sum = 0;

    public LiveData<Integer> getCurrentScore(){

        return currentScore;
    }

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

}
