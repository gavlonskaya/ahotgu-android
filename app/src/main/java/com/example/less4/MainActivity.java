package com.example.less4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.less4.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Photo_Problem_Activity photo_problem_activity = new Photo_Problem_Activity();
    private Queue<ImageView> images = new LinkedList<>();
    private ImageView imImage, imImage2, imImage3, imImage4;
    private String[] array;
    private ArrayAdapter<String> adapter;
    private Toolbar toolbar;
    private int category_index;
    public DrawerLayout drawer;
    private ActivityMainBinding binding;
    private StorageReference mStorageRef;
    private Uri uploadUri;
    private Button openCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_problems) {
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
            Toast.makeText(this, "Сообщить о проблеме", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_requests) {
            Intent i = new Intent(MainActivity.this, My_Problems_Activity.class);
            startActivity(i);
            Toast.makeText(this, "Мои обращения", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_exit) {
            Intent i = new Intent(MainActivity.this, Exit_Activity.class);
            startActivity(i);
            Toast.makeText(this, "Выход", Toast.LENGTH_SHORT).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void init() {
//        imImage=(findViewById(R.id.imImage));
//        imImage=(findViewById(R.id.imImage2));
//        imImage=(findViewById(R.id.imImage3));
//        imImage=(findViewById(R.id.imImage4));
        images.add(findViewById(R.id.imImage));
        images.add(findViewById(R.id.imImage2));
        images.add(findViewById(R.id.imImage3));
        images.add(findViewById(R.id.imImage4));
        mStorageRef = FirebaseStorage.getInstance().getReference("Image");
        openCamera = findViewById(R.id.openCamera);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open_camera, 100);
            }
        });

    }

    private void fillArray(int title, int arrayList, int titleMini, int index) {
        toolbar.setTitle(title);
        array = getResources().getStringArray(arrayList);
        adapter.clear();
        adapter.addAll(array);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, titleMini, Toast.LENGTH_SHORT).show();
        category_index = index;
    }

    public void onClickGalleryProblemAcivity(View view) {
        getImage();
    }

    private void getImage() {
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null && data.getData() != null) {
            if (resultCode == RESULT_OK) {
                Log.d("MyLog", "Image URL: " + data.getData());
                if(!images.isEmpty()) {
                    ImageView element = images.remove();
                    element.setImageURI(data.getData());
                    imImage = element;
                    uploadImage();
                }
            }
        } else {
            Bitmap photo =(Bitmap)data.getExtras().get("data");
            if(!images.isEmpty()) {
                ImageView element = images.remove();
                element.setImageBitmap(photo);
                imImage = element;
                uploadImage();
            }
        }
    }

    private void uploadImage() {
        Bitmap bitmap = ((BitmapDrawable) imImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        final StorageReference mRef = mStorageRef.child(System.currentTimeMillis() + "my_image");
        UploadTask up = mRef.putBytes(byteArray);

        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
        {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(
                new OnCompleteListener<Uri>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        uploadUri = task.getResult();
                    }

                });
    }
    public void onClickNext(View view) {
        Intent i = new Intent(MainActivity.this, Categories_Problem_Activity.class);
        startActivity(i);
    }

    public void onClickCamera(View view) {
        photo_problem_activity.onClickCamera(view);
    }

}