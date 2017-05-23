package com.mbm.ftn.mbm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.activities.CompassActivity;
import com.mbm.ftn.mbm.activities.Flashlight2Activity;
import com.mbm.ftn.mbm.activities.ImportantNumbersActivity;
import com.mbm.ftn.mbm.activities.SurvivalTextActivity;
import com.mbm.ftn.mbm.activities.WeatherActivity;
import com.mbm.ftn.mbm.dao.NumberDao;
import com.mbm.ftn.mbm.dao.NumberListDao;
import com.mbm.ftn.mbm.database.DatabaseManager;
import com.mbm.ftn.mbm.models.Number;
import com.mbm.ftn.mbm.models.NumberList;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    NumberDao numberDao = null;
    NumberListDao numberListDao = null;
    Number number = null;
    NumberList numberList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Database init
        DatabaseManager.init(this);
        //databaseInit();

   /*     List<Number> numbers = numberDao.findAll();
        List<NumberList> numbersLists = numberListDao.findAll();
        Log.d("NUMBERS", "Numbers: " + numbers.size());
        Log.d("NUMBERSLISTS", "NumbersLists: " + numbersLists.size());*/

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);



        ImageButton flashlightButton2 = (ImageButton) findViewById(R.id.button_topleft);
        flashlightButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Flashlight2Activity.class);
                startActivity(intent);
            }
        });

        ImageButton flashlightButton3 = (ImageButton) findViewById(R.id.button_topright);
        flashlightButton3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CompassActivity.class);
                startActivity(intent);
            }
        });

        ImageButton survivalButton = (ImageButton) findViewById(R.id.button_bottomleft);
        survivalButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SurvivalTextActivity.class);
                startActivity(intent);
            }
        });

        ImageButton importantNumbers = (ImageButton) findViewById(R.id.button_middleright);
        importantNumbers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ImportantNumbersActivity.class);
                startActivity(intent);
            }
        });

        ImageButton weather = (ImageButton) findViewById(R.id.button_bottomright);
        weather.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WeatherActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_gps:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void databaseInit() {

        numberDao = new NumberDao(this);
        numberListDao = new NumberListDao(this);


        NumberList emergencyList = new NumberList(getResources().getString(R.string.numbers_emergency_services));
        numberListDao.create(emergencyList);
        Number number1 = new Number("Hitna pomoć", "194", "Hitne intervencije hitne pomoći", emergencyList, null, null);
        Number number2 = new Number("Policija", "192", "Hitne intervencije policije", emergencyList, "http://www.mup.vladars.net/", null);
        Number number3 = new Number("Vatrogasci", "193", null, emergencyList, null, "Omladinskih brigada 1, Beograd");
        Number number4 = new Number("Gorska služba", "194", "Gorska služba spašavanja", emergencyList, "http://www.gss.rs/", "Ruzveltova 18, Zvezdara");


        NumberList mobileOperatorsList = new NumberList(getResources().getString(R.string.numbers_mobile_operators));
        numberListDao.create(mobileOperatorsList);
        Number number5 = new Number("Vip mobile", "+381601234", null, mobileOperatorsList, null, null);
        Number number6 = new Number("Telenor", "+3816390000", null, mobileOperatorsList, null, null);
        Number number7 = new Number("MTS", "+38164789", null, mobileOperatorsList, "https://www.mts.rs/", "Branislava Nusića");


        NumberList healthCare = new NumberList(getResources().getString(R.string.numbers_health_care));
        numberListDao.create(healthCare);
        Number number8 = new Number("Dom zdravlja Liman", "+381214807187", null, healthCare, null, null);
        Number number9 = new Number("Dom zdravlja bistrica", "+38121401122", null, healthCare, null, null);
        Number number10 = new Number("Kliničko bolnički centar", "+381214843484", null, healthCare, null, null);
        Number number11 = new Number("Dečja bolnija", "+38121422699", "Bolnica za decu", healthCare, null, null);
       /* Number number8 = new Number("Automoto Savez Srbije", "987", "Pomoć na putu, stanje na putevima, međunarodna dokumenta, saobraćajna...", healthCare);
        Number number9 = new Number("Aerodorom Nikola Tesla", "+381112094444", null, healthCare);
        Number number10 = new Number("Aerodrom Konstantin Veliki", "+38118585858", null, healthCare);
        Number number11 = new Number("Jat Airwazs", "+381113114222", null, healthCare);*/

        numberDao.create(number1);
        numberDao.create(number2);
        numberDao.create(number3);
        numberDao.create(number4);
        numberDao.create(number5);
        numberDao.create(number6);
        numberDao.create(number7);
        numberDao.create(number8);
        numberDao.create(number9);
        numberDao.create(number10);
        numberDao.create(number11);


        Log.d("DATABASE_INIT", "Database has initilize");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
