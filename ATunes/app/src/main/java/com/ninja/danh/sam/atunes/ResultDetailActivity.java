package com.ninja.danh.sam.atunes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ResultDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);

        Intent intent = getIntent();
        Result result_detail = intent.getParcelableExtra("result");
        Log.d("result detail", result_detail.getTrackName());
        Log.d("result detail", result_detail.getArtworkUrl100());
        setImage(result_detail.getArtworkUrl100());
        loadDetails(result_detail);
    }

    protected void setImage(String image_url) {
        ImageView result_art = (ImageView) findViewById(R.id.result_detail_image);
        Glide.with(getApplicationContext())
                .load(image_url)
                .into(result_art);

    }

    protected void loadDetails(Result result) {
        //set the title
        TextView title = (TextView) findViewById(R.id.result_detail_title);
        title.setText(result.getTrackName());

        //set the artist name
        TextView artist = (TextView) findViewById(R.id.result_detail_artist);
        artist.setText(result.getArtistName());

        //set the price
        TextView price = (TextView) findViewById(R.id.result_detail_price);
        price.setText(result.getTrackPrice().toString());

    }
}
