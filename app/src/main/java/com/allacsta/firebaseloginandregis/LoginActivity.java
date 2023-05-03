package com.allacsta.firebaseloginandregis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email, pass;
    Button btnLogin;
    TextView tv_lupa_pass, tv_register, tv_login_with_phone;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.et_email);
        pass = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.bttn);
        tv_lupa_pass = findViewById(R.id.tv_lupa_pass);
        tv_register = findViewById(R.id.tv_daftar);
        tv_login_with_phone = findViewById(R.id.tv_klik_login_with_phone);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = email.getText().toString().trim();
                String str_pass = pass.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(str_email,str_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, StorageActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//                if(email.getText().toString().isEmpty()){
//                    email.setError("Mohon isi Email Anda...");
//                }
//                if(pass.getText().toString().isEmpty()){
//                    pass.setError("Mohon isi Password Anda...");
//                }
//                if (email.getText().toString().equals("arlan@gmail.com") &&
//                        pass.getText().toString().equals("123456")){
//                    Toast.makeText(MainActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(MainActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        tv_lupa_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_lupapass = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i_lupapass);
                finish();
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i_register);
                finish();
            }
        });

        tv_login_with_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_phone = new Intent(LoginActivity.this, LoginWithPhoneActivity.class);
                startActivity(i_phone);
                finish();
            }
        });

    }
}