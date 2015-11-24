package com.ninja.danh.sam.atunes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ninja.danh.sam.atunes.ITunesAPI;
import com.ninja.danh.sam.atunes.ITunesObj;
import com.ninja.danh.sam.atunes.Result;

import retrofit.*;




public class MainSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        new ItunesAPISearch().execute("drake");


    }
}
