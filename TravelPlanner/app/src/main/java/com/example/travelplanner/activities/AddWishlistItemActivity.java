package com.example.travelplanner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelplanner.R;
import com.example.travelplanner.model.TravelPlannerRepository;
import com.example.travelplanner.model.WishlistItem;

public class AddWishlistItemActivity extends AppCompatActivity {

    TextView longitude, latitude, name;
    static double longi = 0, lat = 0;
    Button save, location;
    TravelPlannerRepository travelPlannerRepository;
    LocationManager locationGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wishlist_item);

        travelPlannerRepository = new TravelPlannerRepository(getApplicationContext());

        save = findViewById(R.id.btnWishlistSave);
        location = findViewById(R.id.btnLocation);
        longitude = findViewById(R.id.tv_longitude);
        latitude = findViewById(R.id.tv_latitude);
        name = findViewById(R.id.ed_wishlistItem_name);
        locationGPS = (LocationManager) getSystemService(LOCATION_SERVICE);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(locationGPS.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Intent intent = new Intent(AddWishlistItemActivity.this, MapsActivity3.class);
                    intent.putExtra("id", 7);
                    startActivity(intent);
//                }else{
//                    Toast.makeText(AddWishlistItemActivity.this, "Turn on location.", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WishlistItem wishlistItem = new WishlistItem();
                if(longi == 0 && lat == 0){
                    Toast.makeText(AddWishlistItemActivity.this, "Must pick location.", Toast.LENGTH_SHORT).show();
                    longitude.setText(longi + "");
                    latitude.setText(lat + "");
                }else {
                    wishlistItem.setName(name.getText().toString());
                    wishlistItem.setLatitude(lat);
                    wishlistItem.setLongitude(longi);
                    travelPlannerRepository.insertWishListItem(wishlistItem);
                    longitude.setText(longi + "");
                    latitude.setText(lat + "");
                    Toast.makeText(AddWishlistItemActivity.this, "Added destination on wislist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}