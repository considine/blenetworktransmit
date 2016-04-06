package com.example.johnpconsidine.blemap;

import com.example.johnpconsidine.blemap.ParseClasses.Places;
import com.example.johnpconsidine.blemap.ParseClasses.User;
import com.example.johnpconsidine.transmit.Loc;
import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnpconsidine on 4/1/16.
 */
public class Application extends android.app.Application {

    public static List<Loc> getmPlaces() {
        return mPlaces;
    }

    private static List<Loc> mPlaces;
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Places.class);
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                        .applicationId("lqmx2GmYTOn8of5IM0LrrZ8bYT0ehDvzHTSdGLGA")
                        .clientKey(null)
                        .server("http://notredame.herokuapp.com/parse/") // The trailing slash is important.
                        .build()
        );
        mPlaces = new ArrayList<>();
    }

    public static void setPlaces(List<Loc> places) {
        mPlaces = places;
    }
    public static Loc getPlace (int position) {
        return mPlaces.get(position);
    }

}
