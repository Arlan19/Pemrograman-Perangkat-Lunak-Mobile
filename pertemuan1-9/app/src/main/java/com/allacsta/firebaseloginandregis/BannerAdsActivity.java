package com.allacsta.firebaseloginandregis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class BannerAdsActivity extends AppCompatActivity {

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ads);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                Toast.makeText(BannerAdsActivity.this, "Proses Inisialisasi berhasil", Toast.LENGTH_SHORT).show();
            }
        });

        mAdView = findViewById(R.id.AdView);
        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onPause() {
        if (mAdView!=null){
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mAdView!=null){
            mAdView.resume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mAdView!=null){
            mAdView.destroy();
        }
        super.onDestroy();
    }
}