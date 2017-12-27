package com.gangtok.nielit.skdirectory.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gangtok.nielit.skdirectory.MapActivity;
import com.gangtok.nielit.skdirectory.R;
import com.gangtok.nielit.skdirectory.database.DatabaseHelper;
import com.gangtok.nielit.skdirectory.model.Employee;

import java.util.List;

public class EmpInfooActivity extends AppCompatActivity
{

    private DatabaseHelper mDBHelper;
    private List<Employee> mEmployeeList;
    private ImageButton callButton,mapButton;
    private byte[] imgUserVal;
    Employee word;
    String getValueFromIntentEmployee[]=new String[2];
    public static final int    MY_PERMISSIONS_REQUEST_CALL_PHONE=0;
    // private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_infoo_description);
        Intent intent = getIntent();

        getValueFromIntentEmployee = intent.getStringArrayExtra("valueFromEmployee");

        EmpInfooActivity.PhoneCallListener phoneListener = new EmpInfooActivity.PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        mDBHelper = new DatabaseHelper(this);

        mEmployeeList = mDBHelper.getListEmployeeInfoWithImage(Integer.parseInt(getValueFromIntentEmployee[0]));
        word = mEmployeeList.get(0);

        AppCompatImageView imgUser = (AppCompatImageView) findViewById(R.id.imageView);
        imgUserVal = word.getEmp_Image();

        Bitmap bmp = BitmapFactory.decodeByteArray(imgUserVal, 0, imgUserVal.length);
        imgUser.setImageBitmap(bmp);

        TextView textName = (TextView) findViewById(R.id.txt_employeename);
        TextView textDesignation = (TextView) findViewById(R.id.designationText);
        TextView textPlaceofPosting = (TextView) findViewById(R.id.placeOfPostingText);
        TextView textOfficeLandline = (TextView) findViewById(R.id.officeLandlineText);
        TextView textOfficeMobile = (TextView) findViewById(R.id.officeMobileText);
        TextView textResidenceLandline = (TextView) findViewById(R.id.residenceLandlineText);
        TextView textResidenceMobile = (TextView) findViewById(R.id.residenceMobileText);
        TextView textFax = (TextView) findViewById(R.id.faxText);
        TextView textMailId = (TextView) findViewById(R.id.emailIDText);
        TextView textOfficeAddress = (TextView) findViewById(R.id.addressText);
        TextView textPath=(TextView) findViewById(R.id.txt_path);

        textPath.setText(getValueFromIntentEmployee[1] +" > " +getValueFromIntentEmployee[2]);
        textName.setText(word.getEmpName().toString());
        textDesignation.setText(word.getDesignation_Name().toString());
        textPlaceofPosting.setText(word.getplace_of_posting().toString());
        textOfficeLandline.setText((word.getOffice_landline()));
        textOfficeMobile.setText((word.getOffice_mobile()));
        textResidenceLandline.setText(word.getHome_landline());
        textResidenceMobile.setText((word.getHome_mobile()));
        textFax.setText((word.getFax()));
        textMailId.setText(word.getEmail_id());
        textOfficeAddress.setText(word.getOffice_address().toString());

        Log.v("Office Landline = ",word.getOffice_landline());
        Log.v("Office Mobile = ",word.getOffice_mobile());
        Log.v("Res Landline = ",word.getHome_landline());
        Log.v("Res Mobile = ",word.getHome_mobile());
        Toast toast= Toast.makeText(getApplicationContext(),word.getHome_landline(),Toast.LENGTH_LONG);
        toast.show();
        LinearLayout offLandlineLayout = (LinearLayout) findViewById(R.id.officeLandlineLayout);
        LinearLayout offMobileLayout = (LinearLayout) findViewById(R.id.officeMobileLayout);
        LinearLayout resLandlineLayout = (LinearLayout) findViewById(R.id.resLandlineLayout);
        LinearLayout resMobileLayout = (LinearLayout) findViewById(R.id.resMobileLayout);



        callButton = (ImageButton) findViewById(R.id.officeLandlineImageButton);

        callButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
             //   callIntent.setData(Uri.parse("tel:" + cell));
                callIntent.setData(Uri.parse("tel:0" + (word.getOffice_landline())));
                if(ActivityCompat.checkSelfPermission(EmpInfooActivity.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(EmpInfooActivity.this,new String[]{Manifest.permission.CALL_PHONE},MY_PERMISSIONS_REQUEST_CALL_PHONE);
                }
                else
                {
                    startActivity(callIntent);
                }



            }

        });

        mapButton = (ImageButton) findViewById(R.id.addressImageButton);

        mapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i=new Intent(EmpInfooActivity.this,MapActivity.class);
                String passArray[]=new String[3];
                passArray[0]=word.getOffice_address().toString();
                passArray[1]=getValueFromIntentEmployee[1];
                passArray[2]=Integer.toString(word.getDesignationId());
                i.putExtra("LocationPlace",passArray);
                Log.v("click = ","done");
                startActivity(i);
            }

        });

        ImageButton addImage = (ImageButton) findViewById(R.id.emailIDImageButton);
        addImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                String aEmailList[] = { word.getEmail_id() };
               // String aEmailCCList[] = { "user3@fakehost.com","user4@fakehost.com"};
            //  String aEmailBCCList[] = { "user5@fakehost.com" };
               emailIntent.setData((Uri.parse("mailto:")));
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
          //    emailIntent.putExtra(android.content.Intent.EXTRA_CC, aEmailCCList);
       //       emailIntent.putExtra(android.content.Intent.EXTRA_BCC, aEmailBCCList);
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My subject");
           //     emailIntent.setType("message/rfc822");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "My message body.");
                startActivity(Intent.createChooser(emailIntent, "Send your email in:"));
            }
        });

    }
    //monitor phone call activities
    private class PhoneCallListener extends PhoneStateListener
    {

        private boolean isPhoneCalling = false;
        String LOG_TAG = "LOGGING 123";
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");
                    //      onResume();
                    // restart app

                 Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }



}
