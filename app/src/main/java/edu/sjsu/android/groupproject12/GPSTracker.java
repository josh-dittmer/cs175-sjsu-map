package edu.sjsu.android.groupproject12;

import static com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class GPSTracker {

    private final Context context;

    private final GoogleMap mMap;
    private Marker mLastMarker;

    private static final LatLng START_LOCATION = new LatLng(37.335371, -121.881050);
    private static final int START_LOCATION_ZOOM = 18;

    public GPSTracker(Context context, GoogleMap map) {
        this.context = context;
        this.mMap = map;

        // checks permissions and starts location updates
        init();
    }

    public void init() {
        this.mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        returnStartLocation();

        if (!isLocationEnabled()){
            showSettingAlert();
        }

        else if (!checkPermission()) {
            requestPermission();
        }

        else {
            // all required permissions are present
            enabledLocationUpdates();
        }
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {
        // this function will be called every 2 seconds with the user's latest location
        // right now it just draws a blue marker on the map
        // in the future it should check if the user is inside a building and unlock it accordingly
        @Override
        public void onLocationResult(LocationResult res) {
            List<Location> locationList = res.getLocations();
            if (!locationList.isEmpty()) {
                Location lastLocation = locationList.get(locationList.size() - 1);
                LatLng lastCoords = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());

                if (mLastMarker != null) {
                    mLastMarker.remove();
                }

                MarkerOptions marker = new MarkerOptions();
                marker.position(lastCoords);
                marker.title("Current Position");
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                mLastMarker = mMap.addMarker(marker);
            }
        }
    };

    public void returnStartLocation() {
        //CameraUpdate update = CameraUpdateFactory.newLatLngZoom(START_LOCATION, START_LOCATION_ZOOM);
        CameraPosition position = new CameraPosition.Builder()
                .target(START_LOCATION)
                .tilt(60.0f)
                .zoom(START_LOCATION_ZOOM)
                .bearing(0.0f)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }

    // to be called after permissions are granted
    public void permissionsGranted() {
        enabledLocationUpdates();
    }

    private boolean isLocationEnabled() {
        LocationManager manager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showSettingAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage("This app requires location services to be enabled.");
        alertDialog.setPositiveButton("Enable", (dialog, which) -> {
            Intent intent =
                    new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(intent);
        });
        alertDialog.setNegativeButton("Cancel", (dialog, which) ->
                dialog.cancel());
        alertDialog.show();
    }

    private boolean checkPermission() {
        int result1 = ActivityCompat.checkSelfPermission
                (context, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int result2 = ActivityCompat.checkSelfPermission
                (context, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        return result1 == PackageManager.PERMISSION_GRANTED
                && result2 == PackageManager.PERMISSION_GRANTED;
    }

    static final int PERMISSIONS_REQUEST_CODE = 100;

    private void requestPermission() {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_CODE);
    }

    private void enabledLocationUpdates() {
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(context);

        LocationRequest.Builder locReqBuilder = new LocationRequest.Builder(2000);
        LocationRequest mLocationRequest = locReqBuilder.build();

        if (checkPermission()) {
            client.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mMap.setMyLocationEnabled(true);
        }
    }

    /*private void onSuccess(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Toast.makeText(context, "Elias Keller is at \n" +
                            "Lat " + latitude + "\nLong: " + longitude,
                    Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Unable to get location",
                    Toast.LENGTH_LONG).show();
    }*/
}
