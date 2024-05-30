package com.example.wishlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wishlist.databinding.ActivityRegisterBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.email.getText().toString().isEmpty() || binding.nickname.getText().toString().isEmpty()
                        || binding.pass.getText().toString().isEmpty() || binding.name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Поля не могут быть пустыми", Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("nickname")
                            .equalTo(binding.nickname.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    boolean check = false;
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        check = true;
                                        Toast.makeText(getApplicationContext(), "Никнейм уже занят :(", Toast.LENGTH_SHORT).show();
                                    }
                                    if (!check) {
                                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.email.getText().toString(), binding.pass.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            HashMap<String, String> userInfo = new HashMap<>();
                                                            userInfo.put("userID", FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                                                            userInfo.put("name", binding.name.getText().toString());
                                                            userInfo.put("nickname", binding.nickname.getText().toString());
                                                            userInfo.put("email", binding.email.getText().toString());
                                                            userInfo.put("photo", "");
                                                            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                    .setValue(userInfo);

                                                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                        }else {
                                                            Toast.makeText(getApplicationContext(), "Почта уже занята :(", Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}
                            });

                }
            }
        });

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}