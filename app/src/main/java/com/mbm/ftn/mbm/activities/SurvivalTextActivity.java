package com.mbm.ftn.mbm.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.dao.SurvivalTextDao;
import com.mbm.ftn.mbm.models.SurvivalText;
import com.mbm.ftn.mbm.adapters.SurvivalTextAdapter;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Boris K on 25-Apr-17.
 */

public class SurvivalTextActivity extends FragmentActivity {

    private List<SurvivalText> textList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SurvivalTextAdapter sAdapter;
    private SurvivalTextDao survivalTextDao = null;
    private static final String TAG = "SurvivalTextActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

       if (savedInstanceState == null) {
            initGui();

        }

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void initGui() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            Toast.makeText(this, "landscape SurvivalTextActivity", LENGTH_SHORT).show();
            setContentView(R.layout.activity_survival_text_landscape);

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            Toast.makeText(this, "portrait SurvivalTextActivity", LENGTH_SHORT).show();
            setContentView(R.layout.activity_survival_text_portrait);
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
