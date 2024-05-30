package com.example.wishlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wishlist.databinding.ActivityNewGiftBinding;
import com.example.wishlist.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class NewGiftActivity extends AppCompatActivity {
    private ActivityNewGiftBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewGiftBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewGiftActivity.this, MainActivity.class));
            }
        });
        binding.plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Заполните имя Т-Т", Toast.LENGTH_SHORT).show();
                }else {
                    newGift();
                }
            }
        });
        binding.userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewGiftActivity.this, UserActivity.class));
            }
        });
        binding.friendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewGiftActivity.this, FriendsActivity.class));
            }
        });
    }

    private void newGift(){
        HashMap<String, String> gift = new HashMap<>();
        gift.put("bookerID", "");
        gift.put("name", binding.name.getText().toString());
        gift.put("link", binding.link.getText().toString());
        gift.put("comment", binding.comment.getText().toString());
        gift.put("status", "Ожидание");
        gift.put("userID", FirebaseAuth.getInstance().getCurrentUser().getUid());
        //FirebaseDatabase.getInstance().getReference().child("Gift").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(gift);

        String activeIdLog = FirebaseDatabase.getInstance().getReference().child("Gift")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().getKey();
        gift.put("giftID", activeIdLog.toString());
        FirebaseDatabase.getInstance().getReference().child("Gift")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(activeIdLog).setValue(gift);

        //FirebaseDatabase.getInstance().getReference().child("Gift").push().setValue(gift);

        startActivity(new Intent(NewGiftActivity.this, MainActivity.class));
    }
}