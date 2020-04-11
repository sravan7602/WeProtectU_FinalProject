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
        final int timeout = 5000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth fAuth=FirebaseAuth.getInstance();
                FirebaseFirestore firestore=FirebaseFirestore.getInstance();
                DocumentReference docref;
                if (fAuth.getInstance().getCurrentUser()!=null)
                {
                    //Toast.makeText(this, "Hello "+fAuth.getInstance().getCurrentUser().toString(), Toast.LENGTH_SHORT).show();
                    docref=firestore.collection("user").document(fAuth.getInstance().getUid());
                    docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            //Toast.makeText(MainActivity.this, "Bye", Toast.LENGTH_SHORT).show();
                            if (documentSnapshot.exists()) {
                                //Toast.makeText(MainActivity.this, "res=1", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(SplashScreen.this, HomePage.class);
                                startActivity(i);
                            } else{
                                Intent i=new Intent(SplashScreen.this,SignUp.class);
                                //Toast.makeText(MainActivity.this, "res=0", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else
                {
                    Intent i=new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(i);
                }
            }
        },timeout);
    }
}