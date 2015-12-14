package com.ninja.danh.sam.atunes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

// In this case, the fragment displays simple text based on the page
public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    @Bind(R.id.simpleSearchButton) Button simpleSearcButton;
    @Bind(R.id.advancedSearchSwitchButton) Button advancedButton;
    @Bind(R.id.simpleSearchField) EditText simpleSearchField;
    private int mPage;

    private List<Result> tracks;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
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
        if (mPage == 1) {
            view = inflater.inflate(R.layout.search_layout, container, false);
            ButterKnife.bind(this, view);
        } else {
            view = inflater.inflate(R.layout.fave_page, container, false);
            //TextView textView = (TextView) view;
            //textView.setText("Fragment #" + mPage);
        }

        return view;
    }

    @OnClick(R.id.simpleSearchButton)
    public void searchItem(View view) {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        String term = simpleSearchField.getText().toString();

        if (term.length() == 0) {
            simpleSearchField.setError("What do you want to search?");
            return;
        }
        term = term.replace(' ', '+');
        String url = "https://itunes.apple.com/search?term=" + term + "&limit=5";
        RestAdapter retrofit = new RestAdapter.Builder()
                .setEndpoint(url)
                .build();


        ITunesAPI api = retrofit.create(ITunesAPI.class);
        api.searchItem(new Callback<ITunesObj>() {
            @Override
            public void success(ITunesObj iTunesObj, Response response) {
                tracks = iTunesObj.getResults();
                for (int i = 0; i < tracks.size(); i++) {
                    Log.i("success", tracks.get(i).getTrackName());
                }

                Intent intent = new Intent(getActivity(), ResultsActivity.class);
                intent.putParcelableArrayListExtra("results", (ArrayList)tracks);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("fail", error.toString());
            }
        });
    }
}