package com.ninja.danh.sam.atunes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        ArrayList<Result> results = intent.getParcelableArrayListExtra("results");

        Log.d("parceble data", results.get(0).getArtistName());

        ListView resultsView = (ListView) findViewById(R.id.resultsListView);
        resultsView.setAdapter(new MusicItemListAdapter(this, R.layout.list_result_item ,results));
    }

    public class MusicItemListAdapter extends ArrayAdapter<Result> {

        private List<Result> objects;
        Context context;
        private int resource;

        public MusicItemListAdapter(Context context, int textViewResourceId, List<Result> items) {
            super(context, textViewResourceId, items);
            objects = items;
            context = context;
            resource = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row;
            /*LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
            row = inflater.inflate(resource, parent, false);*/

            if (convertView == null) {
                //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(resource, parent, false);
            } else {
                Log.d("recycleview", "I am recycling a view for element " + position);
                row = convertView;
            }

            final Result track = objects.get(position);
            Log.d("create table", track.getTrackName());
            TextView title = (TextView)row.findViewById(R.id.result_item_title);
            TextView artist = (TextView)row.findViewById(R.id.result_item_artist);
            TextView price = (TextView)row.findViewById(R.id.result_item_price);
            TextView explicit = (TextView)row.findViewById(R.id.result_item_explicitness);

            if (track.getTrackExplicitness().equals("explicit")) {
                explicit.setText("E");
            }

            title.setText(track.getTrackName());
            artist.setText(track.getArtistName());
            price.setText(track.getTrackPrice().toString());

            return row;
        }

        }


}
