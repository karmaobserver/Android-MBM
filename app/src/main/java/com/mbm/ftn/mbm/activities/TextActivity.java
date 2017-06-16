package com.mbm.ftn.mbm.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mbm.ftn.mbm.R;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Boris K on 30-May-17.
 */

public class TextActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_survival_text_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String text = intent.getStringExtra("text");
        String topic = intent.getStringExtra("topic");
     //   int image = intent.getIntExtra("image", -1);

        TextView tv1 = (TextView)findViewById(R.id.view_topic);
        TextView tv2 = (TextView)findViewById(R.id.view_text);
    //    ImageView iv = (ImageView) findViewById(R.id.imageView);

        tv1.setText(topic);
        tv2.setText(text);
   //     iv.setImageResource(image);


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape TextActivity", LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait TextActivity", LENGTH_SHORT).show();
        }
    }

}
