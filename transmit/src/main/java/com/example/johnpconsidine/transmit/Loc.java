package com.example.johnpconsidine.transmit;

import android.bluetooth.le.ScanRecord;
import android.os.ParcelUuid;

/**
 * Created by johnpconsidine on 4/6/16.
 */
public class Loc {

    private float latitude;
    private float longitude;





    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }


    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
