package com.example.weprotectu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    String p;
    EditText phn;
    RadioGroup rg;
    RadioButton genbu;
    String genres,usernameres,bgres;
    Button b;
    EditText username;
    int genid;
    Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Intent i=getIntent();
        phn=(EditText)findViewById(R.id.phnum1);
        p=i.getStringExtra("phnumber");
        phn.setText(p);
        phn.setEnabled(false);
        sp=(Spinner)findViewById(R.id.bg);
        username=(EditText)findViewById(R.id.username1);
        usernameres=username.getText().toString();
        rg=(RadioGroup)findViewById(R.id.gender);
        genid=rg.getCheckedRadioButtonId();
        genbu=(RadioButton)findViewById(genid);
        genres=genbu.getText().toString();
        bgres=sp.getSelectedItem().toString();
        b=(Button)findViewById(R.id.con1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUp.this, "Details:"+usernameres+" "+bgres+" "+genres, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
