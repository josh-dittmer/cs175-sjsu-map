package edu.sjsu.android.groupproject12;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
//import androidx.navigation.Navigation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.sjsu.android.groupproject12.databinding.ActivityMapsBinding;
import edu.sjsu.android.groupproject12.list.LocationListActivity;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<Cursor> {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private GPSTracker tracker;

    //private final LatLng LOCATION_UNIV = new LatLng(37.335371, -121.881050);
    //private final LatLng LOCATION_CS = new LatLng(37.333714, -121.881860);

    private final Uri CONTENT_URI = Uri.parse("content://edu.sjsu.android.groupProject12");

    private boolean isMapStateRestored = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the SupportMapFragment and request the map to be loaded
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        LoaderManager.getInstance(this).restartLoader(0, null, this);

        binding.location.setOnClickListener(v -> tracker.returnStartLocation());
        binding.locationListButton.setOnClickListener(this::onLocationListClick);

        /*binding.city.setOnClickListener(this::switchView);
        binding.univ.setOnClickListener(this::switchView);
        binding.cs.setOnClickListener(this::switchView);
        binding.location.setOnClickListener(this::getLocation);
        binding.uninstall.setOnClickListener(this::uninstall);*/
    }

    private void onLocationListClick(View view) {
        Intent intent = new Intent(getApplicationContext(), LocationListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        tracker = new GPSTracker(this, mMap);

        //loadMapState();

        //mMap.setOnMapClickListener(this::addLocation);
        //mMap.setOnMapLongClickListener(p -> deleteAllLocations());
    }

    @Override
    protected void onPause() {
        super.onPause();
        //saveMapState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //saveMapState();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == GPSTracker.PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tracker.permissionsGranted();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
            }
        }
    }

    /*public void getLocation(View view){
        GPSTracker tracker = new GPSTracker(this);
        tracker.getLocation();
    }*/

    /*private void addLocation(LatLng point) {
        mMap.addMarker(new MarkerOptions().position(point));
        ContentValues values = new ContentValues();
        values.put(LocationsDB.LAT, point.latitude);
        values.put(LocationsDB.LONG, point.longitude);
        values.put(LocationsDB.ZOOM, mMap.getCameraPosition().zoom);
        new MyTask().execute(values);
    }

    private void deleteAllLocations() {
        mMap.clear();
        new MyTask().execute();
    }*/

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve data row by row
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(LocationsDB.LOCATION_NAME));

                double lat = cursor.getDouble(cursor.getColumnIndexOrThrow(LocationsDB.LATITUDE));
                double lng = cursor.getDouble(cursor.getColumnIndexOrThrow(LocationsDB.LONGITUDE));
                LatLng point = new LatLng(lat, lng);

                MarkerOptions marker = new MarkerOptions();
                marker.position(point);
                marker.title(name);
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

                mMap.addMarker(marker);
            } while (cursor.moveToNext());

            /*if (!isMapStateRestored) {
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, zoom);
                mMap.moveCamera(update);
            }*/
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    /*class MyTask extends AsyncTask<ContentValues, Void, Void> {

        @Override
        protected Void doInBackground(ContentValues... values) {
            // if there is a ContentValues object passed in, insert
            if (values != null & values.length > 0 && values[0] != null) {
                getContentResolver().insert(CONTENT_URI, values[0]);
            } else {
                // else delete
                getContentResolver().delete(CONTENT_URI, null, null);
            }
            return null;
        }
    }*/

    /*public void switchView(View view) {
        CameraUpdate update = null;
        if (view.getId() == R.id.city) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 10f);
        } else if (view.getId() == R.id.univ) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 14f);
        } else {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            update = CameraUpdateFactory.newLatLngZoom(LOCATION_CS, 18f);
        }
        mMap.animateCamera(update);
    }*/

    /*public void uninstall(View view) {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        startActivity(uninstallIntent);
    }*/

    /*private void saveMapState() {
        if (mMap != null) {
            SharedPreferences prefs = getSharedPreferences("MapPreferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putInt("mapType", mMap.getMapType());

            LatLng target = mMap.getCameraPosition().target;
            editor.putFloat("latitude", (float) target.latitude);
            editor.putFloat("longitude", (float) target.longitude);
            editor.putFloat("zoom", mMap.getCameraPosition().zoom);

            editor.apply();
        }
    }*/


    /*private void loadMapState() {
        SharedPreferences prefs = getSharedPreferences("MapPreferences", MODE_PRIVATE);

        int mapType = prefs.getInt("mapType", GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMapType(mapType);

        double latitude = prefs.getFloat("latitude", (float) LOCATION_UNIV.latitude);
        double longitude = prefs.getFloat("longitude", (float) LOCATION_UNIV.longitude);
        float zoom = prefs.getFloat("zoom", 14f); // Default zoom level

        LatLng target = new LatLng(latitude, longitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(target, zoom);
        mMap.moveCamera(update);

        isMapStateRestored = true;
    }*/

}