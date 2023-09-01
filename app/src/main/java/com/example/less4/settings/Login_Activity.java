package com.example.less4.settings;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import androidx.annotation.Nullable;

import com.example.less4.R;

public class Login_Activity extends Activity {
    private EditText edNameRegister, edSurnameRegister, edLoginRegister, edPasswordRegister;
    private FirebaseAuth mAuth;
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
        mAuth= FirebaseAuth.getInstance();
    }

}
