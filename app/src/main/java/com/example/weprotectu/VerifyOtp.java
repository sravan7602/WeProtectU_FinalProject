package com.example.weprotectu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOtp extends AppCompatActivity {
    EditText enterotp1;
    Button b;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        enterotp1=(EditText)findViewById(R.id.enterotp);
        b=(Button)findViewById(R.id.continu);
        mAuth=FirebaseAuth.getInstance();
        Intent i=getIntent();
        final String codeSent=i.getStringExtra("co");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=enterotp1.getText().toString().trim();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
                signInWithPhoneAuthCredential(credential);
            }
        });

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VerifyOtp.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent i2=new Intent(VerifyOtp.this,SignUp.class);
                            startActivity(i2);
                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                Toast.makeText(VerifyOtp.this, "Invalied Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                });
    }
}
