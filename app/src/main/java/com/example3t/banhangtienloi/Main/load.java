package com.example3t.banhangtienloi.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example3t.banhangtienloi.R;

import io.paperdb.Paper;

public class load extends AppCompatActivity {
    int SPLASH_TIME_OUT =3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Paper.init(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()

            {
                if(Paper.book().read("user")==null){

                    Intent intent= new Intent(load.this, MainActivity.class);
                    startActivity(intent);
                    finish();
            }else{
                    Intent intent= new Intent(load.this, Trangchu.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);
    }
}