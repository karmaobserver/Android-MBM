package com.mbm.ftn.mbm.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.adapters.SurvivalTextAdapter;
import com.mbm.ftn.mbm.dao.SurvivalTextDao;
import com.mbm.ftn.mbm.models.SurvivalText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris K on 15-Jun-17.
 */

public class SurvivalTextViewFragment extends Fragment {
    private RecyclerView recyclerView;
    private SurvivalTextAdapter sAdapter;
    private SurvivalTextDao survivalTextDao = null;
    private List<SurvivalText> textList = new ArrayList<>();

    public SurvivalTextViewFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_survival_text_view, container, false);
        TextView viewTopic = (TextView) view.findViewById(R.id.view_topic);
        TextView viewText = (TextView) view.findViewById(R.id.view_text);

     //   viewTopic.setText("Naslov 123");
     //   viewText.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
      //          " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." +
      //          " Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.");


        String topic = "";
        String text = "";

        if (getArguments() != null) {
            topic = getArguments().getString("topic");
            text = getArguments().getString("text");
        }

        viewTopic.setText(topic);
        viewText.setText(text);

        return view;
    }


}
