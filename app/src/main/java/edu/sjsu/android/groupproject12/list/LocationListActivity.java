package edu.sjsu.android.groupproject12.list;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.sjsu.android.groupproject12.LocationsDB;
import edu.sjsu.android.groupproject12.R;
import edu.sjsu.android.groupproject12.databinding.ActivityLocationListBinding;
import edu.sjsu.android.groupproject12.databinding.ActivityMapsBinding;

/**
 * A fragment representing a list of Items.
 */
public class LocationListActivity extends AppCompatActivity {

    private ActivityLocationListBinding binding;

    private ArrayList<Location> locationList;

    private static final Uri CONTENT_URI = Uri.parse("content://edu.sjsu.android.groupProject12");

    public LocationListActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLocationListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.locationList = new ArrayList<>();

        // import locations from db
        Cursor cursor = this.getContentResolver().query(CONTENT_URI, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(LocationsDB.LOCATION_NAME));
                boolean visited = cursor.getInt(cursor.getColumnIndexOrThrow(LocationsDB.VISITED)) != 0;

                this.locationList.add(new Location(name, visited));
            } while (cursor.moveToNext());

            cursor.close();
        }

        RecyclerView view = binding.getRoot();
        view.setLayoutManager(new LinearLayoutManager(view.getContext()));
        view.setAdapter(new LocationViewAdapter(locationList));
    }
}