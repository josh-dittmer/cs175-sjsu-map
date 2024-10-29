package edu.sjsu.android.project4eliaskeller;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TempActivity extends AppCompatActivity {

    Uri uri = Uri.parse("content://edu.sjsu.android.project4EliasKeller");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        findViewById(R.id.deleteBtn).setOnClickListener(this::delete);
        findViewById(R.id.insertBtn).setOnClickListener(this::insert);
    }


    void delete(View v) {
        getContentResolver().delete(uri, null, null);


    }
    void insert(View v){
        ContentValues values = new ContentValues();
        values.put(LocationsDB.LAT, 1.0);
        values.put(LocationsDB.LONG, 1.0);
        values.put(LocationsDB.ZOOM, 3.0f);
        getContentResolver().insert(uri, values);
    }
}