package com.example.weprotectu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EmergencyContacts extends AppCompatActivity {
    EditText mn1,mn2,mn3,mn4;
    TextView c1,c2,c3,c4;
    int res=0;
    ImageView add1,add2,add3,add4;
    ImageView remove1,remove2,remove3,remove4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);
        mn1 = (EditText) findViewById(R.id.mobilenumber1);
        mn2 = (EditText) findViewById(R.id.mobilenumber2);
        mn3 = (EditText) findViewById(R.id.mobilenumber3);
        mn4 = (EditText) findViewById(R.id.mobilenumber4);
        c1=(TextView)findViewById(R.id.contact1);
        c2=(TextView)findViewById(R.id.contact2);
        c3=(TextView)findViewById(R.id.contact3);
        c4=(TextView)findViewById(R.id.contact4);
        remove1=(ImageView)findViewById(R.id.remove1);
        remove2=(ImageView)findViewById(R.id.remove2);
        remove3=(ImageView)findViewById(R.id.remove3);
        remove4=(ImageView)findViewById(R.id.remove4);
        mn1.setEnabled(false);
        mn2.setEnabled(false);
        mn3.setEnabled(false);
        mn4.setEnabled(false);
        add1=(ImageView)findViewById(R.id.add1);
        add2=(ImageView)findViewById(R.id.add2);
        add3=(ImageView)findViewById(R.id.add3);
        add4=(ImageView)findViewById(R.id.add4);
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
            Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
            add1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addcontacts1();
                }
            });
            add2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addcontacts2();
                }
            });
            add3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addcontacts3();
                }
            });
            add4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addcontacts4();
                }
            });

        }


    }

    private void addcontacts1() {
        Intent i1=new Intent();
        i1.setAction(i1.ACTION_PICK);
        i1.setData(ContactsContract.Contacts.CONTENT_URI);
        Toast.makeText(this, "activity started", Toast.LENGTH_SHORT).show();
        startActivityForResult(i1,0);
    }
    private void addcontacts2() {
        Intent i2=new Intent();
        i2.setAction(i2.ACTION_PICK);
        i2.setData(ContactsContract.Contacts.CONTENT_URI);
        Toast.makeText(this, "activity started", Toast.LENGTH_SHORT).show();
        startActivityForResult(i2,1);
    }
    private void addcontacts3() {
        Intent i3=new Intent();
        i3.setAction(i3.ACTION_PICK);
        i3.setData(ContactsContract.Contacts.CONTENT_URI);
        Toast.makeText(this, "activity started", Toast.LENGTH_SHORT).show();
        startActivityForResult(i3,2);
    }
    private void addcontacts4() {
        Intent i4=new Intent();
        i4.setAction(i4.ACTION_PICK);
        i4.setData(ContactsContract.Contacts.CONTENT_URI);
        Toast.makeText(this, "activity started", Toast.LENGTH_SHORT).show();
        startActivityForResult(i4,3);
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
        if(requestCode==0 && data!=null) {
            Uri contactUri = data.getData();
            ContentResolver cr = getContentResolver();
            String id = "";
            Cursor cursor;
            cursor = cr.query(contactUri,null,null,null,null);


            try {
                cursor.moveToFirst();
                c1.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                id=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            } catch (Exception e) {
                Toast.makeText(this, "exception is " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                cursor.close();
            }
            Cursor cursor1 = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
            try {
                cursor1.moveToFirst();
                mn1.setText(cursor1.getString(0).trim());
            } catch (Exception e) {
                Toast.makeText(this, "2nd exception is " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                cursor1.close();
            }
        }

        if(requestCode==1 && data!=null) {
            Uri contactUri = data.getData();
            ContentResolver cr = getContentResolver();
            String id = "";
            Cursor cursor;
            cursor = cr.query(contactUri,null,null,null,null);


            try {
                cursor.moveToFirst();
                c2.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                id=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            } catch (Exception e) {
                Toast.makeText(this, "exception is " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                cursor.close();
            }
            Cursor cursor1 = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
            try {
                cursor1.moveToFirst();
                mn2.setText(cursor1.getString(0).trim());
            } catch (Exception e) {
                Toast.makeText(this, "2nd exception is " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                cursor1.close();
            }
        }
        if(requestCode==2 && data!=null) {

            Uri contactUri = data.getData();
            ContentResolver cr = getContentResolver();
            String id = "";
            Cursor cursor;
            cursor = cr.query(contactUri,null,null,null,null);


            try {
                cursor.moveToFirst();
                c3.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                id=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            } catch (Exception e) {
                Toast.makeText(this, "exception is " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                cursor.close();
            }
            Cursor cursor1 = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
            try {
                cursor1.moveToFirst();
                mn3.setText(cursor1.getString(0).trim());
            } catch (Exception e) {
                Toast.makeText(this, "2nd exception is " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                cursor1.close();
            }
        }
        if(requestCode==3 && data!=null) {
            Uri contactUri = data.getData();
            ContentResolver cr = getContentResolver();
            String id = "";
            Cursor cursor;
            cursor = cr.query(contactUri,null,null,null,null);


            try {
                cursor.moveToFirst();
                c4.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                id=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            } catch (Exception e) {
                Toast.makeText(this, "exception is " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                cursor.close();
            }
            Cursor cursor1 = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
            try {
                cursor1.moveToFirst();
                mn4.setText(cursor1.getString(0));
            } catch (Exception e) {
                Toast.makeText(this, "2nd exception is " + e.getMessage().trim(), Toast.LENGTH_SHORT).show();
            } finally {
                cursor1.close();
            }
        }

    }



    public void next(View view) {
        Intent i=new Intent(EmergencyContacts.this,HomePage.class);
        startActivity(i);
        finish();
    }

    public void rem1(View view) {
        c1.setText("Contact-1");
        mn1.setText(null);
    }
    public void rem2(View view) {
        c2.setText("Contact-2");
        mn2.setText(null);
    }
    public void rem3(View view) {
        c3.setText("Contact-3");
        mn3.setText(null);
    }
    public void rem4(View view) {
        c4.setText("Contact-4");
        mn4.setText(null);
    }
}
