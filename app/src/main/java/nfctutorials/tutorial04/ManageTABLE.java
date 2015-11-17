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
    public static final String TABLE_ASSIGNED = "assignTABLE";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRSTNAME = "Firstname";
    public static final String COLUMN_LASTNAME = "Lastname";
    public static final String COLUMN_USER = "User";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_USERTYPE = "UserType";
    public static final String COLUMN_TAGNFC = "tagNFC";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_LOCATION = "Location";
    public static final String COLUMN_STATUS = "Status";
    public static final String COLUMN_USERID = "User_id";
    public static final String COLUMN_DEVICEID = "device_id";
    public static final String COLUMN_ASSIGNEDDATE = "Assigned_date";
    public static final String COLUMN_CHECKSTATUS = "checkStatus";
    public static final String COLUMN_COMMENT = "Comment";


    public ManageTABLE(Context context) {

        //Create & Connected
        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();

    }   // Constructor

    //Add New Value to SQLite
    public long addUser(String strFirstName, String strLastName, String strUser, String strPassword, String strUserType) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_FIRSTNAME, strFirstName);
        objContentValues.put(COLUMN_LASTNAME, strLastName);
        objContentValues.put(COLUMN_USER, strUser);
        objContentValues.put(COLUMN_PASSWORD, strPassword);
        objContentValues.put(COLUMN_USERTYPE, strUserType);

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

    public long addAssign(String strUserID, String strDeviceID, String strAssignDate, String strCheckStatus, String strComment) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_USERID, strUserID);
        objContentValues.put(COLUMN_DEVICEID, strDeviceID);
        objContentValues.put(COLUMN_ASSIGNEDDATE, strAssignDate);
        objContentValues.put(COLUMN_CHECKSTATUS, strCheckStatus);
        objContentValues.put(COLUMN_COMMENT, strComment);

        return writeSqLiteDatabase.insert(TABLE_ASSIGNED, null, objContentValues);
    }


}   // Main Class
