package br.com.pdasolucoes.dashboardinventario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by PDA on 22/03/2017.
 */

public class SplashActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.actionbar);
        setContentView(R.layout.splash);

        Handler handler = new Handler();
        handler.postDelayed(this, 5000);
    }

    @Override
    public void run() {

        SharedPreferences preferences = getSharedPreferences("inventario", MODE_PRIVATE);

        if (preferences != null) {
            preferences.getString("autorizacao", "");
            if (preferences.getString("autorizacao", "") != "") {
                startActivity(new Intent(this, DashBoardActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, AutorizaoActivity.class));

            }
        }

    }
}
