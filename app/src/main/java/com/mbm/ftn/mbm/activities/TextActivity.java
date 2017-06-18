package com.mbm.ftn.mbm.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.mbm.ftn.mbm.R;

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

        tv2.setMovementMethod(new ScrollingMovementMethod());
   //     iv.setImageResource(image);


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

        }
    }

}
