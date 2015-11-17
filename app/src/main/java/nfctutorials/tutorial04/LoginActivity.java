package nfctutorials.tutorial04;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Bind Widget
        bindWidget();

        //Connected Database
        objManageTABLE = new ManageTABLE(this);

        //Tester Add Value
        //testerAddValue();

        //Delete All Data
          deleteAllData();

        //Synchronize JSON to SQLite
          synJSONtoSQLite();

    }   // Main Method

    public void clickLogin(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Zero
        if (userString.equals("") || passwordString.equals("") ) {

            //Have Space

        } else {

            //No Space

        }

    }


    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.edtLoginUser);
        passwordEditText = (EditText) findViewById(R.id.edtLoginPassword);
    }

    private void synJSONtoSQLite() {

        //Setup Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);

        int intTimes = 1;
        while (intTimes <= 3) {

            //1. Create InputStream
            InputStream objInputStream = null;
            String strJSON = null;
            HttpPost objHttpPost = null;
            String strUrluserTABLE = "http://swiftcodingthai.com/golf/php_get_data_golf.php";
            String strUrldeviceTABLE = "http://swiftcodingthai.com/golf/php_get_data_device.php";
            String strUrlassignTABLE = "http://swiftcodingthai.com/golf/php_get_assign_golf.php";

            try {

                HttpClient objHttpClient = new DefaultHttpClient();

                switch (intTimes) {
                    case 1:
                        objHttpPost = new HttpPost(strUrluserTABLE);
                        break;
                    case 2:
                        objHttpPost = new HttpPost(strUrldeviceTABLE);
                        break;
                    case 3:
                        objHttpPost = new HttpPost(strUrlassignTABLE);
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
                            String strFirstName = object.getString("Firstname");
                            String strLastName = object.getString("Lastname");
                            String strUser = object.getString("User");
                            String strPassword = object.getString("Password");
                            String strUserType = object.getString("UserType");
                            objManageTABLE.addUser(strFirstName, strLastName, strUser, strPassword, strUserType);

                            break;
                        case 2:

                            //for deviceTABLE
                            String strTagNFC = object.getString("tagNFC");
                            String strName = object.getString("Name");
                            String strLocation = object.getString("Location");
                            String strStatus = object.getString("Status");
                            objManageTABLE.addDevice(strTagNFC, strName, strLocation, strStatus);

                            break;
                        case 3:
                            String strUserID = object.getString("User_id");
                            String strDeviceID = object.getString("device_id");
                            String strAssigeDate = object.getString("Assigned_date");
                            String strCheckStatus = object.getString("checkStatus");
                            String strComment = object.getString("comment");
                            objManageTABLE.addAssign(strUserID, strDeviceID, strAssigeDate, strCheckStatus, strComment);

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
        objSqLiteDatabase.delete("assignTABLE", null, null);
    }

    private void testerAddValue() {

        objManageTABLE.addUser("testFirst", "testLast", "testUser", "testPassword", "testUserType");
        objManageTABLE.addDevice("testTAG", "testName", "testLocation", "testStatus");
        objManageTABLE.addAssign("testUserID", "testDeviceID", "testAssignDate", "testCheckStatus", "testComment");

    }

}   // Main Class
