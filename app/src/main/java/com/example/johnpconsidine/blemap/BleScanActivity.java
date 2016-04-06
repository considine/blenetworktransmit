package com.example.johnpconsidine.blemap;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.johnpconsidine.transmit.Loc;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jerry on 04/06/2016.
 */
public class BleScanActivity extends AppCompatActivity {
    private static final String TAG = "BleScanActivity";

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;


    private void startScan() {

        //the filter settings
        ScanFilter ResultsFilter = new ScanFilter.Builder()
                //.setDeviceAddress(string)
                //.setDeviceName(string)
                //.setManufacturerData()
                //.setServiceData()
                .setServiceUuid(Loc.LOC_SERVICE)
                .build();

        ArrayList<ScanFilter> filters = new ArrayList<ScanFilter>();
        filters.add(ResultsFilter);

        //scan settings
        ScanSettings settings = new ScanSettings.Builder()
                //.setCallbackType() //int
                //.setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE) //AGGRESSIVE, STICKY  //require API 23
                //.setNumOfMatches(ScanSettings.MATCH_NUM_MAX_ADVERTISEMENT) //ONE, FEW, MAX  //require API 23
                .setReportDelay(0) //0: no delay; >0: queue up
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

            Loc loc = new Loc(result.getScanRecord());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            //Bluetooth is disabled
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
            finish();
            return;
        }

        /*
         * Check for Bluetooth LE Support.  In production, our manifest entry will keep this
         * from installing on these devices, but this will allow test devices or other
         * sideloads to report whether or not the feature exists.
         */
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "No LE Support.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        BluetoothManager manager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = manager.getAdapter();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
    }


}
