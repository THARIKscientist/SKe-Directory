package com.gangtok.nielit.skdirectory;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.gangtok.nielit.skdirectory.activity.CategoryActivity;

import com.gangtok.nielit.skdirectory.activity.EmpInfooActivity;
import com.gangtok.nielit.skdirectory.activity.EmployeeActivity;
import com.gangtok.nielit.skdirectory.activity.SearchEmployeeActivity;
import com.gangtok.nielit.skdirectory.adapter.ListEmployeeAdapter;
import com.gangtok.nielit.skdirectory.database.DatabaseHelper;
import com.gangtok.nielit.skdirectory.model.Employee;
import com.gangtok.nielit.skdirectory.supporting.JsonToDbParserActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<Employee> EmployeeListForJson;
    private DatabaseHelper mDBHelper;
    private JsonToDbParserActivity jsonToDbParserActivity;

    private ListView lvEmployess;
    private ListEmployeeAdapter adapter;
    private List<Employee> mEmployeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBHelper = new DatabaseHelper(this);
        callCopyDatabase();
     //   jsonToDbParserActivity.callTask();
        Button btnShowDept=(Button)findViewById(R.id.showDeptBtn);
        Button btnSearch=(Button)findViewById(R.id.btnSearch);
        ImageButton btnCallTask=(ImageButton)findViewById(R.id.button);

        lvEmployess = (ListView) findViewById(R.id.listview_employee);

        final EditText txtKeyVal=(EditText)findViewById(R.id.txtKeyEditText);
        final EditText txtDeptSearch=(EditText)findViewById(R.id.txtDeptEditText);
        btnShowDept.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view)
            {

                Intent i=new Intent(MainActivity.this,CategoryActivity.class);
                startActivity(i);

            }
        });

     /*   btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view)
            {
                String passValueToEmployee[] = new String[3];
                if(!( txtKeyVal.getText().toString().trim().equalsIgnoreCase("") && txtDeptSearch.getText().toString().trim().equalsIgnoreCase("") ))
                {
                    Intent i = new Intent(MainActivity.this, SearchEmployeeActivity.class);
                    passValueToEmployee[0] = txtKeyVal.getText().toString();
                    passValueToEmployee[1] = txtDeptSearch.getText().toString();
                    i.putExtra("valueFromSearch", passValueToEmployee);
                    Log.v("click = ", "done");
                    startActivity(i);
                }
            }
        });*/

        mDBHelper = new DatabaseHelper(this);
        btnSearch.setOnClickListener(new View.OnClickListener(){

            @Override
            public  void onClick(View view)
            {
                txtKeyVal.clearFocus();
                txtDeptSearch.clearFocus();
                String passValueToEmployee[] = new String[3];
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if(!( txtKeyVal.getText().toString().trim().equalsIgnoreCase("") && txtDeptSearch.getText().toString().trim().equalsIgnoreCase("") ))
                {
                 //   Intent i = new Intent(MainActivity.this, SearchEmployeeActivity.class);
                    passValueToEmployee[0] = txtKeyVal.getText().toString();
                    passValueToEmployee[1] = txtDeptSearch.getText().toString();
                    mEmployeeList = mDBHelper.getListEmployeeFromSearchBox(passValueToEmployee[0],passValueToEmployee[1]);
                    adapter = new ListEmployeeAdapter(MainActivity.this, mEmployeeList);
                    lvEmployess.setAdapter(adapter);
              //      i.putExtra("valueFromSearch", passValueToEmployee);
                    Log.v("click = ", "done");

                    lvEmployess.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
                        {
                            Employee word = mEmployeeList.get(position);

                            Intent i=new Intent(MainActivity.this,EmpInfooActivity.class);

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
               //     startActivity(i);
                }
            }
        });





        btnCallTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view)
            {

                 if(jsonToDbParserActivity.employeeList!=null)
                 {
                   if(mDBHelper.updateData(jsonToDbParserActivity.employeeList))
                   {
                         Log.v("This Activity = ","Database Success");
                   }
                   else
                    {
                         Log.v("This Activity = ","Fail");
                    }

          }

            }
        });


    }
    private void setupFloatingLabelError() {
        final TextInputLayout floatingKeyLabel = (TextInputLayout) findViewById(R.id.key_text_input_layout);
        floatingKeyLabel.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() > 0 && text.length() <= 4) {
                    floatingKeyLabel.setError(getString(R.string.key_required));
                    floatingKeyLabel.setErrorEnabled(true);
                } else {
                    floatingKeyLabel.setErrorEnabled(false);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final TextInputLayout floatingDepartmentLabel = (TextInputLayout) findViewById(R.id.department_text_input_layout);
        floatingDepartmentLabel.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() > 0 && text.length() <= 4) {
                    floatingDepartmentLabel.setError(getString(R.string.department_required));
                    floatingDepartmentLabel.setErrorEnabled(true);
                } else {
                    floatingDepartmentLabel.setErrorEnabled(false);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    protected void onStart() {
        jsonToDbParserActivity=new JsonToDbParserActivity();
        jsonToDbParserActivity.callTask();
        super.onStart();
    }

    @Override
    protected void onRestart() {
        jsonToDbParserActivity=new JsonToDbParserActivity();
        jsonToDbParserActivity.callTask();
        super.onRestart();
    }

    private  void callCopyDatabase()
    {
        mDBHelper = new DatabaseHelper(this);
        //Check exists database
        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if (false == database.exists()) {
            mDBHelper.getReadableDatabase();
            //Copy db
            if (copyDatabase(this)) {
                Toast.makeText(this, "Copy database succes", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }
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
