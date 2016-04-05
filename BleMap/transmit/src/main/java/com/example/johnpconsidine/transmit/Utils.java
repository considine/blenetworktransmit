package com.example.johnpconsidine.transmit;

/**
 * Created by johnpconsidine on 4/1/16.
 */
public class Utils {

        /* Strings used as fields for the PlaceObject data model
        * Use this instead of typing in quotes to prevent errors!*/
        public static final String PLACE_OBJECT = "PlaceObject"; //Name of data model
        public static final String PLACE_OBJECT_GROUP = "group"; //Group of the event
        public static final String PLACE_OBJECT_ICON = "icon"; //icon of the event
        public static final String PLACE_OBJECT_LOCATION = "location"; //ParseGeoPoint containing the coordinates of the event
        public static final String PLACE_OBJECT_NOTES = "notes"; //Notes typed by user
        public static final String PLACE_OBJECT_MINUTES = "minutes"; //Number of minutes to exist specified by creator
        public static final String PLACE_OBJECT_USERNAME = "username"; // Username of the creator
        public static final String PLACE_OBJECT_USERID = "userid"; // Userid of the creator
        public static final String PLACE_OBJECT_VOTES = "votes"; // Number of votes of the event
        public static final String PLACE_OBJECT_VOTERS_LIST = "votersList"; // List with usernames of who voted
        public static final String PLACE_OBJECT_EXPIRED = "expired"; // Boolean if it has expired or not
        /* Strings used as fields for the MessageData data model
        * Use this instead of typing in quotes to prevent errors!*/
        public static final String MESSAGE_DATA = "MessageData";//Name of Message Data model
        public static final String MESSAGE_DATA_SENDER = "sender"; //String with username of sender
        public static final String MESSAGE_DATA_RECEIVER = "receiver"; //String with username of receiver
        public static final String MESSAGE_DATA_TEXT = "text"; //String with the message
        public static final String MESSAGE_DATA_READ = "read"; //Boolean if message was read or not
        /* Strings used as fields for the UserData data model
        * Use this instead of typing in quotes to prevent errors!*/
        public static final String USER_DATA = "UserData"; // Name of user data model
        public static final String USER_DATA_USERNAME = "username"; // String with username
        public static final String USER_DATA_PASSWORD_HASH = "passwordhash"; // String with password hash
        public static final String USER_DATA_USER_ID = "id"; // "String with ID generated by parse
        /* Strings used as fields for the editor
        * Use this instead of typing in quotes to prevent errors!*/
        public static final String EDITOR_PREVIOUS_USERNAME = "PreviousUsername"; // String with username of last logged in user
        public static final String EDITOR_PREVIOUS_PASSWORD_HASH = "PreviousPasswordHash"; // String with password hash of last logged in user
        public static final String EDITOR_PREVIOUS_USER_ID = "PreviousUserID"; // String of ID of last logged in user



}
