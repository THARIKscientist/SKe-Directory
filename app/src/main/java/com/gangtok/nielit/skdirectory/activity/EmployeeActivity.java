package com.gangtok.nielit.skdirectory.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gangtok.nielit.skdirectory.R;
import com.gangtok.nielit.skdirectory.adapter.ListEmployeeAdapter;
import com.gangtok.nielit.skdirectory.database.DatabaseHelper;


import com.gangtok.nielit.skdirectory.model.Employee;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {
    private ListView lvEmployees;
    private ListEmployeeAdapter adapter;
    private List<Employee> mEmployeeList;
    private DatabaseHelper mDBHelper;
    public ArrayList<Employee> words = new ArrayList<Employee>();
    String getValueFromIntentDepartment[]=new String[4];
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        getValueFromIntentDepartment=intent.getStringArrayExtra("valueFromDepartment");

        Log.v("enter = ","done");
        setContentView(R.layout.activity_employee);
        TextView textPath=(TextView) findViewById(R.id.txt_path);
        textPath.setText(getValueFromIntentDepartment[0] +" > " +getValueFromIntentDepartment[1]);
        // getValueFromIntentDesignation[1] - > Designation Name
        lvEmployees = (ListView) findViewById(R.id.listview_employee);
        mDBHelper = new DatabaseHelper(this);

        mEmployeeList = mDBHelper.getListEmployee(Integer.parseInt(getValueFromIntentDepartment[3]));

        //Get product list in db when db exists. && Check From where Intent called -------------Temporary code

//----------------------------------------------------------------------------------------------------------
        //Init adapter
        adapter = new ListEmployeeAdapter(this, mEmployeeList);
        //Set adapter for listview
        lvEmployees.setAdapter(adapter);

        lvEmployees.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Employee word = mEmployeeList.get(position);

                Intent i=new Intent(EmployeeActivity.this,EmpInfooActivity.class);
             //   i.putStringArrayListExtra("EmployeeList");
                String passValueToEmployeeInfo[]=new String[3];
                passValueToEmployeeInfo[0]=Integer.toString(word.getId());
                passValueToEmployeeInfo[1]=getValueFromIntentDepartment[0];
                passValueToEmployeeInfo[2]=getValueFromIntentDepartment[1];
                i.putExtra("valueFromEmployee",passValueToEmployeeInfo);
          //      i.putExtra("Employee",word.getId());
                Log.v("click = ","done");
                startActivity(i);


            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }






}
