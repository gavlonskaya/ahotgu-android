package com.example.less4;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Letter_Problem_Activity extends AppCompatActivity {


    private EditText edDescription;

    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private String USER_KEY ="User";
    private String FILE_NAME = "description.txt";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.letter_problem_activity);
        init();
    }

    private void init(){
        edDescription = findViewById(R.id.edDescription);
        mAuth = FirebaseAuth.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickSubmitRequest(View view){
        FirebaseDatabase.getInstance().getReference().child(USER_KEY).child("description").setValue(edDescription.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1500);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                Intent i = new Intent(Letter_Problem_Activity.this,My_Problems_Activity.class);
                startActivity(i);
            }
        }).start();
        try {
            File file = new File(getFilesDir(), FILE_NAME);
//                if (file.exists()) {
//                    file.delete();
//                }
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("@" + edDescription.getText().toString() + "@");
            bw.newLine(); // добавляем перевод строки
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Запрос отправлен", Toast.LENGTH_SHORT).show();

    }
}
