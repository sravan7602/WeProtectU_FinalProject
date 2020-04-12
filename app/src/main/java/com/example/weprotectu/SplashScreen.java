package com.example.weprotectu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final int timeout = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final FirebaseAuth fAuth=FirebaseAuth.getInstance();
                FirebaseFirestore firestore=FirebaseFirestore.getInstance();
                DocumentReference docref;
                if (fAuth.getInstance().getCurrentUser()!=null)
                {
                    Toast.makeText(SplashScreen.this, "user exist", Toast.LENGTH_SHORT).show();
                    docref=firestore.collection("user").document(fAuth.getInstance().getUid());
                    docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            //Toast.makeText(MainActivity.this, "Bye", Toast.LENGTH_SHORT).show();
                            if (documentSnapshot.exists()) {
                                //Toast.makeText(MainActivity.this, "res=1", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(SplashScreen.this, PrimaryPage.class);
                                startActivity(i);
                                finish();
                            } else{
                                Intent i=new Intent(SplashScreen.this,SignUp.class);
                                i.putExtra("phnumber",fAuth.getCurrentUser().getPhoneNumber().toString().substring(3,13));
                                startActivity(i);
                                //Toast.makeText(MainActivity.this, "res=0", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else
                {
                    Intent i=new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(i);
                    Toast.makeText(SplashScreen.this, "User doesnot exist", Toast.LENGTH_SHORT).show();
                }
            }
        },timeout);
    }
}