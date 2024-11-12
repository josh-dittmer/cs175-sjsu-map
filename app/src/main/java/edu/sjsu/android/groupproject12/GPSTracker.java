package edu.sjsu.android.groupproject12;

import static com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class GPSTracker {

    private Context context;

    public GPSTracker(Context context) {
        this.context = context;
    }

    public void getLocation() {
        FusedLocationProviderClient provider =
                LocationServices.getFusedLocationProviderClient(context);
        if (!isLocationEnabled()) showSettingAlert();
        else if (!checkPermission()) requestPermission();
        else provider.getCurrentLocation(PRIORITY_BALANCED_POWER_ACCURACY, null)
                    .addOnSuccessListener(this::onSuccess);
    }

    private boolean isLocationEnabled() {
        LocationManager manager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showSettingAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage("Please enable location service.");
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

    private void requestPermission() {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
    }

    private void onSuccess(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Toast.makeText(context, "Elias Keller is at \n" +
                            "Lat " + latitude + "\nLong: " + longitude,
                    Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Unable to get location",
                    Toast.LENGTH_LONG).show();
    }
}
