package com.ninja.danh.sam.atunes;

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
        new ItunesAPISearch().execute("drake");


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        this.searchItem();



    }

    public void searchItem() {
        String url = "https://itunes.apple.com/search?term=drake&limit=5";
        RestAdapter retrofit = new RestAdapter.Builder()
                .setEndpoint(url)
                .build();

        ITunesAPI api = retrofit.create(ITunesAPI.class);
        api.searchItem(new Callback<ITunesObj>() {
            @Override
            public void success(ITunesObj iTunesObj, Response response) {
                List<Result> tracks = iTunesObj.getResults();
                for (int i = 0; i < tracks.size(); i++) {
                    Log.i("success", tracks.get(i).getTrackName());
                }

                DatabaseHandler dbHandler = new DatabaseHandler(MainSearchActivity.this);
                dbHandler.addFavorite(tracks.get(0));
                Result res = dbHandler.getResult(780330308);

                Log.i("testing", res.getTrackName());

            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("fail", error.toString());
            }
        });
    }
}
