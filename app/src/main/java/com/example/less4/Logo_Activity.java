package com.example.less4;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Logo_Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo_activity);

        startEntranceActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
    protected void startEntranceActivity(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                Intent i = new Intent(Logo_Activity.this,Entrance_Activity.class);
                startActivity(i);
            }
        }).start();
    }
}
