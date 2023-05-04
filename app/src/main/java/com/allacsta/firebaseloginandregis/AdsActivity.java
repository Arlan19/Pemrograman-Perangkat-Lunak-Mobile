package com.allacsta.firebaseloginandregis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdsActivity extends AppCompatActivity {

    Button bannerAds, interstitialAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);

        bannerAds = findViewById(R.id.btn_banner_ads);
        interstitialAds = findViewById(R.id.btn_interstitial_ads);

        bannerAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent banner = new Intent(AdsActivity.this, BannerAdsActivity.class);
                startActivity(banner);
                finish();
            }
        });

        interstitialAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent interstitial = new Intent(AdsActivity.this, InterstitialAdsActivity.class);
                startActivity(interstitial);
                finish();
            }
        });
    }
}