package com.example.travelplanner.activities;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.travelplanner.R;
import com.example.travelplanner.databinding.ActivityMapsBinding;
import com.example.travelplanner.model.TravelPlannerRepository;
import com.example.travelplanner.model.WishlistItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    TravelPlannerRepository travelPlannerRepository;
    List<WishlistItem> wishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        travelPlannerRepository = new TravelPlannerRepository(getApplicationContext());
        wishlist = travelPlannerRepository.getWishList();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        travelPlannerRepository = new TravelPlannerRepository(getApplicationContext());
        wishlist = travelPlannerRepository.getWishList();

        for(int i = 0; i < wishlist.size(); i++) {
            LatLng dest = new LatLng(wishlist.get(i).getLatitude(), wishlist.get(i).getLongitude());
            mMap.addMarker(new MarkerOptions().position(dest).title("Destination "+i));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(dest));
        }
    }
}