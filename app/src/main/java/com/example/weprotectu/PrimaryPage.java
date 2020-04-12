package com.example.weprotectu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PrimaryPage extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore firestore;
    DocumentReference docref;
    String c1,c2,c3,c4,c5;
    String msg="Hello, This is the first message send from my Application";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_page);
        fAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        docref=firestore.collection("user").document(fAuth.getCurrentUser().getUid());
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    c1=documentSnapshot.getString("EmergencyContact1");
                    c1=documentSnapshot.getString("EmergencyContact1");
                    c1=documentSnapshot.getString("EmergencyContact1");
                    c1=documentSnapshot.getString("EmergencyContact1");
                }
            }
        });
    }

    private void sendsms(String c1) {
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(c1,null,msg,null,null);
        Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
    }

    public void moreoptions(View view) {
        Intent i=new Intent(PrimaryPage.this,HomePage.class);
        startActivity(i);
    }

    public void emergency(View view) {
        try{
        sendsms(c1);
        sendsms(c2);
        sendsms(c3);
        sendsms(c4);}
        catch(Exception e)
        {
            Toast.makeText(this, "error is : "+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
