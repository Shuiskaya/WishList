//адаптер для активити друзей
package com.example.wishlist;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapterFriends extends FirebaseRecyclerAdapter<User, MainAdapterFriends.myViewHolder> {
    public int count;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapterFriends(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull User model) {

        holder.name.setText(model.getName());
        holder.nickname.setText(model.getNickname());
        Glide.with(holder.photo.getContext()).load(model.getPhoto()).placeholder(R.drawable.photo_user)
                .circleCrop().error(R.drawable.photo_user).into(holder.photo);

        holder.friend_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FriendGiftActivity.class);
                intent.putExtra("userID", model.getUserID());
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_friends,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView name, nickname;
        RelativeLayout friend_card;
        CircleImageView photo;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = (CircleImageView)itemView.findViewById(R.id.photo);
            name = (TextView)itemView.findViewById(R.id.name);
            nickname = (TextView)itemView.findViewById(R.id.nickname);
            friend_card = (RelativeLayout)itemView.findViewById(R.id.friend_card);
        }
    }
}












