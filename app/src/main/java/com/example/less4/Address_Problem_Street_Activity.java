package com.example.less4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Address_Problem_Street_Activity extends AppCompatActivity {
    String[] countriesStreet = { "УЛК", "Э", "НИЧ", "Г", "У", "С", "В", "Д", "А", "Е", "Ф"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_problem_street_activity);

        Spinner spinnerFrameStreet = findViewById(R.id.spinnerFramStreet);
        ArrayAdapter<String> adapterFrame = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countriesStreet);
        adapterFrame.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrameStreet.setAdapter(adapterFrame);


        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinnerFrameStreet.setOnItemSelectedListener(itemSelectedListener);

        checkMyPermission();
    }
    private void checkMyPermission() {
//        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener)
    }
        public void onClickNext(View view) {
            Intent i = new Intent(Address_Problem_Street_Activity.this, Letter_Problem_Activity.class);
            startActivity(i);
    }
}
