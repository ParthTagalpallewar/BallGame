package com.example.balltry1java.ui.maps;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.balltry1java.R;
import com.example.balltry1java.data.room.AppDatabase;
import com.example.balltry1java.data.room.entity.UserModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.LatLongMap);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();
        List<UserModel> adderUserModel = AppDatabase.getInstance(this).getNoteDao().getTopPlayers();

        for (int i = 0; i < adderUserModel.size(); i++) {

            String StringLoc = adderUserModel.get(i).getLocation();
            String[] fullLocation = StringLoc.split(",");
            Double latitude = 0.0;
            Double longitude = 0.0;


            if(i == 0){
               latitude  = Double.valueOf("29.999089");
               longitude =  Double.valueOf("75.3649013");
            }else if(i == 1){
                latitude  = Double.valueOf("29.959489");
                longitude =  Double.valueOf("75.3649413");
            }else if(i == 2){
                latitude  = Double.valueOf("29.459489");
                longitude =  Double.valueOf("75.3649413");
            } else{
                latitude = Double.valueOf(fullLocation[0]);
                longitude =  Double.valueOf(fullLocation[1]);
            }


            Log.e("Location",StringLoc);

            LatLng PlayerLocation = new LatLng(latitude, longitude);

            markerOptions.position(PlayerLocation);

            mMap.addMarker(markerOptions).setTitle(adderUserModel.get(i).getUserName() +"-"+adderUserModel.get(i).getScore());

        }
    }
}