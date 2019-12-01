package com.example.myfirstapp;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class DetailActivity extends Fragment implements View.OnClickListener {

    ArrayList<Integer> selection = new ArrayList<>();
    TextView score_text;
    public int final_taskSelection = 0;
    public NoteViewModel model;

    ArrayList<Integer> health = new ArrayList<>();
    String[] listHealth;
    boolean[] checkedHealth;
    Integer healthScore = 0;

    ArrayList<Integer> social = new ArrayList<>();
    String[] listSocial;
    boolean[] checkedSocial;
    Integer socialScore = 0;

    ArrayList<Integer> personal = new ArrayList<>();
    String[] listPersonal;
    boolean[] checkedPersonal;
    Integer personalScore = 0;

    ProgressBar healthProg;
    ProgressBar socialProg ;
    ProgressBar personalProg;

    TextView healthText;
    TextView socialText;
    TextView personText;

    String CurrDate;
    public DetailActivity() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        model = ViewModelProviders.of(getActivity()).get(NoteViewModel.class);

        model.getHealthScore().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null) {
                    healthProg.setProgress(integer);
                    healthText.setText(integer + "%");
                }
            }
        });

        model.getSocialScore().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null) {
                    socialProg.setProgress(integer);
                    socialText.setText(integer + "%");
                }
            }
        });

        model.getPersonalScore().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null) {
                    personalProg.setProgress(integer);
                    personText.setText(integer + "%");
                }
            }
        });

        model.getCurrentScore().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {

                //score_text.setText(s);

            }
        });

        model.getCurrDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                CurrDate = s;
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's state here
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.activity_detail, container, false);

        healthProg = view.findViewById(R.id.progressBar_health);
        socialProg = view.findViewById(R.id.progressBar_social);
        personalProg = view.findViewById(R.id.progressBar_personal);

        Button health = view.findViewById(R.id.button_health);
        Button social = view.findViewById(R.id.button_social);
        Button personal = view.findViewById(R.id.button_personal);

        healthText = view.findViewById(R.id.txtProgressHealth);
        socialText = view.findViewById(R.id.txtProgressSocial);
        personText = view.findViewById(R.id.txtProgressPersonal);

        score_text = view.findViewById(R.id.scoreView);
        score_text.setEnabled(false);
        Button btt = view.findViewById(R.id.butt_score);

        btt.setOnClickListener(this);
        health.setOnClickListener(this);
        social.setOnClickListener(this);
        personal.setOnClickListener(this);

        listHealth = getResources().getStringArray(R.array.Health_items);
        checkedHealth = new boolean[listHealth.length];

        listSocial = getResources().getStringArray(R.array.Social_items);
        checkedSocial = new boolean[listSocial.length];

        listPersonal = getResources().getStringArray(R.array.Personal_items);
        checkedPersonal = new boolean[listPersonal.length];

        //Intent in = getIntent();
        //int index = in.getIntExtra("com.example.INDEX",-1);

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.butt_score:

                final_taskSelection = socialScore + personalScore + healthScore;

                if (final_taskSelection == 0){

                    AlertDialog.Builder alertbuilder = new AlertDialog.Builder(getContext());
                    alertbuilder.setTitle("Alert");
                    alertbuilder.setMessage("You must select at least one activity to submit!");
                    alertbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog warrning = alertbuilder.create();
                    warrning.show();
                    break;
                }

