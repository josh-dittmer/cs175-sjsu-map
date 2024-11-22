package edu.sjsu.android.groupproject12;

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
    protected final static String LOCATION_NAME = "LOCATION_NAME";
    protected final static String LATITUDE = "LATITUDE";
    protected final static String LONGITUDE = "LONGITUDE";
    protected final static String RADIUS = "RADIUS";
    protected final static String ZOOM = "ZOOM_LEVEL";
    protected final static String VISITED = "VISITED";

    private static final String CREATE_TABLE =
            String.format("CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT NOT NULL, " +
                    "%s DOUBLE NOT NULL, " +
                    "%s DOUBLE NOT NULL, " +
                    "%s DOUBLE NOT NULL, " +
                    "%s FLOAT NOT NULL, " +
                    "%s BOOLEAN NOT NULL);", TABLE_NAME, ID, LOCATION_NAME, LATITUDE, LONGITUDE, RADIUS, ZOOM, VISITED);

    public LocationsDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

        // add all buildings here
        addLocation(db, "MacQuarrie Hall", 37.334004608195535, -121.88176099075902, 0.0);
        addLocation(db, "Duncan Hall", 37.33248267290922, -121.88183017676899, 0.0);
        addLocation(db, "Sweeney Hall", 37.334000253665586, -121.8810702349945, 0.0);
        addLocation(db, "Spartan Complex", 37.33419421472578, -121.88266524558215, 0.0);
        addLocation(db, "Yoshihiro Uchida Hall", 37.333544979580935, -121.883741499371, 0.0);
        addLocation(db, "SJSU Student Wellness Center", 37.33494289649486, -121.88136862398663, 0.0);
    }

    private void addLocation(SQLiteDatabase db, String name, double latitude, double longitude, double radius) {
        ContentValues values = new ContentValues();
        values.put(LOCATION_NAME, name);
        values.put(LONGITUDE, longitude);
        values.put(LATITUDE, latitude);
        values.put(RADIUS, radius);
        values.put(ZOOM, 0.0f);
        values.put(VISITED, false);

        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insert(ContentValues contentValues) {
        SQLiteDatabase database = getWritableDatabase();
        return database.insert(TABLE_NAME, null, contentValues);
    }

    public long update(String locationName, boolean visited) {
        ContentValues values = new ContentValues();
        values.put("VISITED", visited);

        final String[] args = new String[]{ locationName };

        SQLiteDatabase database = getWritableDatabase();
        return database.update(TABLE_NAME, values, LOCATION_NAME + "=?", args);
    }

    public Cursor getAllLocations() {
        SQLiteDatabase database = getWritableDatabase();
        return database.query(TABLE_NAME,
                new String[]{ID, LOCATION_NAME, LATITUDE, LONGITUDE, RADIUS, ZOOM, VISITED},
                null, null, null, null, null);
    }

    public int deleteAllLocations() {
        SQLiteDatabase database = getWritableDatabase();
        return database.delete(TABLE_NAME, null, null);
    }
}
