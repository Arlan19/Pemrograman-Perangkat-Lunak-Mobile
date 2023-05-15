package com.allacsta.firebaseloginandregis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginWithPhoneActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private static final int ID_SIGN_IN = 1001;

    private FirebaseAuth mAuth;

    private Button btnLogin;

    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_phone);

        mAuth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.btn_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginWithPhoneActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.fragment_login_dialog, null);
                final EditText editTextPhone = mView.findViewById(R.id.et_no_hp);
                final Button btnLoginPhone = mView.findViewById(R.id.login_by_phone);
                final SignInButton btnLoginGoogle = mView.findViewById(R.id.login_by_google);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                btnLoginPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String noHp = editTextPhone.getText().toString().trim();

                        if (noHp.isEmpty() || noHp.length()< 10){
                            editTextPhone.setError("Isi No HP Anda");
                            editTextPhone.requestFocus();
                            return;
                        }

                        Intent intent = new Intent(LoginWithPhoneActivity.this, VerifySMSActivity.class);
                        intent.putExtra("nohp", noHp);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
                    }
                });
                btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        signInByGoogle();
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    private void signInByGoogle() {
        Intent singInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(singInIntent, ID_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==ID_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseSignInWithGoogle(account);
            }catch (ApiException ex){
                Log.d(TAG, "onActivityResult: "+ex.getMessage());
            }
        }
    }

    private void firebaseSignInWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginWithPhoneActivity.this, StorageActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginWithPhoneActivity.this, "Login by Google gagal...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}