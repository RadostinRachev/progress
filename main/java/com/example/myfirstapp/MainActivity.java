package com.example.myfirstapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

private DetailActivity mContent;
private NoteViewModel mViewModel;
private DrawerLayout drawer;
private NoteDataBase instance;
private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);


        mViewModel.getCurrentScore().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {

            }
        });

        mViewModel.getMyItems().observe(this, new Observer<ArrayList<Checks>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Checks> checks) {

            }
        });

        mViewModel.getHealthScore().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer health) {

            }
        });

        mViewModel.getSocialScore().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer social) {

            }
        });

        mViewModel.getPersonalScore().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer personal) {

            }
        });


        //mViewModel.deleteAllNotes();


        String forres = mViewModel.getResultNote("0");
        String res = forres.replaceAll("[a-z]","");
        String fin = res.replaceAll(" ","");
        Toast.makeText(this , "" + fin , Toast.LENGTH_SHORT).show();
        Integer intres = (Integer.valueOf(fin));
        mViewModel.setCurrentScore(intres);


        ArrayList<Checks> newList = new ArrayList<>(7);
        Checks itemList = mViewModel.getChecksNotes("0");
        String temp = itemList.getItemName();
        Toast.makeText(this , "" + temp, Toast.LENGTH_SHORT).show();
        for (int j = 2 ; j < temp.length() ; j++){
            if (temp.charAt(j-1) == ':' && temp.charAt(j) == '"') {
                if (temp.charAt(j+1) == 'n'){
                    newList.add(new Checks("no"));
                    continue;
                }
                else if (temp.charAt(j+1) == 'C'){
                    newList.add(new Checks("Check"));
                    continue;
                }
            }

        }
        mViewModel.setmyItems(newList);

        String heal = mViewModel.getHealthNote("0");
        String soc = mViewModel.getSocialNote("0");
        String pers = mViewModel.getPersonalNote("0");

        mViewModel.setHealthScore(Integer.valueOf(heal));
        mViewModel.setSocialScore(Integer.valueOf(soc));
        mViewModel.setPersonalScore(Integer.valueOf(pers));

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            //mContent = getSupportFragmentManager().getFragment(savedInstanceState, "DetailActivity");


        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this ,drawer , toolbar ,
                R.string.navigation_drawer_open , R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "DetailActivity", mContent);
    }


    @Override
public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_stats:

                NavController navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);
                navController.navigate(R.id.my_nav_host_fragment_to_statisticsFragment);

                if (drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;

        }


        return true;
        }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    @Override
    public void finish() {
        super.finish();
        NoteDataBase.getInstance(this).close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
