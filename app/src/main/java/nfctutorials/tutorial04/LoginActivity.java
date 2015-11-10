package nfctutorials.tutorial04;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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

        //Synchronize JSON to SQLite
        synJSONtoSQLite();

    }   // Main Method

    private void synJSONtoSQLite() {

        //Setup Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);

        int intTimes = 1;
        while (intTimes <= 2) {

            //1. Create InputStream
            InputStream objInputStream = null;
            String strJSON = null;
            HttpPost objHttpPost = null;
            String strUrluserTABLE = "http://swiftcodingthai.com/golf/php_get_data_golf.php";
            String strUrldeviceTABLE = "http://swiftcodingthai.com/golf/php_get_data_device.php";

            try {

                HttpClient objHttpClient = new DefaultHttpClient();

                switch (intTimes) {
                    case 1:
                        objHttpPost = new HttpPost(strUrluserTABLE);
                        break;
                    case 2:
                        objHttpPost = new HttpPost(strUrldeviceTABLE);
                        break;
                }

                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();

            } catch (Exception e) {
                Log.d("Suthep", "InputStream ==> " + e.toString());
            }

            //2. Create JSON String
            try {

                BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
                String strLine = null;
                StringBuilder objStringBuilder = new StringBuilder();

                while ((strLine = objBufferedReader.readLine()) != null) {
                    objStringBuilder.append(strLine);
                }

                objInputStream.close();
                strJSON = objStringBuilder.toString();


            } catch (Exception e) {
                Log.d("Suthep", "JSON String ==> " + e.toString());
            }


            //3. Update to SQLite
            try {

                JSONArray objJsonArray = new JSONArray(strJSON);

                for (int i = 0; i < objJsonArray.length(); i++) {

                    JSONObject object = objJsonArray.getJSONObject(i);

                    switch (intTimes) {
                        case 1:

                            //For userTABLE
                            String strUser = object.getString("User");
                            String strPassword = object.getString("Password");
                            String strOfficer = object.getString("Officer");
                            String strPermission = object.getString("Permission");
                            objManageTABLE.addUser(strUser, strPassword, strOfficer, strPermission);

                            break;
                        case 2:

                            //For deviceTABLE
                            String strTAGnfc = object.getString("tagNFC");
                            String strName = object.getString("Name");
                            String strLocation = object.getString("Location");
                            String strStatus = object.getString("Status");
                            String strDate = object.getString("Date");
                            String strComment = object.getString("Comment");
                            objManageTABLE.addDevice(strTAGnfc, strName, strLocation, strStatus, strDate, strComment);

                            break;
                    }

                } //for

            } catch (Exception e) {
                Log.d("Suthep", "Update ==> " + e.toString());
            }




            intTimes += 1;
        }   // while


    }   // synJSONtoSQLite

    private void deleteAllData() {
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase("golf.db", MODE_PRIVATE, null);
        objSqLiteDatabase.delete("userTABLE", null, null);
        objSqLiteDatabase.delete("deviceTABLE", null, null);
    }

    private void testerAddValue() {

        objManageTABLE.addUser("testUser", "testPass", "testOfficer", "testPermission");
        objManageTABLE.addDevice("testTAG", "testName", "testLocation", "testStatus", "testDate", "testComment");

    }

}   // Main Class
