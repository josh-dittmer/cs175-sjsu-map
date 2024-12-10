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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    private GPSTracker tracker;

    private final Uri CONTENT_URI = Uri.parse("content://edu.sjsu.android.groupProject12");

    //private boolean isMapStateRestored = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
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
    }

    private void onLocationListClick(View view) {
        Intent intent = new Intent(getApplicationContext(), LocationListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        tracker = new GPSTracker(this, mMap);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
                boolean visited = cursor.getInt(cursor.getColumnIndexOrThrow(LocationsDB.VISITED)) > 0;
                LatLng point = new LatLng(lat, lng);

                MarkerOptions marker = new MarkerOptions();
                marker.position(point);
                marker.title(name);

                if (visited) {
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                } else {
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                }
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