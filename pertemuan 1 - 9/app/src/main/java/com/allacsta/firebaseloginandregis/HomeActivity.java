package com.allacsta.firebaseloginandregis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase myDatabase;
    DatabaseReference myRef;
    ArrayList<String> arrChannel = new ArrayList<>();
    ArrayList<String> arrUrl = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = findViewById(R.id.listView);

        myDatabase = FirebaseDatabase.getInstance();
        myRef = myDatabase.getReference().child("channel");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    String chn = ds.getValue(Channel.class).getNama();
                    String url = ds.getValue(Channel.class).getUrl();
                    arrChannel.add(chn);
                    arrUrl.add(url);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(HomeActivity.this, android.R.layout.simple_list_item_1 ,arrChannel);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String namaValue = arrChannel.get(i);
                        String urlValue = arrUrl.get(i);
                        Toast.makeText(HomeActivity.this, "Channel"+namaValue, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this, VideoActivity.class);
                        intent.putExtra("url", urlValue);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}