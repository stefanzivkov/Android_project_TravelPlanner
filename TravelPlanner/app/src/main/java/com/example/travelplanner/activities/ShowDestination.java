package com.example.travelplanner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelplanner.R;
import com.example.travelplanner.converter.DataConverter;
import com.example.travelplanner.model.Destination;
import com.example.travelplanner.model.TravelPlannerRepository;

public class ShowDestination extends AppCompatActivity implements SensorEventListener {

    ImageView imageView;
    TextView name, dateFrom, dateTo, description;
    Destination destination;
    TravelPlannerRepository travelPlannerRepository;
    MediaPlayer mySong;
    SensorManager sensorManager;
    Sensor sensor;
    private boolean isSensorAvailable, itIsNotFirstTime = false, musicOn;
    private float currentX, currentY, currentZ, lastX, lastY, lastZ;
    private float xDifference, yDifference, zDifference, shakeTreshold = 10f;
    ImageButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_destination);

        mySong = MediaPlayer.create(ShowDestination.this, R.raw.project_music);
        mySong.start();

        imageView = findViewById(R.id.iv_show_destination);
        name = findViewById(R.id.tv_show_naziv);
        dateFrom = findViewById(R.id.tv_show_date_from);
        dateTo = findViewById(R.id.tv_show_date_to);
        description = findViewById(R.id.tv_show_description);
        search = findViewById(R.id.btnShowSearch);
        travelPlannerRepository = new TravelPlannerRepository(getApplicationContext());

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isSensorAvailable = true;
        }else{
            Toast.makeText(getApplicationContext(), "Accelerometer is not available", Toast.LENGTH_SHORT);
            isSensorAvailable = false;
        }

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String id = extras.getString("id");
            destination = travelPlannerRepository.getDestination(Integer.parseInt(id));
            imageView.setImageBitmap(DataConverter.convertByteArray2Bitmap(destination.getImage()));
            name.setText(destination.getName());
            dateFrom.setText("From: "+(destination.getDatumOd().equals("Choose Date From")?"":destination.getDatumOd()));
            dateTo.setText("To: "+(destination.getDatumDo().equals("Choose Date To")?"":destination.getDatumDo()));
            description.setText(destination.getDescription());
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String term = destination.getName();
                if(!term.equals("")){
                    searchNet(term);
                }
            }
        });
    }

    private void searchNet(String word){
        try{
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, word);
            startActivity(intent);
        }catch (ActivityNotFoundException a){
            a.printStackTrace();
            searchNetCompat(word);
        }

    }

    private void searchNetCompat(String word){
        try{
            Uri uri = Uri.parse("http://www.google.com/#q="+ word);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.putExtra(SearchManager.QUERY, word);
            startActivity(intent);
        }catch (ActivityNotFoundException a){
            a.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        mySong.pause();
        super.onPause();
        if(isSensorAvailable) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onResume() {
        mySong.start();
        super.onResume();
        if(isSensorAvailable){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    protected void onStart() {
        mySong.start();
        super.onStart();
    }

    @Override
    protected void onRestart() {
        mySong.start();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        mySong.release();
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        currentX = event.values[0];
        currentY = event.values[1];
        currentZ = event.values[2];

        if(itIsNotFirstTime){
            xDifference = Math.abs(lastX - currentX);
            yDifference = Math.abs(lastY - currentY);
            zDifference = Math.abs(lastZ - currentZ);

            if((xDifference > shakeTreshold && yDifference > shakeTreshold) ||
                    (xDifference > shakeTreshold && zDifference > shakeTreshold) ||
                    (yDifference > shakeTreshold && zDifference > shakeTreshold)){
                    mySong.pause();
                    musicOn = false;
            }

            itIsNotFirstTime = false;
        }

        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;

        itIsNotFirstTime = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}