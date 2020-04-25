package frusdev.com.cbot;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    SharedPreferences prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prf = getSharedPreferences("MyPref",0);

        if (prf.getBoolean("status", false)) {
            Toast.makeText(SplashActivity.this, "Username:"+prf.getString("username", null), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SplashActivity.this, chat.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(SplashActivity.this, "No user found!", Toast.LENGTH_LONG).show();

            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);

        }
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
       // Toast.makeText(SplashActivity.this,"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }



}
