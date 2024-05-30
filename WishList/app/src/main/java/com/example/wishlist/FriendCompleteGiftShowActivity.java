package com.example.wishlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wishlist.databinding.ActivityComleteGiftShowBinding;
import com.example.wishlist.databinding.ActivityFriendCompleteGiftShowBinding;

public class FriendCompleteGiftShowActivity extends AppCompatActivity {

    private ActivityFriendCompleteGiftShowBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFriendCompleteGiftShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String userID = "";
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            userID = arguments.get("userID").toString();
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

        String finalUserID = userID;
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FriendCompleteGiftActivity.class);
                intent.putExtra("userID", finalUserID);
                v.getContext().startActivity(intent);
            }
        });

        binding.userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendCompleteGiftShowActivity.this, UserActivity.class));
            }
        });
        binding.friendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendCompleteGiftShowActivity.this, FriendsActivity.class));
            }
        });
        binding.giftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendCompleteGiftShowActivity.this, MainActivity.class));
            }
        });

    }
}