package com.mbm.ftn.mbm.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import java.util.ArrayList;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.models.SurvivalText;
import com.mbm.ftn.mbm.views.adapters.SurvivalTextAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Boris K on 25-Apr-17.
 */

public class SurvivalTextActivity extends AppCompatActivity {

    private SurvivalTextAdapter adapter;
    private ListView listViewSurvivalTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survival_text);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

    }

    private void init() {
        listViewSurvivalTexts = (ListView) findViewById(R.id.listViewSurvivalTexts);
        final List<SurvivalText> lista = new ArrayList<>();

        String[] topics = getResources().getStringArray(R.array.topics_entries);
        String[] texts = getResources().getStringArray(R.array.text_entries);
        for(int i = 0; i < texts.length; i++) {
            lista.add(new SurvivalText(topics[i], texts[i]));
        }

        adapter = new SurvivalTextAdapter(lista, this);
        listViewSurvivalTexts.setAdapter(adapter);


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
