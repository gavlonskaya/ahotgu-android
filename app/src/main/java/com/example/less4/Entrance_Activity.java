package com.example.less4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Entrance_Activity extends Activity {
    private EditText edLogin, edPassword;
    private Button buttonEntranceRegister, buttonEntrance;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrance_activity);
        init();
    }
    private void init() {
        edLogin = findViewById(R.id.edLoginRegister);
        edPassword = findViewById(R.id.edPasswordRegister);
        buttonEntranceRegister = findViewById(R.id.buttonEntranceRegister);
        buttonEntrance = findViewById(R.id.buttonEntrance);
        mAuth = FirebaseAuth.getInstance();
    }
    public void onClickLogIn(View view) {
        Intent i = new Intent(Entrance_Activity.this, Log_In_Activity.class);
        startActivity(i);
    }
    public void onClickEntrance(View view) {
        if (edLogin.getText().toString().isEmpty() || edPassword.getText().toString().isEmpty()) {
            Toast.makeText(Entrance_Activity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(edLogin.getText().toString(), edPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(Entrance_Activity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(Entrance_Activity.this, "Пользователь не найден", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }
}