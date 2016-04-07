package com.example.johnpconsidine.blemap;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class BleAdvertiseActivity extends AppCompatActivity {

    private static final String TAG = "BleAdvertise";

    public static final ParcelUuid LOC_SERVICE = ParcelUuid.fromString("c57b1613-da40-4888-91e2-a6980d733a1b");

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;

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

/*    byte[] Lat = FloatToArray(latitude);
    byte[] Long = FloatToArray(longitude);*/

    //concatenate two byte[] array of latitude and byte[] array of longitude.
    public static byte[] ArrayConcat(byte[] Lat, byte[] Long){
        byte[] ret = new byte[Lat.length + Long.length];

        System.arraycopy(Lat, 0, ret, 0, Lat.length);
        System.arraycopy(Long, 0, ret, Lat.length, Long.length);
        return ret;
    }

/*    //When receiving loc data, transform byte[] array to float
    public static float ArryToFloat(byte[] Array,int Pos)
    {
        int accum = 0;
        accum = Array[Pos+0] & 0xFF;
        accum |= (long)(Array[Pos+1] & 0xFF)<<8;
        accum |= (long)(Array[Pos+2] & 0xFF)<<16;
        accum |= (long)(Array[Pos+3] & 0xFF)<<24;
        return Float.intBitsToFloat(accum);
    }*/


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);

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

        /*
         * Check for advertising support. Not all devices are enabled to advertise
         * Bluetooth LE data.
         */
        if (!mBluetoothAdapter.isMultipleAdvertisementSupported()) {
            Toast.makeText(this, "No Advertising Support.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }

    public void onUpdateClick(View v){
        //restartAdvertising(data_out);
    }

}
