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
import com.example.travelplanner.model.Destination;
import com.example.travelplanner.model.TravelPlannerRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DestinationListAdapter extends RecyclerView.Adapter<DestinationListAdapter.MyViewHolder> {

    Context context;
    private List<Destination> destinationList;
    ItemClickListener itemClickListener;

    public DestinationListAdapter(Context context, ItemClickListener itemClickListener){
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    public void setDestinationList(List<Destination> destinationList){
        this.destinationList = destinationList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(this.destinationList.get(position).getName().equals("")?"-":this.destinationList.get(position).getName());
        holder.datumOd.setText("To: "+(this.destinationList.get(position).getDatumDo().equals("Choose Date To")?"":this.destinationList.get(position).getDatumDo()));
        holder.datumDo.setText("From: "+(this.destinationList.get(position).getDatumOd().equals("Choose Date From")?"":this.destinationList.get(position).getDatumOd()));
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_one));
        holder.iv.setImageBitmap(DataConverter.convertByteArray2Bitmap(this.destinationList.get(position).getImage()));

        holder.itemView.setOnClickListener( view -> {
            itemClickListener.onItemClick(destinationList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public interface ItemClickListener{
        void onItemClick(Destination destination);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView datumOd;
        TextView datumDo;
        ImageView iv;
        CardView cardView;

        public MyViewHolder(View view){
            super(view);
            name = itemView.findViewById(R.id.tvTitle);
            datumOd = itemView.findViewById(R.id.tvDatumDo);
            datumDo = itemView.findViewById(R.id.tvDatumOd);
            iv = itemView.findViewById(R.id.ivList);
            cardView = itemView.findViewById(R.id.cardView);
        }

    }
}
