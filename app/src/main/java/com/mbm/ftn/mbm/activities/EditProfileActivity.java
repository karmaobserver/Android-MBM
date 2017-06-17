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

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.adapters.ProfileAdapter;
import com.mbm.ftn.mbm.dao.NumberDao;
import com.mbm.ftn.mbm.dao.ProfileDao;
import com.mbm.ftn.mbm.models.Profile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makso on 6/17/2017.
 */

public class EditProfileActivity extends BaseActivity {

    private static final String TAG = "EditProfileActivity";

    ProfileDao profileDao = null;

    RecyclerView recyclerView;
    ProfileAdapter profileAdapter;
    private List<Profile> profileList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.mipmap.ic_sos);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        Intent intent = getIntent();
        Integer id = intent.getIntExtra("id", 1);

        profileDao = new ProfileDao(this);
        Profile profile = new Profile();
        try {
            profile = profileDao.findByIdProfile(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String title = profile.getTitle();
        String firstName = profile.getFirstName();
        String lastName = profile.getLastName();
        String phone = profile.getPhone();
        String email = profile.getEmail();
        String message = profile.getMessage();

        TextView inputTitle = (TextView) findViewById(R.id.input_title);
        TextView inputFirstName = (TextView) findViewById(R.id.input_first_name);
        TextView inputLastName = (TextView) findViewById(R.id.input_last_name);
        TextView inputPhone = (TextView) findViewById(R.id.input_phone);
        TextView inputEmail = (TextView) findViewById(R.id.input_email);
        TextView inputMessage = (TextView) findViewById(R.id.input_message);

        inputTitle.setText(title);
        inputFirstName.setText(firstName);
        inputLastName.setText(lastName);
        inputPhone.setText(phone);
        inputEmail.setText(email);
        inputMessage.setText(message);

        Button editButton = (Button) findViewById(R.id.button_edit_profile);
        editButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editProfile();
            }
        });

    }

    private void editProfile() {
        Log.d(TAG, "Edit");

        if (!validate()) {
            onEditFailed();
            return;
        }


        TextView inputTitle = (TextView) findViewById(R.id.input_title);
        TextView inputFirstName = (TextView) findViewById(R.id.input_first_name);
        TextView inputLastName = (TextView) findViewById(R.id.input_last_name);
        TextView inputPhone = (TextView) findViewById(R.id.input_phone);
        TextView inputEmail = (TextView) findViewById(R.id.input_email);
        TextView inputMessage = (TextView) findViewById(R.id.input_message);


        Intent intent = getIntent();
        Integer id = intent.getIntExtra("id", 1);

        String title = inputTitle.getText().toString();
        String firstName = inputFirstName.getText().toString();
        String lastName = inputLastName.getText().toString();
        String phone = inputPhone.getText().toString();
        String email = inputEmail.getText().toString();
        String message = inputMessage.getText().toString();

        profileDao = new ProfileDao(this);
        try {
            profileDao.updateProfileWithoutChecked(id, title, firstName, lastName, phone, email, message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onEditSucceed();

    }

    public void onEditSucceed() {
        Button buttonEdit = (Button) findViewById(R.id.button_edit_profile);
        buttonEdit.setEnabled(true);

        //finish activity profile
        Intent intent2 = new Intent("finish_activity");
        sendBroadcast(intent2);

        Intent intent = new Intent(this, ProfilesActivity.class);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Edit profile succeed", Toast.LENGTH_LONG).show();
        finish();

    }

    private void onEditFailed() {
        Toast.makeText(getBaseContext(), "Edit profile failed", Toast.LENGTH_LONG).show();
        Button buttonEdit = (Button) findViewById(R.id.button_edit_profile);
        buttonEdit.setEnabled(true);
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
            inputTitle.setError("from 1 to at most 30 characters");
            valid = false;
        } else if (titleExist){
            Log.d("USAO", "ad");
            inputTitle.setError("Title already exists!");
            valid = false;
        } else {
            Log.d("NIJE", "ad");
            inputTitle.setError(null);
        }

        if (firstName.isEmpty() || firstName.length() >30) {
            inputFirstName.setError("from 1 to at most 30 characters");
            valid = false;
        } else {
            inputFirstName.setError(null);
        }

        if (lastName.isEmpty() || lastName.length() >30) {
            inputLastName.setError("from 1 to at most 30 characters");
            valid = false;
        } else {
            inputLastName.setError(null);
        }

        if (phone.isEmpty() || phone.length() >30) {
            inputPhone.setError("from 1 to at most 30 characters");
            valid = false;
        } else {
            inputPhone.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("enter a valid email address");
            valid = false;
        } else {
            inputEmail.setError(null);
        }

        if (message.isEmpty() || message.length() >200) {
            inputMessage.setError("from 1 to at most 30 characters");
            valid = false;
        } else {
            inputMessage.setError(null);
        }

        return valid;
    }
}
