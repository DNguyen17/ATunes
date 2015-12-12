package com.ninja.danh.sam.atunes;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ninja.danh.sam.atunes.ITunesAPI;
import com.ninja.danh.sam.atunes.ITunesObj;
import com.ninja.danh.sam.atunes.Result;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        //new ItunesAPISearch().execute("drake");


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainSearchActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void searchItem(String term) {
        String url = "https://itunes.apple.com/search?term=" + term + "&limit=5";
        RestAdapter retrofit = new RestAdapter.Builder()
                .setEndpoint(url)
                .build();

        ITunesAPI api = retrofit.create(ITunesAPI.class);
        api.searchItem(new Callback<ITunesObj>() {
            @Override
            public void success(ITunesObj iTunesObj, Response response) {
                List<Result> tracks = iTunesObj.getResults();
                /*for (int i = 0; i < tracks.size(); i++) {
                    Log.i("success", tracks.get(i).getTrackName());
                }*/
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("fail", error.toString());
            }
        });
    }

    public List<Result> getFavorites() {
        DatabaseHandler dbHandler = new DatabaseHandler(MainSearchActivity.this);
        List<Result> favs = dbHandler.getAll();

        for (int i = 0; i < favs.size(); i++) {
            Log.i("testing", favs.get(i).getTrackName());
        }

        return favs;
    }
}
