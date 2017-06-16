package com.mbm.ftn.mbm.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.activities.MainActivity;
import com.mbm.ftn.mbm.activities.SurvivalTextActivity;
import com.mbm.ftn.mbm.activities.TextActivity;
import com.mbm.ftn.mbm.fragments.SurvivalTextListFragment;
import com.mbm.ftn.mbm.fragments.SurvivalTextViewFragment;
import com.mbm.ftn.mbm.models.SurvivalText;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Boris K on 22-May-17.
 */

public class SurvivalTextAdapter extends RecyclerView.Adapter<SurvivalTextAdapter.MyViewHolder> {

    private List<SurvivalText> textList;
    private Context context;
    private SurvivalTextViewFragment stvf;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView topic, text, description;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            topic = (TextView) view.findViewById(R.id.topic);
            description = (TextView) view.findViewById(R.id.description);
            text = (TextView) view.findViewById(R.id.survival_text);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            text = (TextView) v.findViewById(R.id.survival_text);

            if (v.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                Bundle b = new Bundle();
                b.putString("text", text.getText().toString());
                b.putString("topic", topic.getText().toString());
                stvf = new SurvivalTextViewFragment();
                stvf.setArguments(b);
                //TODO 2. send bundle to fragment

            } else if (v.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                Intent intent = new Intent(v.getContext(), TextActivity.class);
                intent.putExtra("text", (CharSequence) text.getText().toString());
                intent.putExtra("topic", (CharSequence) topic.getText().toString());
                v.getContext().startActivity(intent);
            }

        }
    }

    public SurvivalTextAdapter(List<SurvivalText> textList) {
        this.textList = textList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.survival_text_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SurvivalText text = textList.get(position);
        holder.topic.setText(text.getTopic());
        holder.description.setText(text.getDescription());
        holder.text.setText(text.getText());
        holder.imageView.setImageResource(text.getImage());
    }

    @Override
    public int getItemCount() {
        return textList.size();
    }
}
