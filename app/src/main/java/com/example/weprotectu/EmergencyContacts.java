package com.example.weprotectu;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EmergencyContacts extends AppCompatActivity {
    EditText mn1,mn2,mn3,mn4;
    int res=0;
    ImageView add1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);
        mn1 = (EditText) findViewById(R.id.mobilenumber1);
        mn2 = (EditText) findViewById(R.id.mobilenumber2);
        mn3 = (EditText) findViewById(R.id.mobilenumber3);
        mn4 = (EditText) findViewById(R.id.mobilenumber4);
        mn1.setEnabled(false);
        mn2.setEnabled(false);
        mn3.setEnabled(false);
        mn4.setEnabled(false);
        add1=(ImageView)findViewById(R.id.add1);
        if (ContextCompat.checkSelfPermission(EmergencyContacts.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(EmergencyContacts.this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(EmergencyContacts.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        7602);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else
        {
            add1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addcontacts();
                }
            });
        }


    }

    private void addcontacts() {
        Intent i=new Intent();
        i.setAction(i.ACTION_PICK);
        i.setData(ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(i,0);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 7602: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Allow WeProtectU to access your contacts", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(EmergencyContacts.this,VerifyOtp.class);
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && data!=null)
        {
            Uri contactUri=data.getData();
            ContentResolver cr=getContentResolver();
            String id="";
            Cursor cursor= null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                cursor = cr.query(contactUri, new String[]{
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                },null,null);
            }

            try {
                cursor.moveToFirst();
                id=cursor.getString(0);
                mn1.setText(cursor.getString(1));
            }
            finally {
                cursor.close();
            }
            Cursor cursor1=cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,new String[]{ContactsContract.CommonDataKinds.Email.ADDRESS},
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" =? ",new String[]{id},null);
            try {
                cursor.moveToFirst();
                mn1.setText(cursor1.getString(0));
            }
            finally {
                cursor1.close();
            }
        }
    }

    public void next(View view) {
        Intent i=new Intent(EmergencyContacts.this,HomePage.class);
        startActivity(i);
        finish();
    }
}
