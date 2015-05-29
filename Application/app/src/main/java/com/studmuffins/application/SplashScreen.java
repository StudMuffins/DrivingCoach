package com.studmuffins.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
/**
 * Created by Jonathan on 2015-05-21.
 */
public class SplashScreen extends Activity {
    // Display SplashScreen for 3sec
    private static int SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Executed after 3sec finished (opens MainActivity)
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);

                // Activity finished
                finish();
            }
        }, SCREEN_DELAY);
    }
}