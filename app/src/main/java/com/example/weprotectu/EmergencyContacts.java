package com.example.weprotectu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.admin.v1beta1.Progress;

import java.util.HashMap;
import java.util.Map;

public class EmergencyContacts extends AppCompatActivity {
    EditText mn1,mn2,mn3,mn4;
    TextView c1,c2,c3,c4;
    Button CreateAccount;
    String mnum1,mnum2,mnum3,mnum4;
    int res=0;
    ImageView add1,add2,add3,add4;
    ImageView remove1,remove2,remove3,remove4;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    ProgressDialog progressDialog;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);
        getSupportActionBar().setTitle("Emergency Contacts");
        mn1 = (EditText) findViewById(R.id.mobilenumber1);
        mn2 = (EditText) findViewById(R.id.mobilenumber2);
        mn3 = (EditText) findViewById(R.id.mobilenumber3);
        mn4 = (EditText) findViewById(R.id.mobilenumber4);
        CreateAccount=(Button)findViewById(R.id.ca); 
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
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(EmergencyContacts.this,new String[]{Manifest.permission.SEND_SMS},7603);
        }
        else
        {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            nextfunction();
        }
        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mnum1=mn1.getText().toString().trim();
                mnum1=checknumber(mnum1);
                mnum2=mn2.getText().toString().trim();
                mnum2=checknumber(mnum2);
                mnum3=mn3.getText().toString().trim();
                mnum3=checknumber(mnum3);
                mnum4=mn4.getText().toString().trim();
                mnum4=checknumber(mnum4);
                if(!mnum1.equals(mnum2) && !mnum2.equals(mnum3) && !mnum3.equals(mnum4) && !mnum3.equals(mnum1) && !mnum2.equals(mnum4)  && mnum1.length()!=0 && mnum1.length()!=0 && mnum2.length()!=0 && mnum3.length()!=0 && mnum4.length()!=0 )
                {
                    storingData();
                }
                else
                {
                    Toast.makeText(EmergencyContacts.this, "Select four Different Contacts ", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void storingData() {
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        Map<String,Object> user=new HashMap<>();
        Intent ii=getIntent();
        String a=ii.getStringExtra("keyvalues");
        String arr[]=a.split(" ");
        progressDialog=new ProgressDialog(EmergencyContacts.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.processdialogue);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        user.put("username",arr[0].replaceAll("$"," "));
        user.put("bloodgroup",arr[1]);
        user.put("gender",arr[2]);
        user.put("Phonenumber",arr[3]);
        user.put("EmergencyContact1",mnum1);
        user.put("EmergencyContact2",mnum2);
        user.put("EmergencyContact3",mnum3);
        user.put("EmergencyContact4",mnum4);
        DocumentReference docref=fstore.collection("user").document(fAuth.getCurrentUser().getUid());
        docref.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    //Toast.makeText(EmergencyContacts.this, "Contacts Inserted Succesfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EmergencyContacts.this,PrimaryPage.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(EmergencyContacts.this, "Unable to add data", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private String checknumber(String mobnum) {
        mobnum=mobnum.replaceAll(" ","");
        if(mobnum.length()==10)
        {
            count=count+1;
            return("+91"+mobnum);
        }
        if(mobnum.length()==13){
            count=count+1;
            return(mobnum);
        }
        else
        {
            //Toast.makeText(this, "error here", Toast.LENGTH_SHORT).show();
            return(mobnum);
        }

    }

    private void nextfunction() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EmergencyContacts.this,new String[]{Manifest.permission.READ_CONTACTS},7602);
        }
        else {
            //Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
            checkforloaction();

        }
    }

    private void addcontacts1() {
        Intent i1=new Intent();
        i1.setAction(i1.ACTION_PICK);
        i1.setData(ContactsContract.Contacts.CONTENT_URI);
        //Toast.makeText(this, "activity started", Toast.LENGTH_SHORT).show();
        startActivityForResult(i1,0);
    }
    private void addcontacts2() {
        Intent i2=new Intent();
        i2.setAction(i2.ACTION_PICK);
        i2.setData(ContactsContract.Contacts.CONTENT_URI);
        //Toast.makeText(this, "activity started", Toast.LENGTH_SHORT).show();
        startActivityForResult(i2,1);
    }
    private void addcontacts3() {
        Intent i3=new Intent();
        i3.setAction(i3.ACTION_PICK);
        i3.setData(ContactsContract.Contacts.CONTENT_URI);
        //Toast.makeText(this, "activity started", Toast.LENGTH_SHORT).show();
        startActivityForResult(i3,2);
    }
    private void addcontacts4() {
        Intent i4=new Intent();
        i4.setAction(i4.ACTION_PICK);
        i4.setData(ContactsContract.Contacts.CONTENT_URI);
        //Toast.makeText(this, "activity started", Toast.LENGTH_SHORT).show();
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
                    checkforloaction();


                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Allow WeProtectU to access your contacts to send your loaction to contacts", Toast.LENGTH_LONG).show();

                    //ActivityCompat.requestPermissions(EmergencyContacts.this,new String[]{Manifest.permission.READ_CONTACTS},7602);
                    Intent i=new Intent(EmergencyContacts.this,VerifyOtp.class);
                    finish();
                }
                return;
            }
            case 7603:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Request Accepted", Toast.LENGTH_SHORT).show();
                    nextfunction();
                }
                else
                {
                    Toast.makeText(this, "Allow WeProtectU to Send SMS", Toast.LENGTH_SHORT).show();
                    //ActivityCompat.requestPermissions(EmergencyContacts.this,new String[]{Manifest.permission.SEND_SMS},7602);
                    Intent i=new Intent(EmergencyContacts.this,VerifyOtp.class);
                    finish();
                }
            }
            case 7604: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    resultfunction();


                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Allow WeProtectU to on GPS to send your loaction to contacts", Toast.LENGTH_LONG).show();

                    //ActivityCompat.requestPermissions(EmergencyContacts.this,new String[]{Manifest.permission.READ_CONTACTS},7602);
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

    public void resultfunction()
    {
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

    private void checkforloaction() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(EmergencyContacts.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},7604);
        }
        else
        {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            resultfunction();
        }
    }

    @Override
    public void onBackPressed()
    {
        progressDialog.dismiss();
    }
}
