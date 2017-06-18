package com.mbm.ftn.mbm.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.activities.ImportantNumbersActivity;
import com.mbm.ftn.mbm.activities.NumbersActivity;
import com.mbm.ftn.mbm.dialogs.ChooseCityDialog;
import com.mbm.ftn.mbm.dialogs.NumberPickedDialog;
import com.mbm.ftn.mbm.models.City;
import com.mbm.ftn.mbm.models.Number;

import java.util.List;

/**
 * Created by Makso on 6/15/2017.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private static ViewHolder.ClickListener clickListener;
    private List<City> cityList;

    /////////////////////////////////////Beginning of inner class///////////////////////////////////////////
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;

        public ViewHolder(View view, ClickListener listener) {
            super(view);
            clickListener = listener;
            name = (TextView) view.findViewById(R.id.name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

                clickListener.onItemClick(getAdapterPosition(), v);
        }

        public interface ClickListener {
            void onItemClick(int position, View v);
        }
    }
    /////////////////////////////////////End of inner class///////////////////////////////////////////

    public void setOnItemClickListener(ViewHolder.ClickListener clickListener) {
        CityAdapter.clickListener = clickListener;
    }

    public CityAdapter(List<City> cityList) {
        this.cityList = cityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_city, parent, false);

        final CityAdapter.ViewHolder viewHolder = new ViewHolder(view, new CityAdapter.ViewHolder.ClickListener() {


            @Override
            public void onItemClick(int position, View v) {

                TextView name = (TextView) v.findViewById(R.id.name);

                Intent intent = new Intent(v.getContext(), NumbersActivity.class);
                intent.putExtra("city", name.getText().toString());
                v.getContext().startActivity(intent);

                //android.support.v4.app.FragmentManager fragmentManager =  ((ImportantNumbersActivity)parent.getContext()).getSupportFragmentManager();
                /*android.support.v4.app.FragmentManager fragmentManager = ((ImportantNumbersActivity) v.getContext()).getSupportFragmentManager();
                Fragment prev = fragmentManager.findFragmentByTag("showChooseCityDialogTAG");
                if (prev != null) {
                    DialogFragment df = (DialogFragment) prev;
                    df.dismiss();
                }*/

            }

        });

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        City city = cityList.get(position);
        holder.name.setText(city.getName());

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

}
