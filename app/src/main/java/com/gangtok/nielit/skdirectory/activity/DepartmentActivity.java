package com.gangtok.nielit.skdirectory.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gangtok.nielit.skdirectory.R;
import com.gangtok.nielit.skdirectory.adapter.ListDepartmentAdapter;
import com.gangtok.nielit.skdirectory.database.DatabaseHelper;
import com.gangtok.nielit.skdirectory.model.Department;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class DepartmentActivity extends AppCompatActivity {
    private ListView lvEmployees;
    private ListDepartmentAdapter adapter;
    private List<Department> mDepartmentList;
    private DatabaseHelper mDBHelper;
    public String getValueFromIntentCategory; //Category Name

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        getValueFromIntentCategory = intent.getStringExtra("CategoryName");


        setContentView(R.layout.activity_department);
        lvEmployees = (ListView) findViewById(R.id.listview_department);
        mDBHelper = new DatabaseHelper(this);

        TextView textPath=(TextView) findViewById(R.id.txt_path);
        textPath.setText(getValueFromIntentCategory);

        //getValueFromIntent -
        //Get product list in db when db exists
        mDepartmentList = mDBHelper.getListDepartment(getValueFromIntentCategory);
        //Init adapter
        adapter = new ListDepartmentAdapter(this, mDepartmentList);
        //Set adapter for listview
        lvEmployees.setAdapter(adapter);

        lvEmployees.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Department word = mDepartmentList.get(position);
                Intent i=new Intent(DepartmentActivity.this,EmployeeActivity.class);
                String passValueToEmployee[]=new String[4];
                passValueToEmployee[0]=getValueFromIntentCategory;//Category Name
                passValueToEmployee[1]=word.getName().toString();
                passValueToEmployee[2]="FromDepartment";
                passValueToEmployee[3]= String.valueOf(word.getId());
                i.putExtra("valueFromDepartment",passValueToEmployee);
                Log.v("click = ","done");
                startActivity(i);


            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity", "DB copied");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
