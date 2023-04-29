package com.example.travelplanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelplanner.R;
import com.example.travelplanner.converter.DataConverter;
import com.example.travelplanner.model.WishlistItem;

import java.util.List;

public class WishlistListAdapter extends RecyclerView.Adapter<WishlistListAdapter.MyViewHolder>{

    private Context context;
    private List<WishlistItem> wishlist;
    private ItemClickListener mItemListener;

    public WishlistListAdapter(Context context, ItemClickListener itemClickListener){
        this.context = context;
        this.mItemListener = itemClickListener;
    }

    public void setList(List<WishlistItem> wishlist){
        this.wishlist = wishlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_wishlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(this.wishlist.get(position).getName());
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_one));
        holder.iv.setImageResource(R.drawable.wishlist1);
        holder.itemView.setOnClickListener( view -> {
            mItemListener.onItemClick(wishlist.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }

    public interface ItemClickListener{
        void onItemClick(WishlistItem wishlistItem);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, longitude, latitude;
        ImageView iv;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvTitleWl);
            iv = itemView.findViewById(R.id.ivListWl);
            cardView = itemView.findViewById(R.id.wCardView);
        }
    }
}
