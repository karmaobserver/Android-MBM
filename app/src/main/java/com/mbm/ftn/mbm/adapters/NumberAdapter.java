package com.mbm.ftn.mbm.adapters;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
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
import com.mbm.ftn.mbm.dialogs.NumberPickedDialog;
import com.mbm.ftn.mbm.fragments.NumberFragment;
import com.mbm.ftn.mbm.models.Number;

import java.util.List;

/**
 * Created by Makso on 5/21/2017.
 */

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.ViewHolder> {

    private static ViewHolder.ClickListener clickListener;
    private List<Number> numberList;


    /////////////////////////////////////Beginning of inner class///////////////////////////////////////////
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView number;
        private TextView description;
        private TextView website;
        private TextView address;

        public ViewHolder(View view, ClickListener listener) {
            super(view);
            clickListener = listener;
            title = (TextView) view.findViewById(R.id.title);
            number = (TextView) view.findViewById(R.id.number);
            description = (TextView) view.findViewById(R.id.description);
            website = (TextView) view.findViewById(R.id.website);
            address = (TextView) view.findViewById(R.id.address);
            view.setOnClickListener(this);
            //number.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v instanceof TextView) {
                //clickListener.onTextItemClick(getAdapterPosition(), (TextView) v);
            } else {
                clickListener.onItemClick(getAdapterPosition(), v);
            }
        }

        public interface ClickListener {
            void onItemClick(int position, View v);

            //void onTextItemClick(int position, TextView v);
        }
    }
    /////////////////////////////////////End of inner class///////////////////////////////////////////

    public void setOnItemClickListener(ViewHolder.ClickListener clickListener) {
        NumberAdapter.clickListener = clickListener;
    }

    public NumberAdapter(List<Number> numberList) {
        this.numberList = numberList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_number, parent, false);

        final NumberAdapter.ViewHolder viewHolder = new ViewHolder(view, new NumberAdapter.ViewHolder.ClickListener() {


            @Override
            public void onItemClick(int position, View v) {
                //Toast.makeText(v.getContext(), "SHORT is selected!", Toast.LENGTH_SHORT).show();

                TextView number = (TextView) v.findViewById(R.id.number);
                TextView title = (TextView) v.findViewById(R.id.title);
                TextView website = (TextView) v.findViewById(R.id.website);
                TextView address = (TextView) v.findViewById(R.id.address);

                //Pozivanje Dialoga i prosledjivanje parametara
                Bundle bundle = new Bundle();
                bundle.putString("titleName", title.getText().toString());
                bundle.putString("number", number.getText().toString());
                bundle.putString("website", website.getText().toString());
                bundle.putString("address", address.getText().toString());
                NumberPickedDialog numberPickedDialog = new NumberPickedDialog();
                numberPickedDialog.setArguments(bundle);

                android.support.v4.app.FragmentManager fragmentManager = ((NumbersActivity)parent.getContext()).getSupportFragmentManager();
                numberPickedDialog.show(fragmentManager, "showNumberPickedDialogTAG");
            }

            /*@Override
            public void onTextItemClick(int position, TextView v) {
                Toast.makeText(v.getContext(),  "Text is selected!", Toast.LENGTH_SHORT).show();
            }*/
        });

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Number number = numberList.get(position);
        holder.title.setText(number.getTitle());
        holder.number.setText(number.getNumber());
        holder.description.setText(number.getDescription());
        holder.website.setText(number.getWebsite());
        holder.address.setText(number.getAddress());

        if(number.getDescription() == null || number.getDescription().toString().isEmpty())
        {
            holder.description.setVisibility(View.GONE);
        } else {
            holder.description.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return numberList.size();
    }

}