package com.ninja.danh.sam.atunes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
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

        //Intent intent = getIntent();


        //ListView resultsView = (ListView) findViewById(R.id.resultsListView);
        //resultsView.setAdapter(new MusicItemListAdapter(this, R.layout.list_result_item ,results));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fave_page, container, false);
        ButterKnife.bind(this, view);
        ArrayList<Result> results = (ArrayList)getFavorites();

        ListView resultsView = (ListView) view.findViewById(R.id.resultsListViewFav);
        resultsView.setAdapter(new MusicItemListAdapter(getActivity(), R.layout.list_result_item ,results));

        return view;
    }

    public List<Result> getFavorites() {
        DatabaseHandler dbHandler = new DatabaseHandler(getActivity());
        List<Result> favs = dbHandler.getAll();

        /*for (int i = 0; i < favs.size(); i++) {
            Log.i("testing", favs.get(i).getTrackName());
        }*/

        return favs;
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
                LayoutInflater inflater = getActivity().getLayoutInflater();
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