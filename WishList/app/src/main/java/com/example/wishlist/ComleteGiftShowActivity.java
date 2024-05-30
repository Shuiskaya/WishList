package com.example.wishlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wishlist.databinding.ActivityChangeGiftBinding;
import com.example.wishlist.databinding.ActivityComleteGiftShowBinding;

public class ComleteGiftShowActivity extends AppCompatActivity {

    private ActivityComleteGiftShowBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComleteGiftShowBinding.inflate(getLayoutInflater());
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

        binding.name.setEnabled(false);
        binding.link.setEnabled(false);
        binding.comment.setEnabled(false);

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComleteGiftShowActivity.this, ComleteGiftActivity.class));
            }
        });

        binding.userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComleteGiftShowActivity.this, UserActivity.class));
            }
        });
        binding.friendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComleteGiftShowActivity.this, FriendsActivity.class));
            }
        });
        binding.plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComleteGiftShowActivity.this, NewGiftActivity.class));
            }
        });

    }
}