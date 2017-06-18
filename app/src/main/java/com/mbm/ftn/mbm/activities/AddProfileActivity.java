package com.mbm.ftn.mbm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.adapters.ProfileAdapter;
import com.mbm.ftn.mbm.dao.ProfileDao;
import com.mbm.ftn.mbm.models.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makso on 6/17/2017.
 */

public class AddProfileActivity extends BaseActivity {

    private static final String TAG = "AddProfileActivity";

    ProfileDao profileDao = null;

    RecyclerView recyclerView;
    ProfileAdapter profileAdapter;
    private List<Profile> profileList = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference firebase = database.getReference("firebase");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.mipmap.ic_sos);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        profileDao = new ProfileDao(this);
        profileList = profileDao.findAll();



        Button createButton = (Button) findViewById(R.id.button_create_profile);
        createButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               createProfile();
            }
        });

    }

    private void createProfile() {
        Log.d(TAG, "Add");

        if (!validate()) {
            onCreateFailed();
            return;
        }


        TextView inputTitle = (TextView) findViewById(R.id.input_title);
        TextView inputFirstName = (TextView) findViewById(R.id.input_first_name);
        TextView inputLastName = (TextView) findViewById(R.id.input_last_name);
        TextView inputPhone = (TextView) findViewById(R.id.input_phone);
        TextView inputEmail = (TextView) findViewById(R.id.input_email);
        TextView inputMessage = (TextView) findViewById(R.id.input_message);

        Profile profile = new Profile();
        profile.setTitle(inputTitle.getText().toString());
        profile.setFirstName(inputFirstName.getText().toString());
        profile.setLastName(inputLastName.getText().toString());
        profile.setPhone(inputPhone.getText().toString());
        profile.setEmail(inputEmail.getText().toString());
        profile.setMessage(inputMessage.getText().toString());
        profile.setChecked(false);
        profileDao = new ProfileDao(this);
        profileDao.create(profile);

        DatabaseReference profilesDb = firebase.child("profiles");
        profilesDb.push().setValue(profile);

        onCreateSucceed();

    }

    public void onCreateSucceed() {
        Button createButton = (Button) findViewById(R.id.button_create_profile);
        createButton.setEnabled(true);

        //finish activity profile
        Intent intent2 = new Intent("finish_activity");
        sendBroadcast(intent2);

        Intent intent = new Intent(this, ProfilesActivity.class);
        startActivity(intent);
        Toast.makeText(getBaseContext(), getResources().getString(R.string.create_succeed), Toast.LENGTH_LONG).show();
        finish();

    }

    private void onCreateFailed() {
        Toast.makeText(getBaseContext(), getResources().getString(R.string.create_failed), Toast.LENGTH_LONG).show();
        Button createButton = (Button) findViewById(R.id.button_create_profile);
        createButton.setEnabled(true);
    }

    private boolean validate() {

        TextView inputTitle = (TextView) findViewById(R.id.input_title);
        TextView inputFirstName = (TextView) findViewById(R.id.input_first_name);
        TextView inputLastName = (TextView) findViewById(R.id.input_last_name);
        TextView inputPhone = (TextView) findViewById(R.id.input_phone);
        TextView inputEmail = (TextView) findViewById(R.id.input_email);
        TextView inputMessage = (TextView) findViewById(R.id.input_message);

        boolean valid = true;
        String title = inputTitle.getText().toString();
        String firstName = inputFirstName.getText().toString();
        String lastName = inputLastName.getText().toString();
        String phone = inputPhone.getText().toString();
        String email = inputEmail.getText().toString();
        String message = inputMessage.getText().toString();

        profileDao = new ProfileDao(this);
        boolean titleExist = profileDao.checkIfTitleExist(title);
        if (title.isEmpty() || title.length() > 30) {
            inputTitle.setError(getResources().getString(R.string.char1to30));
            valid = false;
        } else if (titleExist){
            inputTitle.setError("Naslov već postoji");
            valid = false;
        } else {
            inputTitle.setError(null);
        }

        if (firstName.isEmpty() || firstName.length() >30) {
            inputFirstName.setError(getResources().getString(R.string.char1to30));
            valid = false;
        } else {
            inputFirstName.setError(null);
        }

        if (lastName.isEmpty() || lastName.length() >30) {
            inputLastName.setError(getResources().getString(R.string.char1to30));
            valid = false;
        } else {
            inputLastName.setError(null);
        }

        if (phone.isEmpty() || phone.length() >30) {
            inputPhone.setError(getResources().getString(R.string.char1to30));
            valid = false;
        } else {
            inputPhone.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError(getResources().getString(R.string.valid_email));
            valid = false;
        } else {
            inputEmail.setError(null);
        }

        if (message.isEmpty() || message.length() >200) {
            inputMessage.setError(getResources().getString(R.string.char1to30));
            valid = false;
        } else {
            inputMessage.setError(null);
        }

        return valid;
    }


}