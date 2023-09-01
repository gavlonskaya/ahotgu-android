package com.example.less4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Categories_Problem_Activity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private String USER_KEY ="User";
    private ListView list;
    private String[] array;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_problem_activity);
        init();
    }
    public void init() {
        mAuth = FirebaseAuth.getInstance();
    }
    public void onClickWindow(View view){
        setContentView(R.layout.categories_topics_problem_activity);
        list = findViewById(R.id.ListView);
        array = getResources().getStringArray(R.array.problems_window);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirebaseDatabase.getInstance().getReference().push().child("problems").setValue("Window");
                Intent i = new Intent(Categories_Problem_Activity.this, Address_Problem_Activity.class);
                startActivity(i);
            }
        });
    }
    public void onClickLampAndSwitch(View view){
        setContentView(R.layout.categories_topics_problem_activity);
        list = findViewById(R.id.ListView);
        array = getResources().getStringArray(R.array.problems_lamp_and_switch);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirebaseDatabase.getInstance().getReference().child("problems").setValue("LampAndSwitch");
                Intent i = new Intent(Categories_Problem_Activity.this, Address_Problem_Activity.class);
                startActivity(i);
            }
        });
    }
    public void onClickPlumbingAndWater(View view){
        setContentView(R.layout.categories_topics_problem_activity);
        list = findViewById(R.id.ListView);
        array = getResources().getStringArray(R.array.problems_plumbing_and_water);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirebaseDatabase.getInstance().getReference().push().child("problems").setValue("PlumbingAndWater");
                Intent i = new Intent(Categories_Problem_Activity.this, Address_Problem_Activity.class);
                startActivity(i);
            }
        });
    }
    public void onClickElevator(View view){
        setContentView(R.layout.categories_topics_problem_activity);
        list = findViewById(R.id.ListView);
        array = getResources().getStringArray(R.array.problems_elevator);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirebaseDatabase.getInstance().getReference().push().child("problems").setValue("Elevator");
                Intent i = new Intent(Categories_Problem_Activity.this, Address_Problem_Activity.class);
                startActivity(i);
            }
        });
    }

    public void onClickDoorWallFurniture(View view){
        setContentView(R.layout.categories_topics_problem_activity);
        list = findViewById(R.id.ListView);
        array = getResources().getStringArray(R.array.problems_door_wall_furniture);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirebaseDatabase.getInstance().getReference().push().child("problems").setValue("DoorWallFurniture");
                Intent i = new Intent(Categories_Problem_Activity.this, Address_Problem_Activity.class);
                startActivity(i);
            }
        });
    }
    public void onClickHeating(View view){
        setContentView(R.layout.categories_topics_problem_activity);
        list = findViewById(R.id.ListView);
        array = getResources().getStringArray(R.array.problems_heating);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirebaseDatabase.getInstance().getReference().push().child("problems").setValue("Heating");
                Intent i = new Intent(Categories_Problem_Activity.this, Address_Problem_Activity.class);
                startActivity(i);
            }
        });
    }

    public void onClickTerritory(View view){
        setContentView(R.layout.categories_topics_problem_activity);
        list = findViewById(R.id.ListView);
        array = getResources().getStringArray(R.array.problems_territory);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirebaseDatabase.getInstance().getReference().push().child("problems").setValue("Territory");
                Intent i = new Intent(Categories_Problem_Activity.this, Address_Problem_Street_Activity.class);
                startActivity(i);
            }
        });
    }
    public void onClickOther(View view){
        FirebaseDatabase.getInstance().getReference().push().child("problems").setValue("Other");
        Intent i = new Intent(Categories_Problem_Activity.this, Address_Problem_Activity.class);
        startActivity(i);
    }
}
