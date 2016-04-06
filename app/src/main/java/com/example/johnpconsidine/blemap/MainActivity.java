package com.example.johnpconsidine.blemap;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.johnpconsidine.blemap.ParseClasses.User;
import com.example.johnpconsidine.transmit.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = MainActivity.class.getSimpleName();
    BluetoothAdapter mBluetoothAdapter;
    private final static int REQUEST_ENABLE_BT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        ParseQuery<User> loginQuery = new ParseQuery<User>(Utils.USER_DATA);
        loginQuery.whereEqualTo(Utils.USER_DATA_USERNAME, "jconsidi");
        loginQuery.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> objects, ParseException e) {
                if (e == null) {
                    for (User user : objects) {
                        Log.v(TAG, "we found him and his password is " + user.getPassword());
                    }
                } else {
                    Log.e(TAG, "Error finding such a user " + e.getMessage());

                }
            }
        });
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
        }
        else {

            //MAP activity:
            Intent mapIntent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(mapIntent);
        }

    }

}
