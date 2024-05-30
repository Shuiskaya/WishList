//адаптер для активити подаренных подарков
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

public class MainAdapterForFriend2 extends FirebaseRecyclerAdapter<Gift, MainAdapterForFriend2.myViewHolder> {
    public int count;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapterForFriend2(@NonNull FirebaseRecyclerOptions<Gift> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Gift model) {
        holder.name.setText(model.getName());

        holder.gift_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FriendCompleteGiftShowActivity.class);
                intent.putExtra("userID", model.getUserID());
                intent.putExtra("name", model.getName());
                intent.putExtra("comment", model.getComment());
                intent.putExtra("link", model.getLink());
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        RelativeLayout gift_card;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.name);

            gift_card = (RelativeLayout)itemView.findViewById(R.id.gift_card);
        }
    }
}












