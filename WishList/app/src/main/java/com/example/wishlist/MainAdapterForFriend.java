package com.example.wishlist;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Objects;

public class MainAdapterForFriend extends FirebaseRecyclerAdapter<Gift, MainAdapterForFriend.myViewHolder> {
    public int count;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapterForFriend(@NonNull FirebaseRecyclerOptions<Gift> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Gift model) {
        holder.name.setText(model.getName());
        if(Objects.equals(model.getBookerID(), "")) {
            holder.status.setText("Свободен");
        }else
            holder.status.setText("Бронь");


        holder.gift_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChangeFriendGiftActivity.class);
                intent.putExtra("giftID", model.getGiftID());
                intent.putExtra("userID", model.getUserID());
                intent.putExtra("name", model.getName());
                intent.putExtra("comment", model.getComment());
                intent.putExtra("link", model.getLink());
                intent.putExtra("status", model.getStatus());
                intent.putExtra("bookerID", model.getBookerID());
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_for_friend,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView name, status;
        RelativeLayout gift_card;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.name);
            status = (TextView)itemView.findViewById(R.id.status);

            gift_card = (RelativeLayout)itemView.findViewById(R.id.gift_card);
        }
    }
}












