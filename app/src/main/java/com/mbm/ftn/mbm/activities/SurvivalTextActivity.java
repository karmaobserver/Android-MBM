package com.mbm.ftn.mbm.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.adapters.SurvivalTextAdapter;
import com.mbm.ftn.mbm.dao.SurvivalTextDao;
import com.mbm.ftn.mbm.fragments.SurvivalTextListFragment;
import com.mbm.ftn.mbm.fragments.SurvivalTextViewFragment;
import com.mbm.ftn.mbm.models.SurvivalText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris K on 25-Apr-17.
 */

public class SurvivalTextActivity extends BaseActivity {

    private List<SurvivalText> textList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SurvivalTextAdapter sAdapter;
    private SurvivalTextDao survivalTextDao = null;
    private static final String TAG = "SurvivalTextActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

       if (savedInstanceState == null) {
           initGui(getResources().getConfiguration());
        }

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initGui(newConfig);

    }
    private void initGui(Configuration newConfig) {

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_survival_text_landscape);
            if (findViewById(R.id.activity_survival_text_landscape) != null){

                SurvivalTextListFragment stlf = new SurvivalTextListFragment();
                stlf.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(R.id.survival_text_fragment, stlf).commit();

                SurvivalTextViewFragment stvf = new SurvivalTextViewFragment();
                stvf.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(R.id.survival_text_view_fragment, stvf).commit();
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_survival_text_portrait);
            if (findViewById(R.id.activity_survival_text_portrait) != null){

                SurvivalTextListFragment stlf = new SurvivalTextListFragment();
                stlf.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(R.id.survival_text_fragment, stlf).commit();
            }

        }
    }
      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.text_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.mipmap.ic_important_numbers);
        getSupportActionBar().setTitle("Text Prezivljavanja");
        getSupportActionBar().setDisplayUseLogoEnabled(true);*/

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
