package com.example.wishlist;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wishlist.databinding.ActivityLoginBinding;
import com.example.wishlist.databinding.ActivityUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;

    private ImageView imgProfile;
    private Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imgProfile = findViewById(R.id.photo);

        loadUserInfo();

        binding.giftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, MainActivity.class));
            }
        });

        binding.friendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, FriendsActivity.class));
            }
        });

        binding.logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserActivity.this, LoginActivity.class));
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() { //кнопка изменения фотографии профиля
            @Override
            public void onClick(View v) {
                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                photoIntent.setType("image/*");
                startActivityForResult(photoIntent, 1);
                //uploadImage();
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
                loadUserInfo();
            }
        });
    }

    private void uploadData(){


        if (binding.name.getText().toString().isEmpty() || binding.nickname.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Поля не могут быть пустыми!", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("nickname")
                    .equalTo(binding.nickname.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean check = false;
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                check = true;
                            }
                            if (check) {
                                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance()
                                        .getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String nickname;
                                        boolean check2=false;
                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            nickname = snapshot.child("nickname").getValue().toString();
                                            if (nickname.equals(binding.nickname.getText().toString()))
                                                check2=true;
                                        }
                                        if(check2){
                                            if (imagePath!=null){
                                                FirebaseStorage.getInstance().getReference("images/"+ UUID.randomUUID().toString())
                                                        .putFile(imagePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                                if(task.isSuccessful()) {
                                                                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Uri> task) {
                                                                            if (task.isSuccessful()){
                                                                                updateProfilePicture(task.getResult().toString());
                                                                            }
                                                                        }
                                                                    });
                                                                }else {
                                                                    Toast.makeText(getApplicationContext(), "Не удалось сохранить фото :(", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                            FirebaseDatabase.getInstance().getReference("Users/"+FirebaseAuth.getInstance()
                                                    .getCurrentUser().getUid() + "/name").setValue(binding.name.getText().toString());
                                            FirebaseDatabase.getInstance().getReference("Users/"+FirebaseAuth.getInstance()
                                                    .getCurrentUser().getUid() + "/nickname").setValue(binding.nickname.getText().toString());

                                            Toast.makeText(getApplicationContext(), "Сохранено ^-^", Toast.LENGTH_SHORT).show();
                                        }else
                                            Toast.makeText(getApplicationContext(), "Данный никнейм уже занят!", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {}
                                });
                            } else {
                                if (imagePath!=null){
                                    FirebaseStorage.getInstance().getReference("images/"+ UUID.randomUUID().toString())
                                            .putFile(imagePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                    if(task.isSuccessful()) {
                                                        task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Uri> task) {
                                                                if (task.isSuccessful()){
                                                                    updateProfilePicture(task.getResult().toString());
                                                                }
                                                            }
                                                        });
                                                    }else {
                                                        Toast.makeText(getApplicationContext(), "Не удалось сохранить фото :(", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                                FirebaseDatabase.getInstance().getReference("Users/"+FirebaseAuth.getInstance()
                                        .getCurrentUser().getUid() + "/name").setValue(binding.name.getText().toString());
                                FirebaseDatabase.getInstance().getReference("Users/"+FirebaseAuth.getInstance()
                                        .getCurrentUser().getUid() + "/nickname").setValue(binding.nickname.getText().toString());

                                Toast.makeText(getApplicationContext(), "Сохранено ^-^", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
        }
    }

    private void updateProfilePicture(String url){
        FirebaseDatabase.getInstance().getReference("Users/"+FirebaseAuth.getInstance()
                .getCurrentUser().getUid() + "/photo").setValue(url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data!=null){
            imagePath = data.getData();
            getImageInImageView();
        }
    }

    private void getImageInImageView() {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imgProfile.setImageBitmap(bitmap);
    }

    private void loadUserInfo(){
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue().toString();
                        String photo = snapshot.child("photo").getValue().toString();
                        String nickname = snapshot.child("nickname").getValue().toString();

                        binding.username.setText(name);
                        binding.name.setText(name);
                        binding.nickname.setText(nickname);
                        if(!photo.isEmpty()) {
                            Glide.with(getBaseContext()).load(photo).into(imgProfile);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}