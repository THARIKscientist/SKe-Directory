package com.gangtok.nielit.skdirectory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.gangtok.nielit.skdirectory.MainActivity;
import com.gangtok.nielit.skdirectory.R;
import com.gangtok.nielit.skdirectory.model.Department;

import com.gangtok.nielit.skdirectory.model.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NgocTri on 11/7/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "EdirecDB_1_0_0.db";
    public static final String DBLOCATION = "/data/data/com.gangtok.nielit.skdirectory/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static  final String ID ="ID";
    public static  final String Emp_Name = "Emp_Name";
    public static  final String Designation_Name = "Designation_Name";
    public static  final String Department_ID="Department_ID";
    public static  final String PlaceOfPosting="PlaceOfPosting" ;
    public static  final String OfficeLandline="OfficeLandline";
    public static  final String OfficeMobile="OfficeMobile";
    public static  final String HomeLandline="HomeLandline";
    public static  final String HomeMobile="HomeMobile";
    public static  final String Fax="Fax";
    public static  final String EmailId="EmailId";
    public static  final String OfficeAddress="OfficeAddress";
    public static  final String EMP_image="EMP_image";
    public static  final String Flag="Flag";

    public static final String TABLE_NAME = "Employee";
    public String[] COL ={"ID", "Emp_Name","Designation_Name","Department_ID","PlaceOfPosting","OfficeLandline",
            "OfficeMobile","HomeLandline","HomeMobile","Fax","EmailId","OfficeAddress","EMP_image","Flag"};
    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if(mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if(mDatabase!=null) {
            mDatabase.close();
        }
    }
    public List<Department> getListDepartment(String Category_Type) {
        Department department = null;
        List<Department> departmentList = new ArrayList<>();
        openDatabase();
        //   String query = "INSERT INTO persons (name,address) VALUES('"+name+"', '"+add+"');";
        //      String query = "SELECT ID,Designation_Name FROM Designation WHERE Department_ID = "+DeptID+"";

        String query = "SELECT dept.ID,dept.Department_Name FROM Department as dept " +
                "INNER JOIN Category as cat on dept.Category_Id = cat.ID " +
                "WHERE  cat.CategoriesList = ('"+Category_Type+"') ";
        //     String query="SELECT _id,Designation FROM DesignationTB";
        Cursor cursor = mDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            department = new Department( cursor.getInt(0),cursor.getString(1));
            departmentList.add(department);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return departmentList;
    }




    public List<Employee> getListEmployee(int Department_ID) {
        Employee employee = null;
        List<Employee> employeeList = new ArrayList<>();
        openDatabase();
        //   String query = "INSERT INTO persons (name,address) VALUES('"+name+"', '"+add+"');";
        //      String query = "SELECT ID,Designation_Name FROM Designation WHERE Department_ID = "+DeptID+"";

        String query = "SELECT ID,Emp_Name,Designation_Name FROM Employee WHERE Department_ID = "+Department_ID+"  ";
        Log.v("Query = ",query);
        //     String query="SELECT _id,Designation FROM DesignationTB";
        Cursor cursor = mDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            employee = new Employee( cursor.getInt(0),cursor.getString(1),cursor.getString(2));
            employeeList.add(employee);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return employeeList;
    }

    public List<Employee> getListEmployeeFromSearchBox(String searchKey,String searchDept) {
        Employee employee = null;
        List<Employee> employeeList = new ArrayList<>();
        openDatabase();
        String query ="";
        if( !searchKey.trim().equalsIgnoreCase("") &&  (searchDept.trim().equalsIgnoreCase(""))  )
        {
            query = "SELECT Emp.ID,Emp.Emp_Name,Emp.Designation_Name,Emp.Department_ID,Cat.CategoriesList , Dept.Department_Name " +
                    "FROM [Employee] as Emp " +
                    "JOIN  [Department] as Dept  ON  Emp.Department_ID = Dept.ID " +
                    "JOIN  [Category] as  Cat ON  Dept.Category_ID  = Cat.ID " +
                    "WHERE  Emp.Designation_Name   LIKE '%"+searchKey+"%' OR  " +
                    " Dept.Department_Name  LIKE '%"+searchKey+"%' OR  " +
                    " Emp.Emp_Name           LIKE '%"+searchKey+"%' OR " +
                    " Emp.PlaceOfPosting     LIKE '%"+searchKey+"%' " +
                    "ORDER BY Emp.Emp_Name Asc";
        }
        else if( searchKey.trim().equalsIgnoreCase("") &&  (!searchDept.trim().equalsIgnoreCase(""))  )
        {
            query = "SELECT Emp.ID,Emp.Emp_Name,Emp.Designation_Name,Emp.Department_ID,Cat.CategoriesList ,Dept.Department_Name " +
                    "FROM [Employee] as Emp " +
                    "JOIN  [Department] as Dept  ON  Emp.Department_ID = Dept.ID " +
                    "JOIN  [Category] as  Cat ON  Dept.Category_ID  = Cat.ID " +
                    "WHERE  Cat.CategoriesList   LIKE '%"+searchDept+"%' OR   " +
                    " Dept.Department_Name    LIKE '%"+searchDept+"%' OR   " +
                    " Emp.Designation_Name   LIKE '%"+searchKey+"%'   " +
                    "ORDER BY Emp.Emp_Name Asc";
        }
        else if(!(searchDept.trim().equalsIgnoreCase("") && searchKey.trim().equalsIgnoreCase("") ))
        {
            query = "SELECT Emp.ID,Emp.Emp_Name,Emp.Designation_Name,Emp.Department_ID,Cat.CategoriesList ,Dept.Department_Name " +
                    "FROM [Employee] as Emp " +
                    "JOIN  [Department] as Dept  ON  Emp.Department_ID = Dept.ID " +
                    "JOIN  [Category] as Cat ON  Dept.Category_ID  = Cat.ID " +
                    "WHERE ( Cat.CategoriesList   LIKE '%"+searchDept+"%' OR  " +
                    " Dept.Department_Name    LIKE '%"+searchDept+"%') AND  " +
                    "( Emp.Designation_Name   LIKE '%"+searchKey+"%' OR  " +
                    " Emp.Emp_Name           LIKE '%"+searchKey+"%' OR " +
                    " Emp.PlaceOfPosting     LIKE '%"+searchKey+"%' )" +
                    "ORDER BY Emp.Emp_Name Asc";
        }

              Log.v("Query = ",query);
        //     String query="SELECT _id,Designation FROM DesignationTB";
        Cursor cursor = mDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            employee = new Employee( cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),cursor.getString(5));
            employeeList.add(employee);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return employeeList;
    }


   /* Employee(int id, String emp_Name,String designation_Name,int designation_ID,String place_of_posting,
             String office_landline,String office_mobile,String home_landline,
             String home_mobile,String fax,String email_id,
             String office_address,byte[] emp_Image,int flag)*/
    public List<Employee> getListEmployeeInfoWithImage(Integer value) {
        Employee employee = null;
        List<Employee> employeeList = new ArrayList<>();
        openDatabase();

        String query = "SELECT ID,Emp_Name,Designation_Name,Department_ID,PlaceOfPosting,OfficeLandline,OfficeMobile,HomeLandline,HomeMobile,Fax,EmailId,OfficeAddress,EMP_image,Flag  FROM Employee WHERE ID = "+value+"";
        Log.v("Query = ",query);
        //     String query="SELECT _id,Designation FROM DesignationTB";
        Cursor cursor = mDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            employee = new Employee( cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),
                    cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),
                    cursor.getString(11),cursor.getBlob(12),cursor.getInt(13));
            employeeList.add(employee);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return employeeList;
    }



  /*public boolean insertData(Employee  EmpList) {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put(COL[0],EmpList.getId());
      contentValues.put(COL[1],EmpList.getEmpName());
      contentValues.put(COL[2],EmpList.getDesignation_Name());
      contentValues.put(COL[3],EmpList.getplace_of_posting());
      contentValues.put(COL[4],EmpList.getEmail_id());
      contentValues.put(COL[5],EmpList.getOffice_address());
      contentValues.put(COL[6],EmpList.getOffice_landline());
      contentValues.put(COL[7],EmpList.getOffice_mobile());
      contentValues.put(COL[8],EmpList.getHome_landline());
      contentValues.put(COL[9],EmpList.getHome_mobile());
      contentValues.put(COL[10],EmpList.getFax());
      contentValues.put(COL[11],EmpList.getDesignationId());
      contentValues.put(COL[12],EmpList.getEmp_Image());
      long result = db.insert(TABLE_NAME,null ,contentValues);
      if(result == -1)
          return false;
      else
          return true;
  }*/
  public boolean updateDataRawQuery(List<Employee> EmployeeListAdapter)
  {
      openDatabase();
      /* (ID,Emp_Name," +
                    "Designation_Name,Designation_ID," +
                    "PlaceOfPosting," +
                    "OfficeLandline,OfficeMobile," +
                    "HomeLandline,HomeMobile," +
                    "Fax,EmailId," +
                    "OfficeAddress,EMP_image,Flag) " + */

      for(int i=0;i<EmployeeListAdapter.size();i++) {

          mDatabase.execSQL("UPDATE Employee SET Emp_Name = ' "+  EmployeeListAdapter.get(i).getEmpName()+"', " +
                                       " Designation_Name = ' "+  EmployeeListAdapter.get(i).getDesignation_Name()+"', " +
                                       " Department_ID    = ' "+  EmployeeListAdapter.get(i).getDesignationId()+"', " +
                                       " OfficeLandline   = ' "+  EmployeeListAdapter.get(i).getOffice_landline()+"' ,  " +
                                       " OfficeMobile     = ' "+  EmployeeListAdapter.get(i).getOffice_mobile()+"' , " +
                                       " HomeLandline     = ' "+  EmployeeListAdapter.get(i).getHome_landline()+"' ,  " +
                                       " HomeMobile       = ' "+  EmployeeListAdapter.get(i).getHome_mobile()+"' ,  " +
                                       " Fax              = ' "+  EmployeeListAdapter.get(i).getFax()+"' , " +
                                       " EmailId          = ' "+  EmployeeListAdapter.get(i).getEmail_id()+"' ,  " +
                                       " OfficeAddress    = ' "+  EmployeeListAdapter.get(i).getOffice_address()+"' , " +
                                       " EMP_image        = ' "+  EmployeeListAdapter.get(i).getEmp_Image()+"' ,  " +
                                       " Flag             = ' "+  EmployeeListAdapter.get(i).getFlag()+"' " +
                                       " Where ID         =   "+  EmployeeListAdapter.get(i).getId() );
      }


    // Log.v("This Activity = ", EmployeeListAdapter.get(0).getEmpName());
     closeDatabase();
     return true;
  }



    public  boolean updateData(List<Employee> EmployeeListAdapter) {

       openDatabase();
        for(int i=0;i<EmployeeListAdapter.size();i++)
        {
            ContentValues contentValues = new ContentValues();
            //   contentValues.put(COL[0], EmployeeListAdapter.get(i).getId());
            contentValues.put(COL[1], EmployeeListAdapter.get(i).getEmpName());
            contentValues.put(COL[2], EmployeeListAdapter.get(i).getDesignation_Name());
            contentValues.put(COL[3], EmployeeListAdapter.get(i).getDesignationId() );
            contentValues.put(COL[4], EmployeeListAdapter.get(i).getplace_of_posting());
            contentValues.put(COL[5], EmployeeListAdapter.get(i).getOffice_landline());
            contentValues.put(COL[6], EmployeeListAdapter.get(i).getOffice_mobile());
            contentValues.put(COL[7], EmployeeListAdapter.get(i).getHome_landline());
            contentValues.put(COL[8], EmployeeListAdapter.get(i).getHome_mobile());
            contentValues.put(COL[9], EmployeeListAdapter.get(i).getFax());
            contentValues.put(COL[10],EmployeeListAdapter.get(i).getEmail_id());
            contentValues.put(COL[11], EmployeeListAdapter.get(i).getOffice_address());
            contentValues.put(COL[12], EmployeeListAdapter.get(i).getEmp_Image());
            contentValues.put(COL[13], EmployeeListAdapter.get(i).getFlag());

            String[] IDVAL = new String[]{ Integer.toString(EmployeeListAdapter.get(i).getId())};

            mDatabase.update(TABLE_NAME, contentValues, "ID = ?",IDVAL);
        }

        closeDatabase();

        return true;
    }
}
