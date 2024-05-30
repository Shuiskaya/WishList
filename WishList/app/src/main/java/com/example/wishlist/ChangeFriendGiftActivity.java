package com.example.wishlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wishlist.databinding.ActivityChangeFriendGiftBinding;
import com.example.wishlist.databinding.ActivityChangeGiftBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeFriendGiftActivity extends AppCompatActivity {

    private ActivityChangeFriendGiftBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeFriendGiftBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle arguments = getIntent().getExtras();
        String giftID = "", name = "", comment = "", link = "", bookerID = "", userID = "", status = "";
        if(arguments!=null){
            giftID = arguments.get("giftID").toString();
            bookerID = arguments.get("bookerID").toString();
            userID = arguments.get("userID").toString();
            status = arguments.get("status").toString();
            name = arguments.get("name").toString();
            comment = arguments.get("comment").toString();
            link = arguments.get("link").toString();
            binding.giftname.setText(name);
            binding.name.setText(name);
            binding.link.setText(link);
            binding.comment.setText(comment);
        }

        binding.name.setEnabled(false);
        binding.link.setEnabled(false);
        binding.comment.setEnabled(false);

        if(bookerID.equals("")){
            binding.useBtn.setText("Забронировать");
        }else {
            if(bookerID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())) {
                binding.useBtn.setText("Снять бронь");
            }else {
                binding.useBtn.setVisibility(View.GONE);
            }
        }

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FriendGiftActivity.class);
                intent.putExtra("userID", arguments.get("userID").toString());
                v.getContext().startActivity(intent);
            }
        });

        String finalBookerID = bookerID;
        String finalUserID = userID;
        String finalGiftID = giftID;
        String finalBookerID1 = bookerID;
        binding.useBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalBookerID.equals("")){
                    FirebaseDatabase.getInstance().getReference("Gift/"+ finalUserID + "/" + finalGiftID + "/bookerID")
                            .setValue(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                }else {
                    if(finalBookerID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())) {
                        FirebaseDatabase.getInstance().getReference("Gift/"+ finalUserID + "/" + finalGiftID + "/bookerID")
                                .setValue("");
                    }
                }
                Toast.makeText(getApplicationContext(), "Сохранено ^-^", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), FriendGiftActivity.class);
                intent.putExtra("userID", arguments.get("userID").toString());
                v.getContext().startActivity(intent);
            }
        });

        binding.giftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangeFriendGiftActivity.this, MainActivity.class));
            }
        });
        binding.userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangeFriendGiftActivity.this, UserActivity.class));
            }
        });
        binding.friendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangeFriendGiftActivity.this, FriendsActivity.class));
            }
        });
    }
}