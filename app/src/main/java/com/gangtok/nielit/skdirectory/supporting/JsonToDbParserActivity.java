package com.gangtok.nielit.skdirectory.supporting;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.gangtok.nielit.skdirectory.MainActivity;
import com.gangtok.nielit.skdirectory.R;
import com.gangtok.nielit.skdirectory.database.DatabaseHelper;
import com.gangtok.nielit.skdirectory.model.Employee;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NIELIT on 21-11-2016.
 */

public class JsonToDbParserActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Error";
    private static final String USGS_REQUEST_URL = "https://script.google.com/macros/s/AKfycby58TlYl9cNghnYWNU-9fRbCBpvVR4AuLqMHeaHfdY3tHRJzx4/exec";

    private DatabaseHelper  mDBHelper = new DatabaseHelper(this);
    public List<Employee> employeeList;
    public MainActivity mainActivity=new MainActivity();
    Button btnUpdate;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * Returns new URL object from the given string URL.
     */

    public void callTask() {

        NielitAsyncTask task = new NielitAsyncTask();
        task.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mDBHelper = new DatabaseHelper(this);


    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("JsonToDbParser Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class NielitAsyncTask extends AsyncTask<URL, Void, List<Employee>> {


        @Override
        protected List<Employee> doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(USGS_REQUEST_URL);
            // Perform HTTP request to the URL and receive a JSON response back


            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }
            // Extract relevant fields from the JSON response and create an {@link Event} object
            List<Employee> EmployeeData = extractFeatureFromJsonWithDB(jsonResponse);

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
           return EmployeeData;
        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link NielitAsyncTask}).
         */
        @Override
        protected void onPostExecute(List<Employee> EmployeeData) {
            if(EmployeeData!= null)
            {

            }

        }


    }

    private List<Employee> extractFeatureFromJsonWithDB(String nielitJSON) {
        if (TextUtils.isEmpty(nielitJSON)) {
            return null;
        }
        try {
/*    "ID": 11,
    "Emp_Name": "Hon'ble Mr. Justice Narendra Kr. Jain ",
    "Designation_Name": "Acting Chief Justice ,High Court of Sikkim  ",
    "Designation_ID": 9,
    "PlaceOfPosting": " Gangtok",
    "OfficeLandline": 3592205610,
    "OfficeMobile": "",
    "HomeLandline": "",
    "HomeMobile": "",
    "Fax": "",
    "EmailId": "",
    "OfficeAddress": "NH-31A, Sungava,, Sungava, Gangtok, Sikkim 737101",
    "EMP_image": "\u00ff\u00d8\u00ff\u00e1\u0010\u00b0Exif",
    "Flag": 0*/
            Employee employee = null;
       //     JSONObject baseJsonResponse = new JSONObject(nielitJSON);
            JSONArray objectsArray = new JSONArray(nielitJSON);
     //       JSONArray objectsArray = baseJsonResponse.getJSONArray("rows");
            employeeList = new ArrayList<>();
            // If there are results in the features array
            if (objectsArray.length() > 0) {
                // Extract out the first feature (which is an Employee)
                for (int i = 0; i < objectsArray.length(); i++) {

                //    JSONArray empList = objectsArray.getJSONArray(i);
                    JSONObject empList = objectsArray.getJSONObject(i);
                  /*(int id, String emp_Name,String designation_Name,int designation_ID,String place_of_posting,
                    String office_landline,String office_mobile,String home_landline,
                    String home_mobile,String fax,String email_id,
                    String office_address,byte[] emp_Image,int flag)*/

                /*    employee = new Employee(empList.getInt(0), empList.getString(1), empList.getString(2), empList.getInt(3), empList.getString(4),
                            empList.getString(5), empList.getString(6), empList.getString(7), empList.getString(8), empList.getString(9), empList.getString(10),
                            empList.getString(11), serialize(empList.get(12)), empList.getInt(13));*/


                    employee = new Employee(empList.getInt("ID"),
                            empList.getString("Emp_Name"), empList.getString("Designation_Name"),
                            empList.getInt("Department_ID"), empList.getString("PlaceOfPosting"),
                            empList.getString("OfficeLandline"), empList.getString("OfficeMobile"),
                            empList.getString("HomeLandline"), empList.getString("HomeMobile"),
                            empList.getString("Fax"), empList.getString("EmailId"),
                            empList.getString("OfficeAddress"), serialize(empList.get("EMP_image")),
                            empList.getInt("Flag"));

                    employeeList.add(employee);

                }
                return employeeList;
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the  JSON results", e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        if (url == null) {
            return jsonResponse;
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }

        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    public void updateDatabase(List<Employee> EmployeeData) {
        mDBHelper = new DatabaseHelper(this);
        mDBHelper.updateDataRawQuery(EmployeeData);
    }
}
