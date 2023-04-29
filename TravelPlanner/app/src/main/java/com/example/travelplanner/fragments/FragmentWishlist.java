package com.example.travelplanner.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.travelplanner.R;
import com.example.travelplanner.activities.AddWishlistItemActivity;
import com.example.travelplanner.adapter.WishlistListAdapter;
import com.example.travelplanner.model.Destination;
import com.example.travelplanner.model.TravelPlannerRepository;
import com.example.travelplanner.model.WishlistItem;

import java.util.List;

public class FragmentWishlist extends Fragment {

    List<WishlistItem> wishlist;
    Button btnAdd;
    private WishlistListAdapter wishlistListAdapter;
    TravelPlannerRepository travelPlannerRepository;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        btnAdd = view.findViewById(R.id.btnWishlist);
        travelPlannerRepository = new TravelPlannerRepository(getActivity().getApplicationContext());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddWishlistItemActivity.class);
                startActivity(intent);
            }
        });

        initRecyclerView();
        loadTripList();
        return view;
    }

    private void loadTripList(){
        wishlist = travelPlannerRepository.getWishList();
        wishlistListAdapter.setList(wishlist);
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = view.findViewById(R.id.rv_wishlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        wishlistListAdapter = new WishlistListAdapter(getActivity().getApplicationContext(), new WishlistListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(WishlistItem wishlistItem) {
            }
        });
        recyclerView.setAdapter(wishlistListAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper((simpleCallback));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int id = viewHolder.getBindingAdapterPosition();
            WishlistItem wishlistItem = wishlist.get(id);
            travelPlannerRepository.deleteWishListItem(wishlistItem);
            wishlist.remove(wishlistItem);
            wishlistListAdapter.setList(wishlist);
            wishlistListAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        travelPlannerRepository = new TravelPlannerRepository(getActivity().getApplicationContext());
        wishlist = travelPlannerRepository.getWishList();
        loadTripList();
    }

}