package com.mbm.ftn.mbm.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.j256.ormlite.table.TableUtils;
import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.dao.NumberDao;
import com.mbm.ftn.mbm.dao.NumberListDao;
import com.mbm.ftn.mbm.dialogs.ChooseCityDialog;
import com.mbm.ftn.mbm.dialogs.NumberPickedDialog;
import com.mbm.ftn.mbm.fragments.NumberFragment;
import com.mbm.ftn.mbm.models.Number;
import com.mbm.ftn.mbm.models.NumberList;

import static com.mbm.ftn.mbm.R.drawable.button;


/**
 * Created by Makso on 4/25/2017.
 */

public class ImportantNumbersActivity extends BaseActivity {

    private static final String TAG = "ImportantNumbersActivity";
    NumberDao numberDao = null;
    NumberListDao numberListDao = null;
    Number number = null;
    NumberList numberList = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_numbers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.mipmap.ic_important_numbers);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Button allNumbersButton = (Button) findViewById(R.id.button_all_numbers);
        allNumbersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), NumbersActivity.class);
                intent.putExtra("city", "none");
                startActivity(intent);
            }
        });

        Button countryButton = (Button) findViewById(R.id.button_country);
        countryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), NumbersActivity.class);
                intent.putExtra("city", "Srbija");
                startActivity(intent);
            }
        });

        Button cityButton = (Button) findViewById(R.id.button_city);
        cityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("titleName", getResources().getString(R.string.dialog_title_choose_city));
                ChooseCityDialog chooseCityDialog = new ChooseCityDialog();
                chooseCityDialog.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = (ImportantNumbersActivity.this).getSupportFragmentManager();
                chooseCityDialog.show(fragmentManager, "showChooseCityDialogTAG");
            }
        });

        Button emergnecyButton = (Button) findViewById(R.id.button_emergency);
        emergnecyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView number = (TextView) findViewById(R.id.number);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + getResources().getString(R.string.number_emergency)));
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        Button policeButton = (Button) findViewById(R.id.button_police);
        policeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView number = (TextView) findViewById(R.id.number);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + getResources().getString(R.string.number_police)));
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        Button firefightersButton = (Button) findViewById(R.id.button_firefighers);
        firefightersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView number = (TextView) findViewById(R.id.number);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + getResources().getString(R.string.number_firefighters)));
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        Button mountainButton = (Button) findViewById(R.id.button_mountain_rescue_service);
        mountainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView number = (TextView) findViewById(R.id.number);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + getResources().getString(R.string.number_mountain_service)));
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //Dodavanje ikone pored tekst-a u button. Jedini nacin da radi i za stariji API od lolipopa.
        int widthIcon = 0;
        int heightIcon = 0;

        Drawable emergIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.emerg);
        widthIcon = emergIcon.getIntrinsicWidth();
        heightIcon = emergIcon.getIntrinsicHeight();
        emergIcon.setBounds(0, 0, widthIcon/ 7, heightIcon/ 7);
        Button emergencyButton = (Button) findViewById(R.id.button_emergency);
        emergencyButton.setCompoundDrawables(emergIcon, null, null, null);

        Drawable policeIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.police);
        widthIcon = policeIcon.getIntrinsicWidth();
        heightIcon = policeIcon.getIntrinsicHeight();
        policeIcon.setBounds(0, 0, widthIcon/ 6, heightIcon/ 6);
        Button policeButton = (Button) findViewById(R.id.button_police);
        policeButton.setCompoundDrawables(policeIcon, null, null, null);

        Drawable firefightersIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.firefighters);
        widthIcon = firefightersIcon.getIntrinsicWidth();
        heightIcon = firefightersIcon.getIntrinsicHeight();
        firefightersIcon.setBounds(0, 0, widthIcon/ 3, heightIcon/ 3);
        Button firefightersButton = (Button) findViewById(R.id.button_firefighers);
        firefightersButton.setCompoundDrawables(firefightersIcon, null, null, null);

        Drawable mountainRescureServiceIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.mountain_rescue_services);
        widthIcon = mountainRescureServiceIcon.getIntrinsicWidth();
        heightIcon = mountainRescureServiceIcon.getIntrinsicHeight();
        mountainRescureServiceIcon.setBounds(0, 0, 80, 80);
        Button mountainRescueServiceButton = (Button) findViewById(R.id.button_mountain_rescue_service);
        mountainRescueServiceButton.setCompoundDrawables(mountainRescureServiceIcon, null, null, null);


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
