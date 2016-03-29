package dynamitechetan.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
//            Toast.makeText(this, "Large screen", Toast.LENGTH_LONG).show();
//        }
//        else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
//            Toast.makeText(this, "Normal sized screen", Toast.LENGTH_LONG).show();
//        }
//        else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
//            Toast.makeText(this, "Small sized screen", Toast.LENGTH_LONG).show();
//        }
//        else {
//            Toast.makeText(this, "Screen size is neither large, normal or small", Toast.LENGTH_LONG).show();
//        }
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if(isOnline()) {
                    Intent startActivity = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(startActivity);
                }
                else{
                    Intent startActivity = new Intent(SplashScreen.this, OfflineActivity.class);
                    startActivity(startActivity);                }

                finish();
            }

        }, 3000);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
