package com.gangtok.nielit.skdirectory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gangtok.nielit.skdirectory.R;
import com.gangtok.nielit.skdirectory.adapter.ListEmployeeAdapter;
import com.gangtok.nielit.skdirectory.database.DatabaseHelper;
import com.gangtok.nielit.skdirectory.model.Employee;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class SearchEmployeeActivity extends AppCompatActivity {
    private ListView lvProduct;
    private ListEmployeeAdapter adapter;
    private List<Employee> mEmployeeList;
    private DatabaseHelper mDBHelper;
    public ArrayList<Employee> words = new ArrayList<Employee>();
    String getValueFromIntentSearch[]=new String[2];
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        getValueFromIntentSearch=intent.getStringArrayExtra("valueFromSearch");

        Log.v("enter = ","done");
        setContentView(R.layout.activity_employee);

        lvProduct = (ListView) findViewById(R.id.listview_employee);
        mDBHelper = new DatabaseHelper(this);
        //getValueFromIntentDesignation[1] - Query Key
        mEmployeeList = mDBHelper.getListEmployeeFromSearchBox(getValueFromIntentSearch[0],getValueFromIntentSearch[1]);
        TextView textPath=(TextView) findViewById(R.id.txt_path);
        textPath.setText("");
        //Get product list in db when db exists. && Check From where Intent called -------------Temporary code

//----------------------------------------------------------------------------------------------------------
        //Init adapter
        adapter = new ListEmployeeAdapter(this, mEmployeeList);
        //Set adapter for listview
        lvProduct.setAdapter(adapter);

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Employee word = mEmployeeList.get(position);

                Intent i=new Intent(SearchEmployeeActivity.this,EmpInfooActivity.class);

             //   i.putStringArrayListExtra("EmployeeList");
                String passValueToEmployeeInfo[]=new String[3];
                passValueToEmployeeInfo[0]=Integer.toString(word.getId());
                passValueToEmployeeInfo[1]=word.getDepartment_Name();
                passValueToEmployeeInfo[2]=word.getPostingName();
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
