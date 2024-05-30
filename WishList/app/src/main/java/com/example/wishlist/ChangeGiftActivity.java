package com.example.wishlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wishlist.databinding.ActivityChangeGiftBinding;
import com.example.wishlist.databinding.ActivityNewGiftBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class ChangeGiftActivity extends AppCompatActivity {

    private ActivityChangeGiftBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeGiftBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            //String giftID = arguments.get("giftID").toString();
            String name = arguments.get("name").toString();
            String comment = arguments.get("comment").toString();
            String link = arguments.get("link").toString();
            binding.giftname.setText(name);
            binding.name.setText(name);
            binding.link.setText(link);
            binding.comment.setText(comment);
        }

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangeGiftActivity.this, MainActivity.class));
            }
        });

        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData(arguments.get("giftID").toString());
                startActivity(new Intent(ChangeGiftActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Сохранено ^-^", Toast.LENGTH_SHORT).show();
            }
        });

        binding.comlpeteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Gift")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(arguments.get("giftID").toString()).child("/status")
                        .setValue("Подарен");
                startActivity(new Intent(ChangeGiftActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Сохранено ^-^", Toast.LENGTH_SHORT).show();
            }
        });

        binding.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Gift")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(arguments.get("giftID").toString()).removeValue();
                startActivity(new Intent(ChangeGiftActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Удалено ^-^", Toast.LENGTH_SHORT).show();
            }
        });

        binding.userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangeGiftActivity.this, UserActivity.class));
            }
        });
        binding.friendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangeGiftActivity.this, FriendsActivity.class));
            }
        });
    }
    private void uploadData(String giftID){
        FirebaseDatabase.getInstance().getReference().child("Gift")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(giftID).child("/name")
                .setValue(binding.name.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("Gift")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(giftID).child("/link")
                .setValue(binding.link.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("Gift")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(giftID).child("/comment")
                .setValue(binding.comment.getText().toString());

    }
}