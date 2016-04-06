package com.example.johnpconsidine.transmit;

import android.os.ParcelUuid;

/**
 * Created by johnpconsidine on 4/6/16.
 */
public class Loc {

    /* Full Bluetooth UUID that defines the Location Service */
    public static final ParcelUuid LOC_SERVICE = ParcelUuid.fromString("c57b1613-da40-4888-91e2-a6980d733a1b");

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    float latitude;
    float longitude;

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
