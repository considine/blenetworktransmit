package com.example.johnpconsidine.blemap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.johnpconsidine.transmit.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseObject;

public class MapActivity extends AppCompatActivity {
    private static final String TAG = MapActivity.class.getSimpleName();
    private GoogleMap mMap;
    Button bleButton;
    Button networkButton;
    MyReceiver receiver;
    private MapView mMapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        //initialize braod cast receiver
        IntentFilter filter = new IntentFilter(MyReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new MyReceiver();
        registerReceiver(receiver, filter);

        //initialize buttons
        bleButton = (Button) findViewById(R.id.bleButton);
        networkButton = (Button) findViewById(R.id.networkButton);

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
                R.id.mapview)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        //USe south bends coordinates since most of the pins are here
                        .target(new LatLng(41.6764, -86.2520))      // Sets the center of the map to location user
                        .zoom(10)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            }
        });




        networkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MapActivity.this, TransmitIntentService .class);
                startService(intent);
                Toast.makeText(MapActivity.this, "Network pins received", Toast.LENGTH_SHORT).show();



            }
        });



    }
    public class MyReceiver extends BroadcastReceiver {

        public static final String ACTION_RESP =
                "com.example.johnpconsidine.blemap.MESSAGE_PROCESSED";


        @Override
        public void onReceive(Context context, Intent intent) {
            for (ParseObject location : Application.getmPlaces()) {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(location.getParseGeoPoint(Utils.PLACE_OBJECT_LOCATION).getLatitude(), location.getParseGeoPoint(Utils.PLACE_OBJECT_LOCATION).getLongitude()))
                        .title(location.getString(Utils.PLACE_OBJECT_NOTES)));
               // Log.v(TAG, "The lat is " + location.getParseGeoPoint(Utils.PLACE_OBJECT_LOCATION).getLatitude());
            }

        }
    }

}
