package edu.sjsu.android.groupproject12;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.Objects;

public class LocationsProvider extends ContentProvider {

    private LocationsDB locationsDB;

    @Override
    public boolean onCreate() {
        locationsDB = new LocationsDB(getContext());
        return true;
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return locationsDB.deleteAllLocations();
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long rowID = locationsDB.insert(values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(uri, rowID);
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return locationsDB.getAllLocations();
    }

    /*
     not implemented and there is no need to do that for this project
     */

    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        assert selectionArgs != null;
        assert values != null;

        return (int) locationsDB.update(selectionArgs[0], (Boolean) values.get(LocationsDB.VISITED));
    }


}