package com.mbm.ftn.mbm.dialogs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.activities.NumbersActivity;

/**
 * Created by Makso on 6/18/2017.
 */

public class SosDialog extends DialogFragment {

    private static final String ARG_PARAM1 = "titleName";

    private String param1;

    private TextView smsTextView;
    private TextView emailTextView;
    private LinearLayout smsLinearLayout;
    private LinearLayout emailLinearLayout;

    public SosDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static NumberPickedDialog newInstance(String param1, String param2) {

        NumberPickedDialog fragment = new NumberPickedDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            param1 = getArguments().getString(ARG_PARAM1);
        }
        return inflater.inflate(R.layout.dialog_sos, container);
    }

    /*@Override
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
    }*/

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        smsTextView = (TextView) view.findViewById(R.id.sms);
        emailTextView = (TextView) view.findViewById(R.id.email);
        smsLinearLayout = (LinearLayout) view.findViewById(R.id.linear_sms);
        emailLinearLayout = (LinearLayout) view.findViewById(R.id.linear_email);
        // Fetch arguments from bundle and set title
        String titleName = getArguments().getString("titleName");


        getDialog().setTitle(titleName);

        if(smsTextView.getText() == null || smsTextView.getText().toString().isEmpty())
        {
            smsLinearLayout.setVisibility(View.GONE);
        }
        if(emailTextView.getText() == null || emailTextView.getText().toString().isEmpty())
        {
            emailLinearLayout.setVisibility(View.GONE);
        }

        ImageButton smsImageButton = (ImageButton) view.findViewById(R.id.image_button_sms);
        smsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "0643748805"));
                intent.putExtra("sms_body", "Test");
                startActivity(intent);

                dismiss();

                /*profileList = profileDao.findAllCheckedProfiles();
                for (Profile profile : profileList) {

                }*/
            }
        });

        ImageButton emailImageButton = (ImageButton) view.findViewById(R.id.image_button_email);
        emailImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","makso.the.one@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "SOS");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "TEST");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));



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
