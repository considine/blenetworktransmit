package com.example.johnpconsidine.blemap;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.johnpconsidine.transmit.Loc;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {
    private static final String TAG = MapActivity.class.getSimpleName();
    private GoogleMap mMap;
    Button bleButton;
    Button networkButton;
    MyReceiver receiver;
    private MapView mMapView;

    /***** Ble Scan code for test only ******/
    /***************** start *********************/

    private BluetoothLeScanner mBluetoothLeScanner;
    private void startScan() {

        //the filter settings
        ScanFilter ResultsFilter = new ScanFilter.Builder()
                //.setDeviceAddress(string)
                //.setDeviceName(string)
                //.setManufacturerData()
                //.setServiceData()
                .setServiceUuid(LocRes.LOC_SERVICE)
                .build();

        ArrayList<ScanFilter> filters = new ArrayList<ScanFilter>();
        filters.add(ResultsFilter);

        //scan settings
        ScanSettings settings = new ScanSettings.Builder()
                //.setCallbackType() //int
                //.setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE) //AGGRESSIVE, STICKY  //require API 23
                //.setNumOfMatches(ScanSettings.MATCH_NUM_MAX_ADVERTISEMENT) //ONE, FEW, MAX  //require API 23
                //.setReportDelay(0) //0: no delay; >0: queue up
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY) //LOW_POWER, BALANCED, LOW_LATENCY
                .build();

        mBluetoothLeScanner.startScan(filters, settings, mScanCallback);
    }

    private void stopScan() {
        mBluetoothLeScanner.stopScan(mScanCallback);
    }

    //Get the scan callback and process results
    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            Log.d(TAG, "onScanResult");
            processResult(result);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {  //should add AdRecord to process the list of results one by one
            Log.d(TAG, "onBatchScanResults: "+results.size()+" results");
            for (ScanResult result : results) {
                processResult(result);
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            Log.w(TAG, "LE Scan Failed: "+errorCode);
        }

        private void processResult(ScanResult result) {

            //Log.i(TAG, "New LE Device: " + result.getDevice().getName() + " @ " + result.getRssi());
            Log.i(TAG, String.valueOf(System.currentTimeMillis())+result);

            LocRes locres = new LocRes(result.getScanRecord());
            Log.v(TAG,"lat is "+ locres.getLatitude()+"long is " + locres.getLongitude());
        }
    };
    /***** Ble Scan code for test only ******/
    /***************** end *********************/

    /***** Ble Advertise code for test only ******/
    /***************** start *********************/
    //private static final String TAG = "BleAdvertise";
    public static final ParcelUuid LOC_SERVICE = ParcelUuid.fromString("c57b1613-da40-4888-91e2-a6980d733a1b");

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;

    public void startAdvertising(byte[] data_out){ //the input is byte[] array, and any double should be transformed
        if (mBluetoothLeAdvertiser == null) return;

        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY) //3 modes: LOW_POWER, BALANCED, LOW_LATENCY
                .setConnectable(false)
                .setTimeout(0)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM) // ULTRA_LOW, LOW, MEDIUM, HIGH
                .build();

        AdvertiseData data = new AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .setIncludeTxPowerLevel(true)
                .addServiceUuid(LOC_SERVICE)
                .addServiceData(LOC_SERVICE,data_out)
                .build();

        mBluetoothLeAdvertiser.startAdvertising(settings, data, mAdvertiseCallback);
    }

    public void stopAdvertising() {
        if (mBluetoothLeAdvertiser == null) return;

        mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
    }

    public void restartAdvertising(byte[] data_out) {
        stopAdvertising();
        startAdvertising(data_out);
    }

    private AdvertiseCallback mAdvertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            Log.i(TAG, "LE Advertise Started.");
        }

        @Override
        public void onStartFailure(int errorCode) {
            Log.w(TAG, "LE Advertise Failed: "+errorCode);
        }
    };

    //When advertising loc data, transform float to byte[] array
    public static byte[] FloatToArray(float Value) {
        int accum = Float.floatToRawIntBits(Value);
        byte[] byteRet = new byte[4];
        byteRet[0] = (byte)(accum & 0xFF);
        byteRet[1] = (byte)((accum>>8) & 0xFF);
        byteRet[2] = (byte)((accum>>16) & 0xFF);
        byteRet[3] = (byte)((accum>>24) & 0xFF);
        return byteRet;
    }

    //concatenate two byte[] array of latitude and byte[] array of longitude.
    public static byte[] ArrayConcat(byte[] Lat, byte[] Long){
        byte[] ret = new byte[Lat.length + Long.length];

        System.arraycopy(Lat, 0, ret, 0, Lat.length);
        System.arraycopy(Long, 0, ret, Lat.length, Long.length);
        return ret;
    }

    /***** Ble Advertise code for test only ******/
    /*****************  end  *********************/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //initialize braod cast receiver
        IntentFilter filter = new IntentFilter(MyReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new MyReceiver();
        registerReceiver(receiver, filter);

        /************* start ble scan, always on ****************/
        startScan();
        /************** start ble scan, always on ***************/




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

                Intent intent = new Intent(MapActivity.this, TransmitIntentService.class);
                startService(intent);
                Toast.makeText(MapActivity.this, "Network pins received", Toast.LENGTH_SHORT).show();

            }
        });

        bleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call ble start advertising;
                Loc loc = Application.getPlace(0);
                Log.v(TAG, "lat is " + loc.getLatitude() + "long is "+ loc.getLongitude());
                byte[] Lat = FloatToArray(loc.getLatitude());
                byte[] Long = FloatToArray(loc.getLongitude());
                byte[] Loc_Ad = ArrayConcat(Lat, Long);
                Log.v(TAG, "loc in byte " + Loc_Ad);

                restartAdvertising(Loc_Ad);
            }
        });



    }
    public class MyReceiver extends BroadcastReceiver {

        public static final String ACTION_RESP =
                "com.example.johnpconsidine.blemap.MESSAGE_PROCESSED";


        @Override
        public void onReceive(Context context, Intent intent) {
            for (Loc location : Application.getmPlaces()) {
                Log.v(TAG, "the lat is " + location.getLatitude());
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng((double) location.getLatitude(), (double) location.getLongitude()))
                        .title("no notes"));
               // Log.v(TAG, "The lat is " + location.getParseGeoPoint(Utils.PLACE_OBJECT_LOCATION).getLatitude());
            }

        }
    }

}
