package com.mbm.ftn.mbm.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.models.Number;
import com.mbm.ftn.mbm.views.adapters.NumberAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makso on 4/25/2017.
 */

public class ImportantNumbersActivity extends AppCompatActivity {

    private ListView listViewNumbersEmergency;

    private ListView listViewNumbersMobileOperators;

    private NumberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_numbers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //emergency numbers
        listViewNumbersEmergency = (ListView) findViewById(R.id.listViewNumbersEmergency);
        final List<Number> numbersListEmergency = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            numbersListEmergency.add(new Number("Number " + i, "+381000" + i));
        }

        adapter =  new NumberAdapter(this, numbersListEmergency);
        listViewNumbersEmergency.setAdapter(adapter);

        //mobile operators numbers
        listViewNumbersMobileOperators = (ListView) findViewById(R.id.listViewNumbersMobileOperators);
        final List<Number> numbersListOperators = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            numbersListOperators.add(new Number("Number " + i, "+381090" + i));
        }

        adapter =  new NumberAdapter(this, numbersListOperators);
        listViewNumbersMobileOperators.setAdapter(adapter);

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
