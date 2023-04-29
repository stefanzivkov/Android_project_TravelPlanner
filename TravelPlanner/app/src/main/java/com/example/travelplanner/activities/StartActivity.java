package com.example.travelplanner.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelplanner.R;

public class StartActivity extends AppCompatActivity {
    Animation zoom_in, zoom_out;
    ImageView imageView;
    TextView textView;
    ConnectivityManager connectivityManager;
    LocationManager locationManager;
    NetworkInfo networkInfo;
    boolean wifiConnected = false, mobileConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        createNotification();

        imageView = findViewById(R.id.iv_start_2);
        textView = findViewById(R.id.tv_start);

        zoom_in = AnimationUtils.loadAnimation(StartActivity.this, R.anim.anim_two);
        zoom_out = AnimationUtils.loadAnimation(StartActivity.this, R.anim.anim_three);

        imageView.startAnimation(zoom_in);
        textView.startAnimation(zoom_out);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3500);
    }

    private void createNotification(){

        Log.i("AAAAAAAAAAAAAAAAAAAAAAAAAAAA", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if((networkInfo != null && networkInfo.isConnected()) && (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))){
            wifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            Log.i("AAAAAAAAAAAAAAAAAAAAAAAAAAAA1", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }else if(!(networkInfo != null && networkInfo.isConnected()) && (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))){
            NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(StartActivity.this, "My notification1")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Notification")
                    .setContentText("You must turn on wi-fi or mobile internet.");
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(StartActivity.this);
            notificationManager.notify(0, builder.build());
            Log.i("AAAAAAAAAAAAAAAAAAAAAAAAAAAA2", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            NotificationChannel notificationChannel = new NotificationChannel("My notification1", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager1 = getSystemService(NotificationManager.class);
            notificationManager1.createNotificationChannel(notificationChannel);

        }else if((networkInfo != null && networkInfo.isConnected()) && !(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))){
            NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(StartActivity.this, "My notification2")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Notification")
                    .setContentText("You must turn on location on phone.");
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(StartActivity.this);
            notificationManager.notify(0, builder.build());

            NotificationChannel notificationChannel = new NotificationChannel("My notification2", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager1 = getSystemService(NotificationManager.class);
            notificationManager1.createNotificationChannel(notificationChannel);

        }else{
            NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(StartActivity.this, "My notification3")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Notification")
                    .setContentText("You must turn on location and internet.");
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(StartActivity.this);
            notificationManager.notify(0, builder.build());

            NotificationChannel notificationChannel = new NotificationChannel("My notification3", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager1 = getSystemService(NotificationManager.class);
            notificationManager1.createNotificationChannel(notificationChannel);

        }

    }
}