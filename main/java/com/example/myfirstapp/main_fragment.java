package com.example.myfirstapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class main_fragment extends Fragment {



    public main_fragment() {
        // Required empty public constructor
    }

    private NoteViewModel model;
    protected MainActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (MainActivity) context;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            Toast.makeText(getActivity(),"Welcome to next page",Toast.LENGTH_LONG).show();
            //Here is the place for arguments
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_fragment, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button addBtn = getView().findViewById(R.id.addBtn);
        Button secondActivity = getView().findViewById(R.id.secondActivitybtn);
        Button googlebtn = getView().findViewById(R.id.Googlebtn);
        Button exit = getView().findViewById(R.id.exitButton);

        secondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
                navController.navigate(R.id.action_main_fragment_to_myListViewFragment);


            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(fragmentInterface != null){
                    fragmentInterface.onButtonClick("This is my Message");
                }*/
                EditText FirstName = getView().findViewById(R.id.NameEditText);
                EditText SecondPass = getView().findViewById(R.id.PasswordEditText);
                TextView resultTextView = getView().findViewById(R.id.resultTextView);

                String name = FirstName.getText().toString();
                String password = SecondPass.getText().toString();
                String result = "We are ready!";

                    resultTextView.setText(result + " " + name + "!");


            }



        });

        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Google = "http://www.google.com";
                Uri webaddress = Uri.parse(Google);

                Intent gotoGoogle = new Intent(Intent.ACTION_VIEW, webaddress);
                if (gotoGoogle.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(gotoGoogle);
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             mActivity.finish();
             System.exit(0);

            }
        });


           /*You should inflate your layout in onCreateView but shouldn't
    initialize other views using findViewById in onCreateView.

    Because sometimes view is not properly initialized.
    So always use findViewById in onViewCreated(when view is fully created)
    and it also passes the view as parameter.

    onViewCreated is a make sure that view is fully created.*/
    }

    //View v = getView();

    /*public interface MyFragmentInterface {
        void onButtonClick(String msg);

    } */

    //MyFragmentInterface fragmentInterface;



   /*     secondActivity.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent StartIntent = new Intent(getApplicationContext(),SecondActivity.class);
            StartIntent.putExtra("com.example.MESSAGE", "Hello World!");
            startActivity(StartIntent);

        }
    });  */




}
