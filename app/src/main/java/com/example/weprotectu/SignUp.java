package com.example.weprotectu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.admin.v1beta1.Progress;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String p;
    EditText phn;
    EditText username;
    Spinner bgp1;
    String bloodgroupres,genderres,usernameres;
    RadioButton rb;
    RadioGroup rg;
    Button b;
    String uid;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Intent i=getIntent();
        phn=(EditText)findViewById(R.id.phnum1);
        p=i.getStringExtra("phnumber");
        phn.setText(p);
        phn.setEnabled(false);
        username=(EditText)findViewById(R.id.username1);
        bgp1=(Spinner)findViewById(R.id.bg);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.bloodgroup,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bgp1.setAdapter(adapter);
        bgp1.setOnItemSelectedListener(this);
        b=(Button)findViewById(R.id.con1);
        rg=(RadioGroup)findViewById(R.id.gender);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameres=username.getText().toString();
                Toast.makeText(SignUp.this, "details: "+usernameres+" "+bloodgroupres+" "+genderres, Toast.LENGTH_SHORT).show();
                if(usernameres.isEmpty()) {
                    username.setError("Fill the User Name");
                    username.requestFocus();
                }
                else if(bloodgroupres.isEmpty())
                {
                    Toast.makeText(SignUp.this, "Select Your Blood Group", Toast.LENGTH_SHORT).show();
                }
                else if(genderres.isEmpty())
                {
                    Toast.makeText(SignUp.this, "Select Gender", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    usernameres=usernameres.replaceAll(" ","$");
                    String s=usernameres+" "+bloodgroupres+" "+genderres+" "+p;
                    progressDialog=new ProgressDialog(SignUp.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.processdialogue);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    Intent intent=new Intent(SignUp.this,EmergencyContacts.class);
                    intent.putExtra("keyvalues",s);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        bloodgroupres=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void onRadioButtonClick(View view) {
        rb=(RadioButton)findViewById(rg.getCheckedRadioButtonId());
        genderres=rb.getText().toString();
    }
    @Override
    public void onBackPressed()
    {
        progressDialog.dismiss();
    }

}
