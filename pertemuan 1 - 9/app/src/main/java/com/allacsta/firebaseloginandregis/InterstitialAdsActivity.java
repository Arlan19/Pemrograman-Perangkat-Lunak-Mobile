package com.allacsta.firebaseloginandregis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class InterstitialAdsActivity extends AppCompatActivity {

    private static final long ADS_LENGTH_MILISECONDS = 3000;
    public static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    private static final String TAG = "InterstitialAdsActivity";

    private InterstitialAd mInterstitialAd;
    private CountDownTimer countDownTimer;
    private Button retryButton;
    private boolean delayIsInProgress;
    private long timerMiliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_ads);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        loadAd();



        retryButton = findViewById(R.id.retry_button);
        retryButton.setVisibility(View.VISIBLE);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(InterstitialAdsActivity.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    startAds();
                }
            }
        });

        startAds();
    }

    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, AD_UNIT_ID, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                Log.i(TAG, loadAdError.getMessage());
                mInterstitialAd = null;

                String error =  String.format("domain: %s, code: %d, message: %s",loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
                Toast.makeText(InterstitialAdsActivity.this, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                Log.d(TAG, "onAdLoaded");

                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        Log.d(TAG, "Ad was clicked.");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad dismissed fullscreen content.");
                        mInterstitialAd = null;
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        Log.e(TAG, "Ad failed to show fullscreen content.");
                        mInterstitialAd = null;
                    }

                    @Override
                    public void onAdImpression() {
                        Log.d(TAG, "Ad recorded an impression.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.");
                    }
                });
            }
        });
    }


    private void startAds() {
        if (mInterstitialAd == null){
            loadAd();
        }

        retryButton.setVisibility(View.INVISIBLE);
        resumeAds(ADS_LENGTH_MILISECONDS);
    }

    private void resumeAds(long adsLengthMiliseconds) {
        delayIsInProgress = true;
        timerMiliseconds = adsLengthMiliseconds;
        createTimer(adsLengthMiliseconds);
        countDownTimer.start();
    }

    private void createTimer(final long adsLengthMiliseconds) {
        if (countDownTimer != null){
            countDownTimer.cancel();
        }

        final TextView textView = findViewById(R.id.timer);

        countDownTimer = new CountDownTimer(adsLengthMiliseconds, 50) {
            @Override
            public void onTick(long l) {
                timerMiliseconds = l;
                textView.setText("seconds remaining: " + ((l /1000)+1));

            }

            @Override
            public void onFinish() {
                delayIsInProgress = false;
                textView.setText("done!");
                retryButton.setVisibility(View.VISIBLE);
            }
        };
    }

    @Override
    public void onResume() {
        // Start or resume the game.
        super.onResume();

        if (delayIsInProgress) {
            resumeAds(timerMiliseconds);
        }
    }

    @Override
    public void onPause() {
        // Cancel the timer if the game is paused.
        countDownTimer.cancel();
        super.onPause();
    }
}