package com.mbm.ftn.mbm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mbm.ftn.mbm.R;

/**
 * Created by Boris K on 30-May-17.
 */

public class TextActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_item_survival_text);

        Intent intent = getIntent();
        String text = intent.getStringExtra("text");

        TextView tv = (TextView)findViewById(R.id.view_text);
        tv.setText(text);

    }

}
