package com.example.less4;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.Nullable;

public class Address_Problem_Activity extends AppCompatActivity {
    String[] countriesFrame = { "УЛК", "Э", "НИЧ", "Г", "У", "С", "В", "Д", "А", "Е", "Ф"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_problem_activity);
        Spinner spinnerFrame = findViewById(R.id.spinnerFramStreet);
        ArrayAdapter<String> adapterFrame = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countriesFrame);
        adapterFrame.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrame.setAdapter(adapterFrame);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinnerFrame.setOnItemSelectedListener(itemSelectedListener);
    }
    public void onClickNext(View view) {
        Intent i = new Intent(Address_Problem_Activity.this, Letter_Problem_Activity.class);
        startActivity(i);
    }
}
