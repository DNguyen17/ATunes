package com.ninja.danh.sam.atunes;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Enumeration;
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
    @Bind(R.id.simpleSearchButton) Button simpleSearchButton;
    @Bind(R.id.advancedSearchSwitchButton) Button advancedButton;
    @Bind(R.id.simpleSearchField) EditText simpleSearchField;
    @Bind(R.id.advancedSearchArtistField) EditText advancedSearchArtistField;
    @Bind(R.id.advancedSearchTitleField) EditText advancedSearchTitleField;
    @Bind(R.id.advancedSearchGenreField) EditText advancedSearchGenreField;
    //bind spinner
    @Bind(R.id.media_type_spinner) Spinner media_type_spinner;

    private int mPage;
    private boolean advancedSearch; //false if advanced search fields not shown
    private enum mediaType {
        MUSICVIDEO,
        MUSIC,
        MOVIE,
        EBOOK,
        PODCAST,
        TVSHOW
    }
    private mediaType media;

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

        //initialize advanced search
        advancedSearch = false;

        //initiate media type
        media = mediaType.MUSICVIDEO;
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
        }

        //set up spinner
        setupSpinner();

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

                Intent intent = new Intent(getActivity(), ResultsActivity.class);
                intent.putParcelableArrayListExtra("results", (ArrayList) tracks);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("fail", error.toString());
            }
        });
    }

    @OnClick(R.id.advancedSearchSwitchButton)
    public void toggleAdvancedSearch(View v) {
        if (!advancedSearch) {
            simpleSearchField.setVisibility(View.INVISIBLE);
            advancedSearchArtistField.setVisibility(View.VISIBLE);
            advancedSearchTitleField.setVisibility(View.VISIBLE);
            advancedSearchGenreField.setVisibility(View.VISIBLE);
            advancedSearch = true;
            advancedButton.setText("Simple Search");
        } else {
            simpleSearchField.setVisibility(View.VISIBLE);
            advancedSearchArtistField.setVisibility(View.INVISIBLE);
            advancedSearchTitleField.setVisibility(View.INVISIBLE);
            advancedSearchGenreField.setVisibility(View.INVISIBLE);
            advancedSearch = false;
            advancedButton.setText("Advanced Search");
        }
    }

    public void setupSpinner() {
        //media_type_spinner = new Spinner(getContext());

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.media_choices, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        media_type_spinner.setAdapter(adapter);
        media_type_spinner.setOnItemSelectedListener(new SpinnerActivity());

    }

    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            String selected = (String) parent.getItemAtPosition(pos);
            selected = selected.replace(" ", "").toUpperCase();
            Log.d("selected media type", selected);

            handleMediaChange(selected);
            
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }

    }

    public void handleMediaChange(String newMedia) {
        media = translateStringtoMediaType(newMedia);

        //change the text in the query edittext
        simpleSearchField.setText(changeQueryBox(media));

    }

    public String changeQueryBox(mediaType m_type) {
        switch (m_type) {
            case MUSICVIDEO: return getString(R.string.music_video_text);
            case MUSIC:      return getString(R.string.music_text);
            case MOVIE:      return getString(R.string.movie_text);
            case EBOOK:      return getString(R.string.ebook_text);
            case PODCAST:    return getString(R.string.podcast_text);
            case TVSHOW:     return getString(R.string.tv_show_text);
            default:         return getString(R.string.music_video_text);
        }
    }

    public mediaType translateStringtoMediaType(String newMedia) {
        switch (newMedia) {
            case "MUSICVIDEO" : return mediaType.MUSICVIDEO;
            case "MUSIC"      : return mediaType.MUSIC;
            case "MOVIE"      : return mediaType.MOVIE;
            case "EBOOK"      : return mediaType.EBOOK;
            case "PODCAST"    : return mediaType.PODCAST;
            case "TVSHOW"     : return mediaType.TVSHOW;
            default           : return mediaType.MUSICVIDEO;
        }
    }
}