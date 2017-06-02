package com.mbm.ftn.mbm.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.models.SurvivalText;
import com.mbm.ftn.mbm.adapters.SurvivalTextAdapter;

import java.util.List;

/**
 * Created by Boris K on 25-Apr-17.
 */

public class SurvivalTextActivity extends AppCompatActivity {

    private List<SurvivalText> textList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SurvivalTextAdapter sAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survival_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        sAdapter = new SurvivalTextAdapter(textList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(sAdapter);

        prepareTextData();

    }

    private void prepareTextData() {

        String[] topics = getResources().getStringArray(R.array.topics_entries);
        String[] texts = getResources().getStringArray(R.array.text_entries);
        for(int i = 0; i < texts.length; i++) {
            textList.add(new SurvivalText(topics[i], texts[i]));
        }

        sAdapter.notifyDataSetChanged();
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
