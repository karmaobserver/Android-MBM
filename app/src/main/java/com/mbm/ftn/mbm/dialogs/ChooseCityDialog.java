package com.mbm.ftn.mbm.dialogs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.adapters.CityAdapter;
import com.mbm.ftn.mbm.adapters.NumberAdapter;
import com.mbm.ftn.mbm.dao.CityDao;
import com.mbm.ftn.mbm.dao.NumberDao;
import com.mbm.ftn.mbm.models.City;
import com.mbm.ftn.mbm.models.Number;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makso on 6/15/2017.
 */

public class ChooseCityDialog extends DialogFragment {

    private static final String ARG_PARAM1 = "titleName";


    private String param1;


    private RecyclerView recyclerView;
    private List<City> cityList = new ArrayList<>();
    private CityAdapter cityAdapter;

    CityDao cityDao = null;

    public ChooseCityDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ChooseCityDialog newInstance(String param1, String param2, String param3, String param4) {

        ChooseCityDialog fragment = new ChooseCityDialog();
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
        return inflater.inflate(R.layout.dialog_choose_city, container);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("titleName");
;
        getDialog().setTitle(title);

        cityDao = new CityDao(getContext());
        cityList = cityDao.findAllExpectCountry();


        //add adapter to dialog
        cityAdapter = new CityAdapter(cityList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(cityAdapter);



        Button button = (Button) view.findViewById(R.id.button_close);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

    }
}
