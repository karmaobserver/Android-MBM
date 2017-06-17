package com.mbm.ftn.mbm.dialogs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.activities.NumbersActivity;

/**
 * Created by Makso on 5/23/2017.
 */

public class NumberPickedDialog extends DialogFragment {

    private static final String ARG_PARAM1 = "titleName";
    private static final String ARG_PARAM2 = "number";
    private static final String ARG_PARAM3 = "website";
    private static final String ARG_PARAM4 = "address";

    private String param1;
    private String param2;
    private String param3;
    private String param4;

    private TextView numberTextView;
    private TextView websiteTextView;
    private TextView addressTextView;
    private LinearLayout websiteLinearLayout;
    private LinearLayout addressLinearLayout;

    public NumberPickedDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static NumberPickedDialog newInstance(String param1, String param2, String param3, String param4) {

        NumberPickedDialog fragment = new NumberPickedDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            param1 = getArguments().getString(ARG_PARAM1);
            param2 = getArguments().getString(ARG_PARAM2);
            param2 = getArguments().getString(ARG_PARAM3);
            param2 = getArguments().getString(ARG_PARAM4);
        }
        return inflater.inflate(R.layout.dialog_number_picked, container);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case 200: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + numberTextView.getText()));
                    startActivity(callIntent);
                }
            }
        }
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        numberTextView = (TextView) view.findViewById(R.id.number);
        websiteTextView = (TextView) view.findViewById(R.id.website);
        addressTextView = (TextView) view.findViewById(R.id.address);
        websiteLinearLayout = (LinearLayout) view.findViewById(R.id.linear_website);
        addressLinearLayout = (LinearLayout) view.findViewById(R.id.linear_address);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("titleName");
        String number = getArguments().getString("number");
        String website = getArguments().getString("website");
        String address = getArguments().getString("address");

        numberTextView.setText(number);
        websiteTextView.setText(website);
        addressTextView.setText(address);
        getDialog().setTitle(title);

        if(websiteTextView.getText() == null || websiteTextView.getText().toString().isEmpty())
        {
            websiteLinearLayout.setVisibility(View.GONE);
        }
        if(addressTextView.getText() == null || addressTextView.getText().toString().isEmpty())
        {
            addressLinearLayout.setVisibility(View.GONE);
        }

        ImageButton phoneImageButton = (ImageButton) view.findViewById(R.id.image_button_phone);
        phoneImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + numberTextView.getText()));

                //test
                boolean permissionGranted = ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
                if (permissionGranted) {
                    startActivity(callIntent);
                } else {
                    ActivityCompat.requestPermissions((NumbersActivity)getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 200);

                }

                if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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



        ImageButton websiteImageButton = (ImageButton) view.findViewById(R.id.image_button_website);
        websiteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = websiteTextView.getText().toString();
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW);
                websiteIntent.setData(Uri.parse(url));
                startActivity(websiteIntent);
            }
        });

        ImageButton addressImageButton = (ImageButton) view.findViewById(R.id.image_button_address);
        addressImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uriBegin = "geo:0,0?q=" + addressTextView.getText().toString();
                Uri uri = Uri.parse(uriBegin);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(intent);

                //if i need coordinates
               /* double latitude = 40.714728;
                double longitude = -73.998672;
                String label = "ABC Label";*/
                // String uriBegin = "geo:" + latitude + "," + longitude;
                /*String query = latitude + "," + longitude + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(intent);*/
            }
        });

        Button button = (Button) view.findViewById(R.id.button_close);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

    }
}

