package com.gangtok.nielit.skdirectory;
/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public GoogleMap m_map;
    boolean mapReady=false;
    String URLPublic="http://maps.google.com/maps/api/geocode/json?address=mumbai&sensor=false";
    public Marker mMarker;
    public String value[]=new String[3] ;
    public MarkerOptions markerAddress;

    /*Cultural Heritage Dept 6
 "lat" : 27.3350083,
 "lng" : 88.61430109999999*/

    /*Animal Husbandary - 4
    *  "lat" : 27.3198924,
               "lng" : 88.6066031*/

/*CMO 3   "lat" : 27.3349606,
 "lng" : 88.6144674*/

/*Doordharsan - 8
* "lat" : 27.338417,
               "lng" : 88.618725*/
   /* static final CameraPosition SEATTLE = CameraPosition.builder()
            .target(new LatLng(47.6204,-122.3491))
            .zoom(17)
            .bearing(0)
            .tilt(45)
            .build();

    static final CameraPosition DUBLIN = CameraPosition.builder()
            .target(new LatLng(53.3478,-6.2597))
            .zoom(17)
            .bearing(90)
            .tilt(45)
            .build();


    static final CameraPosition TOKYO = CameraPosition.builder()
            .target(new LatLng(35.6895,139.6917))
            .zoom(17)
            .bearing(90)
            .tilt(45)
            .build();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        value=intent.getStringArrayExtra("LocationPlace");
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map)
    {
        mapReady=true;
        m_map = map;
        if(mapReady)
        {
            m_map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            //   EditText edit = (EditText)findViewById(R.id.editText2);
        //    URLPublic = "http://maps.google.com/maps/api/geocode/json?address="+ value[0]+"&sensor=false";
        //    DataLongOperationAsynchTask task = new DataLongOperationAsynchTask();
        //    task.execute();
            double lat,lng;
            lat= 27.3349606;
            lng= 88.6144674;
            if(Integer.parseInt(value[2])==3)
            {
                lat= 27.3349606;
                lng= 88.6144674;
                value[1]="Cheif Minister Office";
            }
            else if(Integer.parseInt(value[2])==4)
            {
                lat= 27.3198924;
                lng= 88.6066031;
                value[1]="ANIMAL HUSBANDRY L.F & V S DEPARTMENT";
            }
            else if(Integer.parseInt(value[2])==6)
            {
                lat= 27.3350083;
                lng= 88.61430109999999;
                value[1]="CULTURAL AFFAIRS & HERITAGE DEPARTMENT";
            }
            else if(Integer.parseInt(value[2])==8)
            {
                lat= 27.338417;
                lng= 88.618725;
                value[1]="DOORDARSHAN KENDRA";
            }
            LatLng placeLocation = new LatLng(lat,lng);
            CameraPosition target = CameraPosition.builder().target(placeLocation).zoom(17).build();
            m_map.moveCamera(CameraUpdateFactory.newCameraPosition(target));

            markerAddress=new MarkerOptions().position(placeLocation).title(value[1]);
            m_map.addMarker(markerAddress);
        }

    }

   private void flyTo(CameraPosition target)
    {
        m_map.animateCamera(CameraUpdateFactory.newCameraPosition(target), 10000, null);
    }

    private class DataLongOperationAsynchTask extends AsyncTask<String, Void, String[]> {
        ProgressDialog dialog = new ProgressDialog(MapActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String... params) {
            String response;
            try {
                response = getLatLongByURL(URLPublic);
                Log.d("response",""+response);
                return new String[]{response};
            } catch (Exception e) {
                return new String[]{"error"};
            }
        }

        @Override
        protected void onPostExecute(String... result) {
            try {

                JSONObject jsonObject = new JSONObject(result[0]);

                double lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lng");

                double lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lat");
                CameraPosition landingPlace = CameraPosition.builder()
                        .target(new LatLng(lat,lng))
                        .zoom(17)
                        .bearing(0)
                        .tilt(45)
                        .build();
             //   flyTo(landingPlace);
                Log.d("latitude", "" + lat);
                Log.d("longitude", "" + lng);

                LatLng placeLocation = new LatLng(lat,lng);
                CameraPosition target = CameraPosition.builder().target(placeLocation).zoom(17).build();
                m_map.moveCamera(CameraUpdateFactory.newCameraPosition(target));

                markerAddress=new MarkerOptions().position(placeLocation).title(value[1]);
                m_map.addMarker(markerAddress);




            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }


    public String getLatLongByURL(String requestURL) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
