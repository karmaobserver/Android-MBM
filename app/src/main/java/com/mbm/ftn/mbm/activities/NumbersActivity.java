package com.mbm.ftn.mbm.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.fragments.NumberFragment;

/**
 * Created by Makso on 5/22/2017.
 */

public class NumbersActivity extends BaseActivity {

    private static final String TAG = "NumbersActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*if (savedInstanceState == null) {

            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Bundle bundle = new Bundle();
            bundle.putString("numberType", "emergency_services");
            NumberFragment numberFragment = new NumberFragment();
            numberFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.numbers_emergency_services, numberFragment, "tag1");


            Bundle bundle2 = new Bundle();
            bundle2.putString("numberType", "qwe");
            NumberFragment numberFragment2 = new NumberFragment();
            numberFragment2.setArguments(bundle2);
            fragmentTransaction.add(R.id.numbers_health_care, numberFragment2, "tag2");


            Bundle bundle3 = new Bundle();
            bundle3.putString("numberType", "ww");
            NumberFragment numberFragment3 = new NumberFragment();
            numberFragment3.setArguments(bundle3);
            fragmentTransaction.add(R.id.numbers_mobile_operators, numberFragment3, "tag3");

            fragmentTransaction.commit();

        }*/

        if (savedInstanceState == null) {

            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

           // Log.d("KREIRANJE_FRAGMENTA", "KREIRA_SE");
            Bundle bundle = new Bundle();
            bundle.putString("numberType", getResources().getString(R.string.numbers_emergency_services));
            NumberFragment numberFragment = new NumberFragment();
            numberFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.numbers_emergency_services, numberFragment, "tag1");

            Bundle bundle2 = new Bundle();
            bundle2.putString("numberType", getResources().getString(R.string.numbers_mobile_operators));
            NumberFragment numberFragment2 = new NumberFragment();
            numberFragment2.setArguments(bundle2);
            fragmentTransaction.add(R.id.numbers_mobile_operators, numberFragment2, "tag2");

            Bundle bundle3 = new Bundle();
            bundle3.putString("numberType", getResources().getString(R.string.numbers_health_care));
            NumberFragment numberFragment3 = new NumberFragment();
            numberFragment3.setArguments(bundle3);
            fragmentTransaction.add(R.id.numbers_health_care, numberFragment3, "tag3");

            fragmentTransaction.commit();


        }


    }
}
