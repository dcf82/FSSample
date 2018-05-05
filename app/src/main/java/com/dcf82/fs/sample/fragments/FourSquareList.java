package com.dcf82.fs.sample.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcf82.fs.sample.R;
import com.dcf82.fs.sample.adapters.FourSquareAdapter;
import com.dcf82.fs.sample.fourSquareBeans.FourSquareResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FourSquareList extends Fragment {

    @BindView(R.id.list)
    RecyclerView mList;

    private FourSquareResponse mData;
    private FourSquareAdapter mAdapter;

    public FourSquareList() {}

    public static FourSquareList newInstance(FourSquareResponse fourSquareResponse) {
        FourSquareList fragment = new FourSquareList();
        Bundle args = new Bundle();
        args.putSerializable("data", fourSquareResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mData = (FourSquareResponse)getArguments().getSerializable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four_square_list, container, false);;

        ButterKnife.bind(this, view);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mList.setLayoutManager(manager);

        mAdapter = new FourSquareAdapter();
        mList.setAdapter(mAdapter);

        mAdapter.setmItems(mData.getResponse().getVenues());

        return view;
    }

    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}
