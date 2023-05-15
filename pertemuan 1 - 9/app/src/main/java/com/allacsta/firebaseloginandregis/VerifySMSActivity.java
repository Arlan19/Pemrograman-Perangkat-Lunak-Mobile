package com.allacsta.firebaseloginandregis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifySMSActivity extends AppCompatActivity {

    private static final String TAG = "VerifySMSActivity";

    private String mVerificationId;
    private EditText editTextCode;
    private FirebaseAuth mAuth;
    private Button buttonverifivy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_smsactivity);

        mAuth = FirebaseAuth.getInstance();
        editTextCode = findViewById(R.id.et_kode_verifikasi);

        Intent intent = getIntent();
        String noHp = intent.getStringExtra("nohp");
        sendVerificationCode(noHp);



    }

    private void sendVerificationCode(String noHp){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+62"+noHp)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();


            if (code!=null){
                editTextCode.setText(code);
                verifyVerificationCode(code);
            }

            Log.d(TAG, "onVerificationCompleted: "+code);
            Toast.makeText(VerifySMSActivity.this, "Kode Otpnya adalah "+code, Toast.LENGTH_SHORT).show();

            buttonverifivy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    verifyVerificationCode(code);
                }
            });

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifySMSActivity.this,"Mohon maaf verifikasi login by sms gagal karena" +e.getMessage() ,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(VerifySMSActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(VerifySMSActivity.this, "Mohon maaf proses login gagal...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}