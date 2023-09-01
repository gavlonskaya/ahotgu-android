package com.example.less4;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class My_Problems_Activity extends AppCompatActivity {
    private ListView list;
    private ArrayAdapter<String> adapter;
    private String FILE_NAME = "description.txt";
    private String[] description;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_problems_activity);
        getDescription();
        list = findViewById(R.id.ListViewProblems);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, description);
        list.setAdapter(adapter);
    }

    public void getDescription(){
        String content = "";
        try {
            File file = new File(getFilesDir(), FILE_NAME);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                content = content + line;
            }
            br.close();
            isr.close();
            fis.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> list = new ArrayList<>();
        for (String str:content.split("@")) {
            if(!str.isEmpty() && str.length() != 0){
                list.add(str);
            }
        }
        description = list.stream().toArray(String[]::new);
    }

    public void onClickAll(View view) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, description);
        list.setAdapter(adapter);
    }

    public void onClickOpen(View view) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, description);
        list.setAdapter(adapter);
    }

    public void onClickClose(View view) {
        String[] str = {};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, str);
        list.setAdapter(adapter);
    }

    public void onClickExit(View view) {
        Intent i = new Intent(My_Problems_Activity.this, MainActivity.class);
        startActivity(i);
    }

}
