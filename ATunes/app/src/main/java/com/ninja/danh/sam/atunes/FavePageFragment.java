package com.ninja.danh.sam.atunes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;

// In this case, the fragment displays simple text based on the page
public class FavePageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    public static FavePageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FavePageFragment fragment = new FavePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fave_page, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    public List<Result> getFavorites() {
        DatabaseHandler dbHandler = new DatabaseHandler(getActivity());
        List<Result> favs = dbHandler.getAll();

        for (int i = 0; i < favs.size(); i++) {
            Log.i("testing", favs.get(i).getTrackName());
        }

        return favs;
    }
}