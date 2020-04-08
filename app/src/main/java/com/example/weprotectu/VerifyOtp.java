package com.example.weprotectu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class VerifyOtp extends AppCompatActivity {
    EditText enterotp1;
    Button b;
    FirebaseAuth fAuth;
    DocumentReference docref;
    FirebaseFirestore fstore;
    String p;
    ProgressBar pb1;
    TextView state1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        docref=fstore.collection("user").document(fAuth.getCurrentUser().getUid());
        enterotp1=(EditText)findViewById(R.id.enterotp);
        b=(Button)findViewById(R.id.continu);
        pb1=(ProgressBar)findViewById(R.id.pb);
        state1=(TextView)findViewById(R.id.state);
        Intent i=getIntent();
        final String c=i.getStringExtra("co");
        String ar[]=c.split(" ");
        final String codeSent=ar[0];
        p=ar[1];
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb1.setVisibility(View.VISIBLE);
                state1.setVisibility(View.VISIBLE);
                String code=enterotp1.getText().toString().trim();
                enterotp1.setEnabled(false);
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
                if(code.isEmpty()) {
                    enterotp1.setEnabled(true);
                    pb1.setVisibility(View.INVISIBLE);
                    state1.setVisibility(View.INVISIBLE);
                    enterotp1.setError("Enter OTP");
                    enterotp1.setEnabled(true);
                    getCurrentFocus();
                }
                else if(code.length()!=6)
                {
                    enterotp1.setEnabled(true);
                    pb1.setVisibility(View.INVISIBLE);
                    state1.setVisibility(View.INVISIBLE);
                    enterotp1.setError("Enter Valied OTP");
                    enterotp1.setEnabled(true);
                    getCurrentFocus();
                }
                    signInWithPhoneAuthCredential(credential);
            }
        });

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            checkProfile();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                Toast.makeText(VerifyOtp.this, "Invalied Credentials or OTP expired", Toast.LENGTH_SHORT).show();
                                /*pb1.setVisibility(View.VISIBLE);
                                state1.setVisibility(View.VISIBLE);*/
                                enterotp1.setEnabled(true);
                                Intent i3=new Intent(VerifyOtp.this,MainActivity.class);
                                startActivity(i3);
                                finish();
                            }
                        }
                    }

                });
    }

    private void checkProfile() {
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Toast.makeText(VerifyOtp.this, "Already a registered User", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(VerifyOtp.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                    Intent i2=new Intent(VerifyOtp.this,SignUp.class);
                    i2.putExtra("phnumber",p);
                    startActivity(i2);
                    finish();
                }
            }

        });
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        if(fAuth.getCurrentUser()!=null)
        {
            checkProfile();
        }
    }
}
