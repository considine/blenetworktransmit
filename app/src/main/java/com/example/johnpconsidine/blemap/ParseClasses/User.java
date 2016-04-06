package com.example.johnpconsidine.blemap.ParseClasses;

import com.example.johnpconsidine.transmit.Utils;
import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by johnpconsidine on 4/1/16.
 */
@ParseClassName(Utils.USER_DATA)
public class User extends ParseObject{
    //Things:
    private String password;
    private String username;

    public String getPassword() {
        return getString(Utils.USER_DATA_PASSWORD_HASH);
    }
}
