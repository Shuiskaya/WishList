package com.example.wishlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wishlist.databinding.ActivityComleteGiftBinding;
import com.example.wishlist.databinding.ActivityFriendCompleteGiftBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FriendCompleteGiftActivity extends AppCompatActivity {

    private ActivityFriendCompleteGiftBinding binding;

    RecyclerView recyclerView;
    MainAdapterForFriend2 mainAdapterForFriend2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFriendCompleteGiftBinding.inflate(getLayoutInflater());
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
                                .equalTo("Подарен"), Gift.class).build();

        FirebaseDatabase.getInstance().getReference().child("Gift")
                .child(userID).orderByChild("status").equalTo("Подарен")
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

        mainAdapterForFriend2 = new MainAdapterForFriend2(options);
        recyclerView.setAdapter(mainAdapterForFriend2);


        binding.userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendCompleteGiftActivity.this, UserActivity.class));
            }
        });
        binding.friendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendCompleteGiftActivity.this, FriendsActivity.class));
            }
        });

        binding.giftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendCompleteGiftActivity.this, MainActivity.class));
            }
        });

        String finalUserID = userID;
        binding.giftNotdoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FriendGiftActivity.class);
                intent.putExtra("userID", finalUserID);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapterForFriend2.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapterForFriend2.stopListening();
    }
}