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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Log_In_Activity extends Activity {

    private EditText edNameRegister, edSurnameRegister, edLoginRegister, edPasswordRegister;
    private Button buttonRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private String USER_KEY ="User";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_activity);
        init();
    }

    private void init() {
        edNameRegister = findViewById(R.id.edNameRegister);
        edSurnameRegister = findViewById(R.id.edSurnameRegister);
        edLoginRegister = findViewById(R.id.edLoginRegister);
        edPasswordRegister = findViewById(R.id.edPasswordRegister);
        buttonRegister = findViewById(R.id.buttonEntrance);
        mAuth = FirebaseAuth.getInstance();
        mDataBase= FirebaseDatabase.getInstance().getReference(USER_KEY);
    }

    public void onClickLogIn(View view) {
        if (edNameRegister.getText().toString().isEmpty() ||
                edSurnameRegister.getText().toString().isEmpty() ||
                edLoginRegister.getText().toString().isEmpty() ||
                edPasswordRegister.getText().toString().isEmpty())
        {
            Toast.makeText(Log_In_Activity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(edLoginRegister.getText().toString(), edPasswordRegister.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id = mDataBase.push().getKey();
                            String name = edNameRegister.getText().toString();
                            String surname = edSurnameRegister.getText().toString();
                            String login = edLoginRegister.getText().toString();
                            User newUser = new User(id, name, surname,login);
                            mDataBase.push().setValue(newUser);
                            Intent i = new Intent(Log_In_Activity.this, Entrance_Activity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(Log_In_Activity.this, "Возникла ошибка", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }
}

