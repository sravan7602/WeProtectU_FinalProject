package com.example.weprotectu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    EditText phno1;
    TextView state1;
    Button go1;
    String p;
    ProgressBar pb1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phno1=(EditText)findViewById(R.id.phno);
        go1=(Button)findViewById(R.id.go);
        pb1=(ProgressBar)findViewById(R.id.pb);
        state1=(TextView)findViewById(R.id.state);
        go1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p=phno1.getText().toString();
                if(p.length()==10)
                {
                    pb1.setVisibility(View.VISIBLE);
                    state1.setVisibility(View.VISIBLE);
                    phno1.setEnabled(false);
                    verifyPhoneNum();
                }
                else if(p.length()==0)
                {
                    phno1.setError("Enter Phno");
                    phno1.requestFocus();
                }
                else if(p.length()!=10)
                {
                    phno1.setError("Enter Valid Phno");
                    phno1.requestFocus();
                }
            }
        });

    }

    private void verifyPhoneNum() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+p,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            String c = s;
            Intent i=new Intent(MainActivity.this,VerifyOtp.class);
            i.putExtra("co",c);
            startActivity(i);
            finish();
        }
    };
}
