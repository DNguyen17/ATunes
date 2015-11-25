package com.ninja.danh.sam.atunes;

/**
 * Created by Danh on 11/22/2015.
 */
import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.Callback;
import retrofit.http.GET;

public interface ITunesAPI {
    @GET("/")
    void searchItem(Callback<ITunesObj> response);
}
