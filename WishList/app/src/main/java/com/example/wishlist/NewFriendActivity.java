package com.example.wishlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wishlist.databinding.ActivityNewFriendBinding;
import com.example.wishlist.databinding.ActivityNewGiftBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class NewFriendActivity extends AppCompatActivity {

    private ActivityNewFriendBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewFriendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewFriendActivity.this, FriendsActivity.class));
            }
        });

        binding.userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewFriendActivity.this, UserActivity.class));
            }
        });

        binding.giftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewFriendActivity.this, MainActivity.class));
            }
        });

        binding.plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.nickname.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Заполните поле Т-Т", Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseDatabase.getInstance().getReference().child("Friends").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getUid()).orderByChild("nickname").equalTo(binding.nickname.getText().toString())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    boolean check = false;
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        check = true;
                                        Toast.makeText(getApplicationContext(), "Пользователь уже у вас в друзьях!", Toast.LENGTH_SHORT).show();
                                    }
                                    if (!check)
                                        FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("nickname")
                                                .equalTo(binding.nickname.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        boolean v = false;

                                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                                            v = true;
                                                            User user = new User();
                                                            user.setUserID(ds.getValue(User.class).getUserID());
                                                            user.setName(ds.getValue(User.class).getName());
                                                            user.setNickname(ds.getValue(User.class).getNickname());
                                                            user.setPhoto(ds.getValue(User.class).getPhoto());
                                                            FirebaseDatabase.getInstance().getReference().child("Friends").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                    .push().setValue(user);
                                                            startActivity(new Intent(NewFriendActivity.this, FriendsActivity.class));
                                                        }
                                                        if (!v) {
                                                            Toast.makeText(getApplicationContext(), "Такого пользователя не существует", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}
                            });
                }
            }
        });

    }
}