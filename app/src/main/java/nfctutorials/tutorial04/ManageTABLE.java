package nfctutorials.tutorial04;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 11/10/15 AD.
 */
public class ManageTABLE {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String TABLE_USER = "userTABLE";
    public static final String TABLE_DEVICE = "deviceTABLE";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER = "User";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_OFFICER = "Officer";
    public static final String COLUMN_PERMISSION = "Permission";
    public static final String COLUMN_TAGNFC = "tagNFC";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_LOCATION = "Location";
    public static final String COLUMN_STATUS = "Status";


    public ManageTABLE(Context context) {

        //Create & Connected
        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();

    }   // Constructor

    //Add New Value to SQLite
    public long addUser(String strUser, String strPassword, String strOfficer, String strPermission) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_USER, strUser);
        objContentValues.put(COLUMN_PASSWORD, strPassword);
        objContentValues.put(COLUMN_OFFICER, strOfficer);
        objContentValues.put(COLUMN_PERMISSION, strPermission);

        return writeSqLiteDatabase.insert(TABLE_USER, null, objContentValues);
    }

    public long addDevice(String strTAGNFC, String strName, String strLocation, String strStatus) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_TAGNFC, strTAGNFC);
        objContentValues.put(COLUMN_NAME, strName);
        objContentValues.put(COLUMN_LOCATION, strLocation);
        objContentValues.put(COLUMN_STATUS, strStatus);

        return writeSqLiteDatabase.insert(TABLE_DEVICE, null, objContentValues);
    }


}   // Main Class
