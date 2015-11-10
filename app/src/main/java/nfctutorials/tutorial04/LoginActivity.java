package nfctutorials.tutorial04;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class LoginActivity extends ActionBarActivity {

    //Explicit
    private ManageTABLE objManageTABLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Connected Database
        objManageTABLE = new ManageTABLE(this);

        //Tester Add Value
        //testerAddValue();

        //Delete All Data
        deleteAllData();

    }   // Main Method

    private void deleteAllData() {
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase("golf.db", MODE_PRIVATE, null);
        objSqLiteDatabase.delete("userTABLE", null, null);
        objSqLiteDatabase.delete("deviceTABLE", null, null);
    }

    private void testerAddValue() {

        objManageTABLE.addUser("testUser", "testPass", "testOfficer", "testPermission");
        objManageTABLE.addDevice("testTAG", "testName", "testLocation", "testStatus");

    }

}   // Main Class
