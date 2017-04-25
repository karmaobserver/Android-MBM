package com.mbm.ftn.mbm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.mbm.ftn.mbm.activities.CompassActivity;
import com.mbm.ftn.mbm.activities.Flashlight2Activity;
import com.mbm.ftn.mbm.activities.ImportantNumbersActivity;
import com.mbm.ftn.mbm.activities.SurvivalTextActivity;
import com.mbm.ftn.mbm.activities.WeatherActivity;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        importantNumbers.setOnClickListener(new View.OnClickListener() {

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
