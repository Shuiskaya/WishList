package com.example.wishlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.wishlist.databinding.ActivityFriendGiftBinding;
import com.example.wishlist.databinding.ActivityMainBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FriendGiftActivity extends AppCompatActivity {

    private ActivityFriendGiftBinding binding;

    RecyclerView recyclerView;
    MainAdapterForFriend mainAdapterForFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFriendGiftBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String userID = "";
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            userID = arguments.get("userID").toString();
            FirebaseDatabase.getInstance().getReference().child("Users").child(userID)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = snapshot.child("name").getValue().toString();
                            String nickname = snapshot.child("nickname").getValue().toString();
                            binding.textView2.setText(name + " (" + nickname + ")");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Gift> options =
                new FirebaseRecyclerOptions.Builder<Gift>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Gift")
                                .child(userID).orderByChild("status")
                                .equalTo("Ожидание"), Gift.class).build();

        FirebaseDatabase.getInstance().getReference().child("Gift")
                        .child(userID).orderByChild("status").equalTo("Ожидание")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
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

        mainAdapterForFriend = new MainAdapterForFriend(options);
        recyclerView.setAdapter(mainAdapterForFriend);

        binding.userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendGiftActivity.this, UserActivity.class));
            }
        });
        binding.friendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendGiftActivity.this, FriendsActivity.class));
            }
        });

        binding.giftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendGiftActivity.this, MainActivity.class));
            }
        });

        String finalUserID = userID;
        binding.giftCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FriendCompleteGiftActivity.class);
                intent.putExtra("userID", finalUserID);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapterForFriend.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapterForFriend.stopListening();
    }
}