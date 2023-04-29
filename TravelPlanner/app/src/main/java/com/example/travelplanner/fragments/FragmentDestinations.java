package com.example.travelplanner.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.travelplanner.activities.AddDestinationActivity;
import com.example.travelplanner.activities.ShowDestination;
import com.example.travelplanner.adapter.DestinationListAdapter;
import com.example.travelplanner.model.Destination;
import com.example.travelplanner.model.TravelPlannerRepository;

import java.util.List;

public class FragmentDestinations extends Fragment {

    private View view;
    private TravelPlannerRepository travelPlannerRepository;
    private DestinationListAdapter destinationListAdapter;
    private List<Destination> destinationList;
    Button buttonAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_destinations, container, false);
        buttonAdd = view.findViewById(R.id.btnDestinations);
        travelPlannerRepository = new TravelPlannerRepository(getActivity().getApplicationContext());

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddDestinationActivity.class);
                startActivityForResult(i, 1);
            }
        });

        initRecyclerView();
        loadDestinationList();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void loadDestinationList(){
        travelPlannerRepository = new TravelPlannerRepository(getActivity().getApplicationContext());
        destinationList = travelPlannerRepository.getAllDestinations();
        destinationListAdapter.setDestinationList(destinationList);
        destinationListAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = view.findViewById(R.id.rv_destinations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        destinationListAdapter = new DestinationListAdapter(getActivity().getApplicationContext(), new DestinationListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Destination destination) {
                Intent intent = new Intent(getActivity(), ShowDestination.class);
                intent.putExtra("id", destination.getId()+"");
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(destinationListAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper((simpleCallback));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            loadDestinationList();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int id = viewHolder.getBindingAdapterPosition();
            Log.i("IDDDDDDDDDDDDDDDD", id+"");
            Destination destination = destinationList.get(id);
            travelPlannerRepository.deleteDestination(destination);
            destinationList.remove(destination);
            destinationListAdapter.setDestinationList(destinationList);
            destinationListAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        travelPlannerRepository = new TravelPlannerRepository(getActivity().getApplicationContext());
        destinationList = travelPlannerRepository.getAllDestinations();
        loadDestinationList();
    }
}