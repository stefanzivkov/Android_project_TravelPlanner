package com.example.travelplanner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.travelplanner.R;
import com.example.travelplanner.fragments.FragmentDestinations;
import com.example.travelplanner.fragments.FragmentWishlist;
import com.example.travelplanner.retrofit.DataModel;
import com.example.travelplanner.retrofit.MyAPICall;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        Button btnDestinations = findViewById(R.id.btnFragment1);
        Button btnWishlist = findViewById(R.id.btnFragment2);

        replaceFragment(new FragmentDestinations());

        btnDestinations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentDestinations());
            }
        });

        btnWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentWishlist());
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {

                    case R.id.nav_add1:
                        Intent i = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_add2:
                        callApi();
                        break;
                    default:
                        return true;

                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void callApi(){
        Retrofit retrofit1 = new Retrofit.Builder().baseUrl("https://backend-omega-seven.vercel.app/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyAPICall myAPICall = retrofit1.create(MyAPICall.class);
        Call<List<DataModel>> call = myAPICall.getData();
        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                final List<DataModel> dataModel = response.body();
                Toast.makeText(MainActivity.this, dataModel.get(0).getQuestion()+" \n ->"+dataModel.get(0).getPunchline(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something gone wrong.", Toast.LENGTH_SHORT);
            }
        });
    }
}