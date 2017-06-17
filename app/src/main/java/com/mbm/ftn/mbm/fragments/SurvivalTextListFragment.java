package com.mbm.ftn.mbm.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.adapters.SurvivalTextAdapter;
import com.mbm.ftn.mbm.dao.SurvivalTextDao;
import com.mbm.ftn.mbm.models.SurvivalText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris K on 15-Jun-17.
 */

public class SurvivalTextListFragment extends Fragment {

    private RecyclerView recyclerView;
    private SurvivalTextAdapter sAdapter;
    private SurvivalTextDao survivalTextDao = null;
    private List<SurvivalText> textList = new ArrayList<>();

    public SurvivalTextListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_survival_text, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        sAdapter = new SurvivalTextAdapter(textList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(sAdapter);



        prepareTextData();

        return view;
    }

    private void prepareTextData() {

        survivalTextDao = new SurvivalTextDao(getContext());
        textList.addAll(survivalTextDao.findAll());
        sAdapter.notifyDataSetChanged();
    }
}
