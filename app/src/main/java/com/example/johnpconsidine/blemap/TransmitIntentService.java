package com.example.johnpconsidine.blemap;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.johnpconsidine.transmit.TransmitPlaces;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnpconsidine on 4/3/16.
 */
public class TransmitIntentService extends IntentService {
    private static final String TAG = TransmitIntentService.class.getSimpleName();
    public static final String PARAM_OUT_MSG = "omsg";
    @Override
    protected void onHandleIntent(Intent intent) {
        //Create a thread for receiving bluetooth pins.
        Log.v(TAG, "Calling network pins");
        Application.setPlaces(TransmitPlaces.transmit());
        Log.v(TAG, "The size in application is " + Application.getmPlaces().size());
        String resultTxt = "BTReception";

        //broadcast that all the pins have been received so that the mapActivity can now use them
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(MapActivity.MyReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(PARAM_OUT_MSG, resultTxt);
        sendBroadcast(broadcastIntent);

        //Log.v(TAG, "Transmitted!" + placeParseObjects.size());

        //need to get the latitudes and longitudes

    }

    @Override
    public IBinder onBind(Intent intent) {

        Log.v(TAG, "Calling network pins");
        List<ParseObject> placeParseObjects = new ArrayList<>();
        return super.onBind(intent);
    }

    public TransmitIntentService () {
        super("TransmitIntentService");

        setIntentRedelivery(true); // if process is killed restart transmit
    }

    public void transmit () {
        //get the parse objects
    }


}
