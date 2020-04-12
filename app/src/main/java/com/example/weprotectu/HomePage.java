package com.example.weprotectu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {
    Button b;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);
        fAuth=FirebaseAuth.getInstance();
    }

    public void logout(View view) {
        fAuth.signOut();
        Intent i=new Intent(HomePage.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void Editprofile(View view) {
        Intent i=new Intent(HomePage.this,SignUp.class);
        startActivity(i);
        finish();
    }
}
