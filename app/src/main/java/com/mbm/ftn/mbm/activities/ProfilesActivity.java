package com.mbm.ftn.mbm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.adapters.NumberAdapter;
import com.mbm.ftn.mbm.adapters.ProfileAdapter;
import com.mbm.ftn.mbm.dao.NumberDao;
import com.mbm.ftn.mbm.dao.ProfileDao;
import com.mbm.ftn.mbm.models.Number;
import com.mbm.ftn.mbm.models.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makso on 6/17/2017.
 */

public class ProfilesActivity extends BaseActivity  {

    ProfileDao profileDao = null;

    RecyclerView recyclerView;
    ProfileAdapter profileAdapter;
    private List<Profile> profileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.mipmap.ic_sos);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        profileDao = new ProfileDao(this);
        profileList = profileDao.findAll();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_profile);
        profileAdapter = new ProfileAdapter(profileList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(profileAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_profiles, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(this, AddProfileActivity.class);
                startActivity(intent);
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
}