/*
                for (Integer Selections : selection) {

                    final_taskSelection += Selections;
                }

*/

                String dayScore = String.valueOf(final_taskSelection);
                String HScore = String.valueOf(healthScore);
                String SScore = String.valueOf(socialScore);
                String PScore = String.valueOf(personalScore);
                Note note = new Note(0, dayScore , null , HScore , SScore, PScore , CurrDate);

                if(model.DaySaved(CurrDate) > 0){
                    model.UpdateChanged(CurrDate ,dayScore,HScore, SScore ,PScore);
                }

                else {
                    model.insert(note);
                    Log.d("APPINFO", "Insertion Complete");

                    NoteDataBase.getInstance(getActivity()).checkpoint();
                }

                score_text.setText("Score is confirmed");
                score_text.setEnabled(true);

                model.setPersonalScore(personalScore);
                model.setSocialScore(socialScore);
                model.setHealthScore(healthScore);
                model.setCurrentScore(final_taskSelection);

                break;

            case R.id.button_health:
                AlertDialog.Builder healthBuilder = new AlertDialog.Builder(getContext());
                healthBuilder.setTitle("Health Activities");
                healthBuilder.setMultiChoiceItems(listHealth, checkedHealth, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(!health.contains(position)){
                                health.add(position);
                            }
                            else if (health.contains(position)) {
                                health.remove(position);
                            }
                        }
                    }
                });

                healthBuilder.setCancelable(false);
                healthBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                     for (int i = 0 ; i < health.size() ; i++){
                         if (health.get(i) == 0){
                             healthScore += 3;
                             continue;
                         }
                         else if (health.get(i) == 1){
                             healthScore += 4;
                             continue;
                         }
                         else if (health.get(i) == 2){
                             healthScore += 3;
                             continue;
                         }
                         else if (health.get(i) == 3){
                             healthScore += 2;
                             continue;
                         }
                         else if (health.get(i) == 4){
                             healthScore += 3;
                             continue;
                         }
                         else if (health.get(i) == 5){
                             healthScore += 3;
                             continue;
                         }
                         else if (health.get(i) == 6){
                             healthScore += 3;
                             continue;
                         }
                         else if (health.get(i) == 7){
                             healthScore += 3;
                             continue;
                         }
                     }
                        healthProg.setProgress(healthProg.getProgress() + healthScore);

                    }
                });

                healthBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                healthBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int j = 0 ; j < checkedHealth.length ; j++){
                            checkedHealth[j] = false;
                            health.clear();
                            if (healthProg.getProgress() >= healthScore) {
                                healthProg.setProgress(healthProg.getProgress() - healthScore);
                            }
                            healthScore = 0;

                        }
                    }
                });
                AlertDialog mD = healthBuilder.create();
                mD.show();
                break;

            case R.id.button_social:
                AlertDialog.Builder socialBuilder = new AlertDialog.Builder(getContext());
                socialBuilder.setTitle("Social Activities");
                socialBuilder.setMultiChoiceItems(listSocial, checkedSocial, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(!social.contains(position)){
                                social.add(position);
                            }
                            else if (social.contains(position)) {
                                social.remove(position);
                            }
                        }
                    }
                });

                socialBuilder.setCancelable(false);
                socialBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0 ; i < social.size() ; i++){
                            if (social.get(i) == 0){
                                socialScore += 3;
                                continue;
                            }
                            else if (social.get(i) == 1){
                                socialScore += 4;
                                continue;
                            }
                            else if (social.get(i) == 2){
                                socialScore += 3;
                                continue;
                            }
                            else if (social.get(i) == 3){
                                socialScore += 2;
                                continue;
                            }
                            else if (social.get(i) == 4){
                                socialScore += 3;
                                continue;
                            }
                            else if (social.get(i) == 5){
                                socialScore += 3;
                                continue;
                            }
                            else if (social.get(i) == 6){
                                socialScore += 3;
                                continue;
                            }
                            else if (social.get(i) == 7){
                                socialScore += 3;
                                continue;
                            }
                        }
                        socialProg.setProgress(socialProg.getProgress() + socialScore);
                    }
                });

                socialBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                socialBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int j = 0 ; j < checkedSocial.length ; j++){
                            checkedSocial[j] = false;
                            social.clear();
                            if (socialProg.getProgress() >= socialScore) {
                                socialProg.setProgress(socialProg.getProgress() - socialScore);
                            }
                            socialScore = 0;
                        }
                    }
                });
                AlertDialog msD = socialBuilder.create();
                msD.show();
                break;

            case R.id.button_personal:
                AlertDialog.Builder personalBuilder = new AlertDialog.Builder(getContext());
                personalBuilder.setTitle("Personal Activities");
                personalBuilder.setMultiChoiceItems(listPersonal, checkedPersonal, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(!personal.contains(position)){
                                personal.add(position);
                            }
                            else if (personal.contains(position)) {
                                personal.remove(position);
                            }
                        }
                    }
                });

                personalBuilder.setCancelable(false);
                personalBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0 ; i < personal.size() ; i++){
                            if (personal.get(i) == 0){
                                personalScore += 3;
                                continue;
                            }
                            else if (personal.get(i) == 1){
                                personalScore += 4;
                                continue;
                            }
                            else if (personal.get(i) == 2){
                                personalScore += 3;
                                continue;
                            }
                            else if (personal.get(i) == 3){
                                personalScore += 2;
                                continue;
                            }
                            else if (personal.get(i) == 4){
                                personalScore += 3;
                                continue;
                            }
                            else if (personal.get(i) == 5){
                                personalScore += 3;
                                continue;
                            }
                            else if (personal.get(i) == 6){
                                personalScore += 3;
                                continue;
                            }
                            else if (personal.get(i) == 7){
                                personalScore += 3;
                                continue;
                            }
                        }
                        personalProg.setProgress(personalProg.getProgress() + personalScore);
                    }
                });

                personalBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                personalBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int j = 0 ; j < checkedPersonal.length ; j++){
                            checkedPersonal[j] = false;
                            personal.clear();
                            if (personalProg.getProgress() >= personalScore) {
                                personalProg.setProgress(personalProg.getProgress() - personalScore);
                            }
                            personalScore = 0;
                        }
                    }
                });
                AlertDialog mpD = personalBuilder.create();
                mpD.show();
                break;
        }

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*You should inflate your layout in onCreateView but shouldn't
    initialize other views using findViewById in onCreateView.

    Because sometimes view is not properly initialized.
    So always use findViewById in onViewCreated(when view is fully created)
    and it also passes the view as parameter.

    onViewCreated is a make sure that view is fully created.*/
    }


}
