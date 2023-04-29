package com.example.travelplanner.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelplanner.R;
import com.example.travelplanner.converter.DataConverter;
import com.example.travelplanner.model.Destination;
import com.example.travelplanner.model.TravelPlannerRepository;

import java.io.IOException;
import java.util.Calendar;

public class AddDestinationActivity extends AppCompatActivity {

    Button camera, gallery, save;
    TextView dateFrom, dateTo, description;
    EditText name;
    ImageView imageView;
    Bitmap bitmap;
    private TravelPlannerRepository travelPlannerRepository;

    final int CAMERA_INTENT = 51;
    final int GALLERY_INTENT = 52;
    final int PERMISSION_CODE = 53;
    final int WRITE_EXTERNAL = 54;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destination);

        travelPlannerRepository = new TravelPlannerRepository(getApplicationContext());

        camera = findViewById(R.id.btn_camera);
        gallery = findViewById(R.id.btn_gallery);
        dateFrom = findViewById(R.id.tv_date_from);
        dateTo = findViewById(R.id.tv_date_to);
        description = findViewById(R.id.ed_description);
        name = findViewById(R.id.et_destination_name);
        imageView = findViewById(R.id.iv_add_destination);
        save = findViewById(R.id.btnSaveDestination);

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(AddDestinationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String mesec = checkMonth(month);
                        String date = dayOfMonth+". "+ mesec +" "+year+".";
                        dateFrom.setText(date);
                    }
                },year, month, day);
                dialog.show();
            }
        });

        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(AddDestinationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String mesec = checkMonth(month);
                        String date = dayOfMonth+". "+ mesec +" "+year+".";
                        dateTo.setText(date);
                    }
                },year, month, day);
                dialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap != null) {
                    Destination destination = new Destination();
                    destination.setName(name.getText().toString());
                    destination.setDatumOd(dateFrom.getText().toString());
                    destination.setDatumDo(dateTo.getText().toString());
                    destination.setDescription(description.getText().toString());
                    destination.setImage(DataConverter.convertImage2ByteArray(bitmap));
                    saveNewDestination(destination);
                    Intent intent = new Intent();
                    setResult(1, intent);
                    finish();
                }else{
                    Toast.makeText(AddDestinationActivity.this, "Must choose picture", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private String checkMonth(int month){
        if(month == 1){
            return "Januar";
        }else if(month == 2){
            return "Februar";
        }else if(month == 3){
            return "Mart";
        }else if(month == 4){
            return "April";
        }else if(month == 5){
            return "Maj";
        }else if(month == 6){
            return "Jun";
        }else if(month == 7){
            return "Jul";
        }else if(month == 8){
            return "Avgust";
        }else if(month == 9){
            return "Septermbar";
        }else if(month == 10){
            return "Oktobar";
        }else if(month == 11){
            return "Novembar";
        }else {
            return "Decembar";
        }
    }

    private void saveNewDestination(Destination destination){
        travelPlannerRepository.insertDestination(destination);
    }

    public void pickFromGallery(View view){
        if(hasPermission(this , Manifest.permission.READ_EXTERNAL_STORAGE)){
            pickImage();
        }else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, PERMISSION_CODE);
        }
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }

    public void takePicture(View view){
        if(hasPermission(this, Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, CAMERA_INTENT);
            }
        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_INTENT);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CAMERA_INTENT:
                if(resultCode == Activity.RESULT_OK){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    if(bitmap != null){
                        imageView.setImageBitmap(bitmap);
                    }else{
                        Toast.makeText(AddDestinationActivity.this, "Bitmap is null", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AddDestinationActivity.this, "Result not ok", Toast.LENGTH_SHORT).show();
                }
                break;
            case GALLERY_INTENT:
                if(resultCode == Activity.RESULT_OK){
                    Uri imageUri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        if(bitmap != null){
                            imageView.setImageBitmap(bitmap);
                        }else{
                            Toast.makeText(AddDestinationActivity.this, "Bitmap is null", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private boolean hasPermission(Context context, String permission){
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_INTENT){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAMERA_INTENT);
                }
            }else{
                Toast.makeText(this, "Permission is Denied", Toast.LENGTH_SHORT).show();
            }

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                AlertDialog.Builder extraInfo = new AlertDialog.Builder(this);
                extraInfo.setTitle("Camera Permission is Required");
                extraInfo.setMessage("To Run this app, App need to access to camera");

                extraInfo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(AddDestinationActivity.this,
                                new String[]{Manifest.permission.CAMERA}, CAMERA_INTENT);
                    }
                });

                extraInfo.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AddDestinationActivity.this, "Permission is Denied", Toast.LENGTH_SHORT).show();
                    }
                });

                extraInfo.create().show();
            }
        }

        if(requestCode == PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickImage();
            }else{
                Toast.makeText(this, "Permission denied for gallery", Toast.LENGTH_SHORT);
            }

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                AlertDialog.Builder extraInfo = new AlertDialog.Builder(this);
                extraInfo.setTitle("Gallery Access is required.");
                extraInfo.setMessage("To Run this app, App need to access to gallery.");

                extraInfo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(AddDestinationActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
                    }
                });

                extraInfo.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AddDestinationActivity.this, "Permission is Denied", Toast.LENGTH_SHORT).show();
                    }
                });

                extraInfo.create().show();
            }
        }



    }
}