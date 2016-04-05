package com.example.johnpconsidine.blemap.ParseClasses;

import com.example.johnpconsidine.transmit.Utils;
import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by johnpconsidine on 4/1/16.
 */
@ParseClassName(Utils.PLACE_OBJECT)
public class Places extends ParseObject {
    public Places (ParseObject object) {
        this.objectId = object.getObjectId();
        this.location = object.getParseGeoPoint(Utils.PLACE_OBJECT_LOCATION);
        this.icon = object.getString(Utils.PLACE_OBJECT_ICON);
        this.notes = object.getString(Utils.PLACE_OBJECT_NOTES);
        this.minutes = object.getInt(Utils.PLACE_OBJECT_MINUTES);
    }
    public  Places () {

    }
    private String objectId;
    private ParseGeoPoint location;
    private String createdAt;
    private String icon;
    private String notes;
    private Boolean expired;
    private String group;
    private int minutes;



    public String getIcon() {
        return getString(Utils.PLACE_OBJECT_ICON);
    }

    public String getNotes() {
        return getString(Utils.PLACE_OBJECT_NOTES);
    }

    public Boolean getExpired() {
        return getBoolean(Utils.PLACE_OBJECT_EXPIRED);
    }

    public String getGroup() {
        return getString(Utils.PLACE_OBJECT_GROUP);
    }

    public int getMinutes() {
        return getInt(Utils.PLACE_OBJECT_MINUTES);
    }
    public String getUsername() { return getString(Utils.USER_DATA_USERNAME);}


}
