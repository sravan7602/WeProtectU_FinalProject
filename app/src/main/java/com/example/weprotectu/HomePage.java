package com.example.weprotectu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firestore.admin.v1beta1.Progress;

public class HomePage extends AppCompatActivity {
    Button b;
    FirebaseAuth fAuth;
    //ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fAuth=FirebaseAuth.getInstance();
    }

    public void logout(View view) {
        //dialoguebox();
        fAuth.signOut();
        Intent i=new Intent(HomePage.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    /*private void dialoguebox() {
        progressDialog=new ProgressDialog(HomePage.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.processdialogue);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }
    @Override
    public void onBackPressed()
    {
        if(progressDialog!=null)
        progressDialog.dismiss();
    }*/


}
