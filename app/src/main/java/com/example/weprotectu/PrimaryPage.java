package com.example.weprotectu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PrimaryPage extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore firestore;
    SmsManager smsManager=SmsManager.getDefault();
    DocumentReference docref;
    String c1,c2,c3,c4,msg="(Test message dont respond) I am in trouble, my location is:\n"+"http://maps.google.com/maps?q=";
    int count=0;
    ProgressDialog progressdialog;
    Double latitude,longitude;
    TextView textView;
    Button emergency;
    Intent i;
    //String la=Double.toString(latitude);
    //String lo=Double.toString(longitude);
    Button b;
    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_page);
        /*try {
            if(progressDialog!=null)
            progressDialog.cancel();
        }
        catch(Exception e){
            Toast.makeText(this, "exception is:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }*/
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //getlocation();
        textView=(TextView) findViewById(R.id.textreplace);
        b=(Button)findViewById(R.id.mo);
        emergency=(Button)findViewById(R.id.Emergency);
        fAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        docref=firestore.collection("user").document(fAuth.getCurrentUser().getUid());
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    c1=documentSnapshot.getString("EmergencyContact1");
                    c2=documentSnapshot.getString("EmergencyContact2");
                    c3=documentSnapshot.getString("EmergencyContact3");
                    c4=documentSnapshot.getString("EmergencyContact4");
                }
            }
        });
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    getlocation();
                    Toast.makeText(PrimaryPage.this, "Alert sent to your Emergency Contacts:", Toast.LENGTH_SHORT).show();
                    //textView.setText(msg);
                    //Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e)
                {
                    Toast.makeText(PrimaryPage.this, "error is:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PrimaryPage.this,HomePage.class);
                startActivity(i);
                //finish();
            }
        });
    }
    private void getlocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            count=count+1;
                            Toast.makeText(PrimaryPage.this, "1", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(PrimaryPage.this, "location:"+latitude+" "+longitude, Toast.LENGTH_SHORT).show();
                            msg=msg+latitude+","+longitude;
                            //textView.setText(msg);
                            smsManager.sendTextMessage(c1,null,msg,null,null);
                            //Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
                            SmsManager smsManager2=SmsManager.getDefault();
                            smsManager2.sendTextMessage(c2,null,msg,null,null);
                            //Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
                            SmsManager smsManager3=SmsManager.getDefault();
                            smsManager3.sendTextMessage(c3,null,msg,null,null);
                            //Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
                            SmsManager smsManager4=SmsManager.getDefault();
                            smsManager4.sendTextMessage(c4,null,msg,null,null);

                        }
                    }
                });
    }
    /*private void dialoguebox() {
        progressDialog=new ProgressDialog(PrimaryPage.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.processdialogue);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }
    @Override
    public void onBackPressed()
    {
        progressDialog.dismiss();
    }*/

}
