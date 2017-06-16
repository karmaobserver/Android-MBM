package com.mbm.ftn.mbm.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.dao.NumberDao;
import com.mbm.ftn.mbm.dao.NumberListDao;
import com.mbm.ftn.mbm.fragments.NumberFragment;
import com.mbm.ftn.mbm.models.Number;
import com.mbm.ftn.mbm.models.NumberList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makso on 5/22/2017.
 */

public class NumbersActivity extends BaseActivity {

    private static final String TAG = "NumbersActivity";

    NumberListDao numberListDao = null;
    NumberDao numberDao = null;

    private List<NumberList> numberLists = new ArrayList<>();

    private List<NumberList> numberListsByCity = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        Log.d("CITY", "JE U ACTIVITY " +city);

        numberListDao = new NumberListDao(getApplicationContext());
        numberDao = new NumberDao(getApplicationContext());

        numberLists = numberListDao.findAll();

        //Adding programmatically fragments containers in layout
        LinearLayout linearForFragments = (LinearLayout) findViewById(R.id.linear_for_fragments);

        for (int i = 0; i<numberLists.size()+1; i++) {   //Zasto mora +1????
            FrameLayout barFrameLayout = new FrameLayout(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER);
            barFrameLayout.setLayoutParams(params);

            barFrameLayout.setId(i);

            linearForFragments.addView(barFrameLayout);
        }

        if (savedInstanceState == null) {

            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            //dodavanje fragmenata u kontainere
            int idOfFragmentContainers = 0;
            for (NumberList numberList : numberLists) {

                //If there is no city (all numbers)
                if (city.equals("none")) {
                    Log.d("NONE", "JE");
                    idOfFragmentContainers++;
                    Bundle bundle = new Bundle();
                    bundle.putString("numberType", numberList.getName());
                    bundle.putString("city", city);
                    NumberFragment numberFragment = new NumberFragment();
                    numberFragment.setArguments(bundle);
                    //getName je za TAG naziv neki
                    fragmentTransaction.add(idOfFragmentContainers, numberFragment, numberList.getName());

                    //if there is city choosen or country
                } else {
                    Log.d("ELSE", "JE");
                    List<Number> numberListsTest = new ArrayList<>();
                    numberListsTest = numberDao.findAllByCityAndListName(city, numberList.getName());
                    if (numberListsTest.size() == 0) {
                        //if there is no numbers for specific list numbers, we won't create fragment for it. Otherwise we do.
                    } else {

                        idOfFragmentContainers++;
                        Bundle bundle = new Bundle();
                        bundle.putString("numberType", numberList.getName());
                        bundle.putString("city", city);
                        NumberFragment numberFragment = new NumberFragment();
                        numberFragment.setArguments(bundle);
                        //getName je za TAG naziv neki
                        fragmentTransaction.add(idOfFragmentContainers, numberFragment, numberList.getName());
                    }
                }
            }
            fragmentTransaction.commit();

        }


    }
}
