package com.example.wishlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.wishlist.databinding.ActivityComleteGiftBinding;
import com.example.wishlist.databinding.ActivityMainBinding;
import com.example.wishlist.databinding.ActivityNewFriendBinding;
import com.example.wishlist.databinding.ActivityUserBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.wishlist.databinding.ActivityMainBinding;

public class ComleteGiftActivity extends AppCompatActivity {

    private ActivityComleteGiftBinding binding;

    RecyclerView recyclerView;
    MainAdapter2 mainAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComleteGiftBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Gift> options =
                new FirebaseRecyclerOptions.Builder<Gift>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Gift")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).orderByChild("status")
                                .equalTo("Подарен"), Gift.class).build();

        FirebaseDatabase.getInstance().getReference().child("Gift")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).orderByChild("status")
                .equalTo("Подарен").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            binding.textView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        mainAdapter2 = new MainAdapter2(options);
        recyclerView.setAdapter(mainAdapter2);


        binding.userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComleteGiftActivity.this, UserActivity.class));
            }
        });
        binding.friendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComleteGiftActivity.this, FriendsActivity.class));
            }
        });

        binding.plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComleteGiftActivity.this, NewGiftActivity.class));
            }
        });

        binding.giftNotdoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComleteGiftActivity.this, MainActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter2.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter2.stopListening();
    }
}