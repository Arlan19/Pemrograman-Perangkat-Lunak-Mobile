package com.allacsta.firebaseloginandregis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etlupaEmail;
    Button btnLupaPass;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etlupaEmail = findViewById(R.id.et_lupa_email);
        btnLupaPass = findViewById(R.id.lupa_pass);

        mAuth = FirebaseAuth.getInstance();

        btnLupaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = etlupaEmail.getText().toString().trim();

                mAuth.sendPasswordResetEmail(str_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgotPasswordActivity.this, "Link Reset password berhasil dikirim. silahkan cek emaill anda secara berkala", Toast.LENGTH_SHORT);
                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }
}