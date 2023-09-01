package com.example.less4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

public class Exit_Activity extends Activity {
    private Button buttonLogOut;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit_activity);
        init();
    }

    void init() {
        buttonLogOut = findViewById(R.id.buttonLogOut);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickYes(View view) {
        mAuth.signOut();
        Intent i = new Intent(Exit_Activity.this, Entrance_Activity.class);
        startActivity(i);
    }

    public void onClickNo(View view) {
        Intent i = new Intent(Exit_Activity.this, MainActivity.class);
        startActivity(i);
    }
}
