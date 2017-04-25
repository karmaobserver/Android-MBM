package com.mbm.ftn.mbm.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.models.SurvivalText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris K on 25-Apr-17.
 */

public class SurvivalTextAdapter extends BaseAdapter {

    private List<SurvivalText> survivalTexts = new ArrayList<>();
    private final LayoutInflater layoutInflater;
    private final Context context;

    public SurvivalTextAdapter(List<SurvivalText> survivalTexts, Context context) {
        this.survivalTexts = survivalTexts;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return survivalTexts.size();
    }

    @Override
    public Object getItem(int position) {
        return survivalTexts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {
            // create a new view holder since we don't have a view to reuse
            viewHolder = new ViewHolder();

            // inflate view item
            convertView = layoutInflater.inflate(R.layout.view_item_survival, parent, false);

            // store the views in a view holder for later reuse

            viewHolder.topic = (TextView) convertView.findViewById(R.id.survival_topic);
            viewHolder.text = (TextView) convertView.findViewById(R.id.survival_text);

            // save view holder for later reuse
            convertView.setTag(viewHolder);
        } else {
            // we already have a view holder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // get item
        final SurvivalText article = (SurvivalText) getItem(position);

        // populate view item with task's data
        viewHolder.text.setText(article.getText());
        viewHolder.topic.setText(article.getTopic());

        return convertView;
    }


    public List<SurvivalText> getSurvivalTexts() {
        return survivalTexts;
    }

    public void setSurvivalTexts(List<SurvivalText> survivalTexts) {
        this.survivalTexts = survivalTexts;
    }

    private static class ViewHolder{
        private TextView topic;
        private TextView text;

    }
}
