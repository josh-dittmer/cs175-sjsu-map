package edu.sjsu.android.groupproject12;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LocationsDB extends SQLiteOpenHelper {

    public final static String DATABASE_NAME= "LocationDB";
    public final static String TABLE_NAME = "Locations";
    public final static String ID = "_id";
    public final static String LOCATION_NAME = "LOCATION_NAME";
    public final static String LATITUDE = "LATITUDE";
    public final static String LONGITUDE = "LONGITUDE";
    public final static String RADIUS = "RADIUS";
    public final static String ZOOM = "ZOOM_LEVEL";
    public final static String VISITED = "VISITED";

    protected final static float SMALL = 30;
    protected final static float MEDIUM = 50;
    protected final static float LARGE = 970;

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
        super(context, DATABASE_NAME, null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

        // add all buildings here
        addLocation(db, "Administration",                       37.336629460619555, -121.88283023972372, SMALL);
        addLocation(db, "Art Building",                         37.335947519663904, -121.87976760772008, SMALL);
        addLocation(db, "Associated Students House",            37.337128749375154, -121.87954271283378, SMALL);
        addLocation(db, "Boccardo Business Center",             37.33658660968051,  -121.87872278135389, SMALL);
        addLocation(db, "Business Tower",                       37.33711273690032,  -121.87889494134407, SMALL);
        addLocation(db, "Career Center",                        37.33689663662828,  -121.88316085453401, SMALL);
        addLocation(db, "Campus Village A",                     37.33461541279022,  -121.87759849708424, SMALL);
        addLocation(db, "Campus Village B",                     37.33505894005537,  -121.87756211736645, SMALL);
        addLocation(db, "Campus Village C",                     37.335332125835706, -121.87814419285097, SMALL);
        addLocation(db, "Campus Village 2",                     37.33488860018299,  -121.87855245413942, SMALL);
        addLocation(db, "Central Classroom Building",           37.33557374403387,  -121.88180765466298, SMALL);
        addLocation(db, "Central Plant",                        37.336118521772526, -121.87838270181874, SMALL);
        addLocation(db, "Clark Hall",                           37.33616300112114,  -121.88249027499428, SMALL);
        addLocation(db, "Corporation Yard Offices",             37.33815376166999,  -121.8802713457516, SMALL);
        addLocation(db, "Corporation Yard Trades Building",     37.3380636599446,   -121.87964810253925, SMALL);
        addLocation(db, "Dining Commons",                       37.334014427435264, -121.87871415794068, SMALL);
        addLocation(db, "Dudley Moorhead Hall",                 37.33634323664453,  -121.88378892142718, SMALL);
        addLocation(db, "Duncan Hall",                          37.33248267290922,  -121.88183017676899, SMALL);
        addLocation(db, "Dr. Martin Luther King Jr. Library",   37.33556025945764,  -121.88499084985756, SMALL);
        addLocation(db, "Dwight Bentel Hall",                   37.33508636969353,  -121.882645494264, SMALL);
        addLocation(db, "Engineering Building",                 37.33713564910301,  -121.88162978529095, LARGE);
        addLocation(db, "Faculty Offices Building",             37.33464462220017,  -121.88265346389251, LARGE);
        addLocation(db, "Hammer Theatre",                       37.33375566686523,  -121.8864608753023, LARGE);
        addLocation(db, "Hugh Gillis Hall",                     37.336043536863606, -121.88450237053074, LARGE);
        addLocation(db, "Health Building",                      37.33575284295194,  -121.87915718951817, LARGE);
        addLocation(db, "Industrial Studies",                   37.33769976749706,  -121.88061637073564, LARGE);
        addLocation(db, "Information Technology",               37.3359597001574,   -121.88332962134592, LARGE);
        addLocation(db, "Interdisciplinary Sciences Building",  37.33316634655916,  -121.88291973045379, LARGE);
        addLocation(db, "Joe West Hall",                        37.33432425973615,  -121.87807020961968, LARGE);
        addLocation(db, "MacQuarrie Hall",                      37.33349505398416,  -121.88183932572258, MEDIUM);
        addLocation(db, "Music Building",                       37.33565485045456,  -121.88084879202778, LARGE);
        addLocation(db, "North Parking Garage",                 37.339410231718446, -121.8807346575884, LARGE);
        addLocation(db, "Provident Credit Union Event Center",  37.335252216298706, -121.88007190760902, LARGE);
        addLocation(db, "Science Building",                     37.33471315844412,  -121.88471079519294, LARGE);
        addLocation(db, "SJSU Student Wellness Center",         37.33494289649486,  -121.88136862398663, LARGE);
        addLocation(db, "South Parking Garage",                 37.33315312012486,  -121.8808404323862, LARGE);
        addLocation(db, "Spartan Complex",                      37.33419421472578,  -121.88266524558215, LARGE);
        addLocation(db, "Spartan Memorial",                     37.33423498248875,  -121.88332761887256, MEDIUM);
        addLocation(db, "Spartan Recreation & Aquatic Center",  37.33454309540966,  -121.87968757814254, LARGE);
        addLocation(db, "Student Union",                        37.33655792336295,  -121.88072508547776, MEDIUM);
        addLocation(db, "Sweeney Hall",                         37.334000253665586, -121.8810702349945, MEDIUM);
        addLocation(db, "Tower Hall",                           37.335306742816975, -121.88341099460202, MEDIUM);
        addLocation(db, "University Police Department",         37.33354470902471,  -121.88020316743632, MEDIUM);
        addLocation(db, "Washburn Hall",                        37.333645469010655, -121.87933599794745, MEDIUM);
        addLocation(db, "Washington Square Hall",               37.334223084499065, -121.88424709448562, MEDIUM);
        addLocation(db, "West Parking Garage",                  37.33240629217577,  -121.88303739562778, LARGE);
        addLocation(db, "Yoshihiro Uchida Hall",                37.333544979580935, -121.883741499371, MEDIUM);
        
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
