package com.allacsta.quizpemrogramanperangkatlunakmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.allacsta.quizpemrogramanperangkatlunakmobile.fragment.CameraFragment;
import com.allacsta.quizpemrogramanperangkatlunakmobile.fragment.ChatFragment;
import com.allacsta.quizpemrogramanperangkatlunakmobile.fragment.StoryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set default fragment when app starts
        loadFragment(new CameraFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_chat:
                        fragment = new ChatFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.action_camera:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CameraFragment()).commit();
                        fragment = new CameraFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.action_story:
                        fragment = new StoryFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}