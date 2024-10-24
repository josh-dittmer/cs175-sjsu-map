package edu.sjsu.android.project4eliaskeller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LocationsDB extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME= "LocationDB";
    protected final static String TABLE_NAME = "Locations";
    protected final static String ID = "_id";
    protected final static String LAT = "latitude";
    protected final static String LONG = "longitude";
    protected final static String ZOOM = "zoom_level";

    private static final String CREATE_TABLE =
            String.format("CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%S DOUBLE NOT NULL, "+
                    "%S DOUBLE NOT NULL, "+
                    "%S FLOAT NOT NULL);", TABLE_NAME, ID, LAT, LONG, ZOOM);

    public LocationsDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        sqlDB.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i1) {
        sqlDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqlDB);
    }

    public long insert(ContentValues contentValues) {
        SQLiteDatabase database = getWritableDatabase();
        return database.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getAllLocations() {
        SQLiteDatabase database = getWritableDatabase();
        return database.query(TABLE_NAME,
                new String[]{ID, LAT, LONG, ZOOM},
                null, null, null, null, null);
    }

    public int deleteAllLocations() {
        SQLiteDatabase database = getWritableDatabase();
        return database.delete(TABLE_NAME, null, null);
    }
}
