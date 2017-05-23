package com.mbm.ftn.mbm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.adapters.NumberAdapter;
import com.mbm.ftn.mbm.dao.NumberDao;
import com.mbm.ftn.mbm.dao.NumberListDao;
import com.mbm.ftn.mbm.models.Number;
import com.mbm.ftn.mbm.models.NumberList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makso on 5/21/2017.
 */

public class NumberFragment extends Fragment {

    private static final String TAG = "NumberFragment";
    private static final String ARG_PARAM1 = "numberType";
    private static final String ARG_PARAM2 = "param2";

    private List<Number> numberList = new ArrayList<>();
    private NumberAdapter numberAdapter;

    NumberDao numberDao = null;
    NumberListDao numberListDao = null;

    private String mParam1;
    private String mParam2;

    private TextView title;

    //private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    public NumberFragment() {
    }

    public static NumberFragment newInstance(String param1, String param2) {
        NumberFragment fragment = new NumberFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_number, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        numberAdapter = new NumberAdapter(numberList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        //recyclerView.setNestedScrollingEnabled(false);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setScrollContainer(false);
        recyclerView.setAdapter(numberAdapter);

        String numberListType = getArguments().getString("numberType");
        Log.d(TAG, numberListType);
        title = (TextView) view.findViewById(R.id.title);


        numberDao = new NumberDao(getContext());
        numberListDao = new NumberListDao(getContext());

        if (numberListType == getResources().getString(R.string.numbers_emergency_services)) {
            title.setText(getActivity().getResources().getString(R.string.numbers_emergency_services));
           /* List<Number> numbers = numberDao.findAll();
            Log.d("NUMBERS", "Numbers: " + numbers.size());
            List<NumberList> numbersLists = numberListDao.findAll();
            Log.d("NUMBERSLISTS", "NumbersLists: " + numbersLists.size());*/
            numberList.clear();
            numberList.addAll(numberDao.findAllbyListName(getResources().getString(R.string.numbers_emergency_services)));
            numberAdapter.notifyDataSetChanged();

        } else if (numberListType == getResources().getString(R.string.numbers_mobile_operators)){
            title.setText(getActivity().getResources().getString(R.string.numbers_mobile_operators));
            numberList.clear();
            numberList.addAll(numberDao.findAllbyListName(getResources().getString(R.string.numbers_mobile_operators)));
            numberAdapter.notifyDataSetChanged();
        } else if (numberListType == getResources().getString(R.string.numbers_health_care)){
            title.setText(getActivity().getResources().getString(R.string.numbers_health_care));
            numberList.clear();
            numberList.addAll(numberDao.findAllbyListName(getResources().getString(R.string.numbers_health_care)));
            numberAdapter.notifyDataSetChanged();
        } else {
            title.setText(getActivity().getResources().getString(R.string.numbers_mobile_operators));
           // prepareMovieData();
        }


       /* moviesAdapter.setOnItemClickListener(new MoviesAdapter.MyViewHolder.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(v.getContext(),  "SHORT ACTVITY is selected!" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextItemClick(int position, TextView v) {
                Toast.makeText(v.getContext(),  "TEXT ACTVITY is selected!" + position, Toast.LENGTH_SHORT).show();
            }

        });*/

        return view;
    }



   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/


}

