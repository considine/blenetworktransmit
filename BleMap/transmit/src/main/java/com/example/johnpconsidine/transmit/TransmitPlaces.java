package com.example.johnpconsidine.transmit;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnpconsidine on 4/1/16.
 */
public class TransmitPlaces {
    private static final String TAG = TransmitPlaces.class.getSimpleName();


    public static List<ParseObject> transmit () {
        //MUST HAVE PARSE INITIALIZED IN MAIN CODE
        //Make parsequery
        Log.v(TAG, "HERE");
        List<ParseObject> mPlaces = new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(Utils.PLACE_OBJECT);

        try {
            mPlaces = query.find();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mPlaces;
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//
//                if (e == null) {
//                    //success!
//                    Log.v(TAG, "CALLED FIRST");
//                    for (ParseObject object : objects) {
//
//                        //  Log.v(TAG, )
//                        mPlaces.add(object);
//                        Log.v(TAG, "CALLED ");
//                        Log.v(TAG, mPlaces.size() + " IS THE SIZE ");
//                    }
//                } else {
//                    Log.e(TAG, "error transmitting places " + e.getMessage());
//                }
//            }
//        });

    }
}
