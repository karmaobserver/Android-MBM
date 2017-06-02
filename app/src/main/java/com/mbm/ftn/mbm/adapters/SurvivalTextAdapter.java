package com.mbm.ftn.mbm.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.activities.TextActivity;
import com.mbm.ftn.mbm.models.SurvivalText;

import java.util.List;

/**
 * Created by Boris K on 22-May-17.
 */

public class SurvivalTextAdapter extends RecyclerView.Adapter<SurvivalTextAdapter.MyViewHolder> {

    private List<SurvivalText> textList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView topic, text;

        public MyViewHolder(View view) {
            super(view);
            topic = (TextView) view.findViewById(R.id.topic);
        //    text = (TextView) view.findViewById(R.id.text);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            text = (TextView) v.findViewById(R.id.topic);
        //    Toast.makeText(v.getContext(),  "Text is selected! " + text.getText().toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(v.getContext(), TextActivity.class);
            intent.putExtra("text", (CharSequence) text.getText().toString());
            v.getContext().startActivity(intent);


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
        SurvivalText movie = textList.get(position);
        holder.topic.setText(movie.getTopic());
    //    holder.text.setText(movie.getText());
    }

    @Override
    public int getItemCount() {
        return textList.size();
    }
}
