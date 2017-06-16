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
import com.mbm.ftn.mbm.dao.CityDao;
import com.mbm.ftn.mbm.dao.NumberDao;
import com.mbm.ftn.mbm.dao.NumberListDao;
import com.mbm.ftn.mbm.dao.SurvivalTextDao;
import com.mbm.ftn.mbm.database.DatabaseManager;
import com.mbm.ftn.mbm.models.City;
import com.mbm.ftn.mbm.models.Number;
import com.mbm.ftn.mbm.models.NumberList;
import com.mbm.ftn.mbm.models.SurvivalText;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    NumberDao numberDao = null;
    NumberListDao numberListDao = null;
    CityDao cityDao = null;
    Number number = null;
    NumberList numberList = null;
    SurvivalTextDao survivalTextDao = null;
    SurvivalText survivalText = null;

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
        cityDao = new CityDao(this);

        City city1 = new City("Beograd");
        City city2 = new City("Novi Sad");
        City city3 = new City("Srbija");
        City city4 = new City("Niš");

        cityDao.create(city1);
        cityDao.create(city2);
        cityDao.create(city3);
        cityDao.create(city4);

        NumberList emergencyList = new NumberList("Hitne službe");
        numberListDao.create(emergencyList);
        Number number1 = new Number("Hitna pomoć", "194", "Hitne intervencije hitne pomoći", emergencyList, null, null, city3);
        Number number2 = new Number("Policija", "192", "Hitne intervencije policije", emergencyList, null, null, city3);
        Number number3 = new Number("Vatrogasci", "193", "Vatrogasna služba", emergencyList, null, "Omladinskih brigada 1, Beograd", city3);
        Number number4 = new Number("Gorska služba", "+38163466466", "Gorska služba spašavanja", emergencyList, "http://www.gss.rs/", null, city3);


        NumberList mobileOperatorsList = new NumberList("Mobilna telefonija");
        numberListDao.create(mobileOperatorsList);
        Number number5 = new Number("Vip mobile", "+381601234", null, mobileOperatorsList, "ww.vipmobile.rs", "Omladinskih brigada 21, Beograd", city3);
        Number number6 = new Number("Telenor", "+3816390000", null, mobileOperatorsList, "www.telenor.rs", "Omladinskih brigada 90, Beograd", city3);
        Number number7 = new Number("MTS", "+38164789", null, mobileOperatorsList, "https://www.mts.rs/", "Branislava Nusića", city3);


        NumberList healthCare = new NumberList("Zdravstvo");
        numberListDao.create(healthCare);
        Number number8 = new Number("Dom zdravlja Liman", "+381214807187", null, healthCare, null, null, city2);
        Number number9 = new Number("Dom zdravlja bistrica", "+38121401122", null, healthCare, null, null, city2);
        Number number10 = new Number("Kliničko bolnički centar", "+381214843484", null, healthCare, null, null, city2);
        Number number11 = new Number("Dečja bolnija", "+38121422699", "Bolnica za decu", healthCare, null, null, city2);
        Number number12 = new Number("Dispanzer", "+38121422699", null, healthCare, null, null, city2);
        Number number13 = new Number("Klinika za akušerstvo i ginekologiju", "+381214899222", null, healthCare, null, null, city2);
        Number number14 = new Number("Higijenski Zavod", "+38121422255", "Bolnica za decu", healthCare, null, null, city2);
        Number number15 = new Number("Noćna ambulanta Zemun", "+381112600192", "Svakog dana 19:00-07:00", healthCare, null, "Karađorđev trg 4", city1);
        Number number16 = new Number("Dežurna apoteka  Prvi Maj", "+381112643170", "svakog dana 00:00-24:00", healthCare, null, "Kralja Milana 9", city1);
        Number number17 = new Number("KBC Zvezdara", "+381113806969", null, healthCare, "www.kbczvezdara.com", "Dimitrija Tucovića 161", city1);

        NumberList transport = new NumberList("Saobraćaj");
        numberListDao.create(transport);
        Number number18 = new Number("Automoto Savez Srbije", "987", "Pomoć na putu, stanje na putevima, međunarodna dokumenta, saobraćajna...", transport, "www.amss.org.rs", "Ruzveltova 18, Zvezdara", city3);
        Number number19 = new Number("Aerodorom Nikola Tesla", "+381112094444", null, transport, "www.beg.aero", "Beograd", city3);
        Number number20 = new Number("Aerodrom Konstantin Veliki", "+38118585858", null, transport, "www.nis-airport.com", "Vazduhoplovaca 24", city3);
        Number number21 = new Number("Autobuska stanica", "+38118255177", null, transport, null, null, city4);
        Number number22 = new Number("Železnička stanica", "+38118364625", null, transport, null, null, city4);
        Number number23 = new Number("Autobuska stanica", "0901111021", "Međumesna autobuska stanica Novog Sada", transport, "www.gspns.co.rs", null, city2);
        Number number24 = new Number("Železnička stanica", "+38121443200", "Želeynice Srbije, stanica Novi Sad", transport, "www.zeleznicesrbije.com", null, city2);
        Number number25 = new Number("Autobuska stanica", "+381112636299", "Beogradska autobuska stanica", transport, "www.bas.rs", "Železnička 4", city1);
        Number number26 = new Number("Železnička stanica", "+38118364625", "Železnice Srbije, stanica Beograd", transport, "www.zeleznicesrbije.com", "Savski trg 2", city1);

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
        numberDao.create(number12);
        numberDao.create(number13);
        numberDao.create(number14);
        numberDao.create(number15);
        numberDao.create(number16);
        numberDao.create(number17);
        numberDao.create(number18);
        numberDao.create(number19);
        numberDao.create(number20);
        numberDao.create(number21);
        numberDao.create(number22);
        numberDao.create(number23);
        numberDao.create(number24);
        numberDao.create(number25);
        numberDao.create(number26);

        /*Survival Text Database*/
        //TODO 1. dodati validne podatke i slike
        survivalTextDao = new SurvivalTextDao(this);

        SurvivalText s1 = new SurvivalText("Uvod", "Text Uvod", "Opis Uvod", R.drawable.compass);
        SurvivalText s2 = new SurvivalText("Psihologija preživljavanja", "Text Psihologija preživljavanja", "Opis Psihologija preživljavanja", R.drawable.flashlight);
        SurvivalText s3 = new SurvivalText("Planiranje i oprema", "Text Planiranje i oprema", "Opis Planiranje i oprema", R.drawable.button_shape_important_numbers);
        SurvivalText s4 = new SurvivalText("Skrovište", "Text Skrovište", "Opis Skrovište", R.drawable.police);

        survivalTextDao.create(s1);
        survivalTextDao.create(s2);
        survivalTextDao.create(s3);
        survivalTextDao.create(s4);

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
