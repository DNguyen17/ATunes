package com.ninja.danh.sam.atunes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danh on 11/25/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper{
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "favDB";
    private static final String TABLE_NAME = "favorites";

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( trackId INTEGER PRIMARY KEY, trackName TEXT, " +
                "artistName TEXT, collectionName TEXT, artworkUrl100 TEXT, previewUrl TEXT, " +
                "trackExplicitness TEXT, trackPrice FLOAT, kind TEXT, wrapperType TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addFavorite(Result result) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("trackId", result.getTrackId());
        values.put("trackName", result.getTrackName());
        values.put("artistName", result.getArtistName());
        values.put("collectionName", result.getCollectionName());
        values.put("artworkUrl100", result.getArtworkUrl100());
        values.put("previewUrl", result.getPreviewUrl());
        values.put("trackExplicitness", result.getTrackExplicitness());
        values.put("trackPrice", result.getTrackPrice());
        values.put("kind", result.getKind());
        values.put("wrapperType", result.getWrapperType());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Result getResult(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"trackId", "trackName", "artistName",
                        "collectionName", "artworkUrl100", "previewUrl", "trackExplicitness",
                "trackPrice", "kind", "wrapperType"}, "trackId" + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Result result = new Result();

        result.setTrackId(Integer.parseInt(cursor.getString(0)));
        result.setTrackName(cursor.getString(1));
        result.setArtistName(cursor.getString(2));
        result.setCollectionName(cursor.getString(3));
        result.setArtworkUrl100(cursor.getString(4));
        result.setPreviewUrl(cursor.getString(5));
        result.setTrackExplicitness(cursor.getString(6));
        result.setTrackPrice(Double.parseDouble(cursor.getString(7)));
        result.setKind(cursor.getString(8));
        result.setWrapperType(cursor.getString(9));

        return result;
    }

    public Result getResult(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"trackId", "trackName", "artistName",
                        "collectionName", "artworkUrl100", "previewUrl", "trackExplicitness",
                        "trackPrice", "kind", "wrapperType"}, "trackName" + "=?",
                new String[]{id}, null, null, null, null);

        boolean tb = false;
        if (cursor != null)
            tb = cursor.moveToFirst();

        Result result = new Result();

        if (!tb) {
            result.setTrackId(-1);
            return result;
        }

        result.setTrackId(Integer.parseInt(cursor.getString(0)));
        result.setTrackName(cursor.getString(1));
        result.setArtistName(cursor.getString(2));
        result.setCollectionName(cursor.getString(3));
        result.setArtworkUrl100(cursor.getString(4));
        result.setPreviewUrl(cursor.getString(5));
        result.setTrackExplicitness(cursor.getString(6));
        result.setTrackPrice(Double.parseDouble(cursor.getString(7)));
        result.setKind(cursor.getString(8));
        result.setWrapperType(cursor.getString(9));

        return result;
    }

    public List<Result> getAll() {
        List<Result> list = new ArrayList<Result>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Result result = new Result();
                result.setTrackId(Integer.parseInt(cursor.getString(0)));
                result.setTrackName(cursor.getString(1));
                result.setArtistName(cursor.getString(2));
                result.setCollectionName(cursor.getString(3));
                result.setArtworkUrl100(cursor.getString(4));
                result.setPreviewUrl(cursor.getString(5));
                result.setTrackExplicitness(cursor.getString(6));
                result.setTrackPrice(Double.parseDouble(cursor.getString(7)));
                result.setKind(cursor.getString(8));
                result.setWrapperType(cursor.getString(9));
                list.add(result);
            } while (cursor.moveToNext());
        }

        return list;

    }

    public void delete() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void deleteFave(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "trackName = ?", new String[]{name});
        db.close();
    }
}
