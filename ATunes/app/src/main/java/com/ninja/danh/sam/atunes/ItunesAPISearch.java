package com.ninja.danh.sam.atunes;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sam on 11/20/15.
 */
public class ItunesAPISearch extends AsyncTask<String, Double, String> {

    @Override
    protected String doInBackground(String... params) {
        String apibase = "https://itunes.apple.com/search?term=";

        String entity_string = "&entity=musicVideo";

        String response = "";

        try {

            URL query = new URL(apibase + params[0] + entity_string);

            HttpURLConnection connection = (HttpURLConnection) query.openConnection();
            connection.setRequestMethod("GET");

            connection.connect();
            int responseCode = connection.getResponseCode();
            Log.d("book_connection", "The response code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = convertToString(connection.getInputStream());
            } else {
                Log.e("netconnect", "Uh oh, our URL might not be right!");
            }

            connection.disconnect();

        } catch (MalformedURLException e) {
            Log.e("APISearch", "There is an error with the query URL:" + e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("successful_api", String.valueOf(result.length()));
    }

    protected String convertToString(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line+"\n");
        }
        reader.close();
        return builder.toString();
    }
    
}
