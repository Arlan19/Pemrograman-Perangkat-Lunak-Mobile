package com.allacsta.firebaseloginandregis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    FirebaseDatabase myDatabase;
    DatabaseReference myRef;

    TextView heloText;
    EditText etHello;
    Button btnHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        myDatabase = FirebaseDatabase.getInstance();

        heloText = findViewById(R.id.tv_hello);
        etHello = findViewById(R.id.et_hello);
        btnHello = findViewById(R.id.btn_hello);

        btnHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etHello.getText().toString().trim();

                //Create database
                myRef = myDatabase.getReference().child("pesan").child("001");
                myRef.setValue(message);

                //Read database
//                myRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String value = snapshot.getValue(String.class);
//                        heloText.setText(value);
////                        Log.d(TAG, "Value is: " + value);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

                //Delete database
//                myRef = myDatabase.getReference().child("pesan").child("002");
//                myRef.removeValue();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        mAuth.signOut();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}