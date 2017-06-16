package com.mbm.ftn.mbm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mbm.ftn.mbm.R;

/**
 * Created by Milos on 5/24/2017.
 */

public class SearchCitiesActivity extends AppCompatActivity {

    private EditText editText;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_weather);
        setupActionBar();
        editText = (EditText) findViewById(R.id.city_name_text);
        Button button = (Button) findViewById(R.id.submit_city);

    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onClickBtn(View v)
    {
        // Toast.makeText(this, editText.getText(), Toast.LENGTH_LONG).show();
        // editText.getText();
        Intent intent = new Intent(getBaseContext(), WeatherActivity.class);
        //intent.putExtra("EXTRA_SESSION_ID", sessionId)
        intent.putExtra("search",editText.getText().toString());
        startActivity(intent);

    }
}
