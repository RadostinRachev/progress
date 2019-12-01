package com.example.myfirstapp;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


public class StatisticsFragment extends Fragment implements View.OnClickListener {

    TextView statsview;
    EditText enter;
    Button Log;
    Button ClearRep;
    Button Show;
    ProgressBar hlt;
    ProgressBar soc;
    ProgressBar per;
    NoteViewModel model;
    String UserDate;

    String Healthstr;
    String Socialstr;
    String Personalstr;



    List<String> records;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        model = ViewModelProviders.of(getActivity()).get(NoteViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        enter = view.findViewById(R.id.enter_view);
        //enter.setText("Enter a date");

        statsview = view.findViewById(R.id.stats_view);
        Log = view.findViewById(R.id.button_log);
        ClearRep = view.findViewById(R.id.button_clr);
        Show = view.findViewById(R.id.button_show);
        hlt = view.findViewById(R.id.progressBar_stats_health);
        soc = view.findViewById(R.id.progressBar_stats_social);
        per = view.findViewById(R.id.progressBar_stats_personal);

        Log.setOnClickListener(this);
        Show.setOnClickListener(this);
        ClearRep.setOnClickListener(this);

        return view;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_show:

                UserDate = enter.getText().toString();
                //model.getResultNote(UserDate);
                Healthstr = model.getHealthNote(UserDate);
                Socialstr = model.getSocialNote(UserDate);
                Personalstr = model.getPersonalNote(UserDate);
                //model.getDateByRecords(UserDate);

                /* work with fetched results */
                if (Healthstr != null ) {
                    hlt.setProgress(Integer.valueOf(Healthstr));
                    soc.setProgress(Integer.valueOf(Socialstr));
                    per.setProgress(Integer.valueOf(Personalstr));
                }
                else {
                    AlertDialog.Builder norec = new AlertDialog.Builder(getContext());
                    norec.setTitle("Alert");
                    norec.setMessage("No record on this date");
                    norec.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog rec = norec.create();
                    rec.show();
                }


                break;


            case R.id.button_clr:

                AlertDialog.Builder alertcleaner = new AlertDialog.Builder(getContext());
                alertcleaner.setTitle("Alert");
                alertcleaner.setMessage("Choose wisely with what you proceed");
                alertcleaner.setPositiveButton("Clear current", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
                });
                alertcleaner.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertcleaner.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog warrning = alertcleaner.create();
                warrning.show();

                break;

            case R.id.button_log:

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
