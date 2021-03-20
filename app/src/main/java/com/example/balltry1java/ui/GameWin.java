package com.example.balltry1java.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balltry1java.R;
import com.example.balltry1java.Utils.GameResult;
import com.example.balltry1java.Utils.user;
import com.example.balltry1java.data.room.AppDatabase;
import com.example.balltry1java.data.room.entity.UserModel;


public class GameWin extends AppCompatActivity implements LocationListener {

    TextView tvName, tvScore, tvtimeTaken, Gotomain;
    String loc;

    Boolean doUpdate = true;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_win);

        initViews();

        tvScore.setText("Your Score - " + GameResult.getStaticScore());
        tvName.setText("User Name - " + user.getStaticUserName());
        tvtimeTaken.setText("Time Taken - " + GameResult.getStaticTakenTime() + "Sec");


    }

    private void initViews() {

        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        Boolean fistCondion = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED;
        Boolean SecondCondion = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED;


        if (fistCondion && SecondCondion) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 4);
        } else {
            LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            Boolean status = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (status) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, this);
            } else {
                Toast.makeText(this, "Please turn on gps to continue", Toast.LENGTH_SHORT).show();
            }
        }


        tvName = findViewById(R.id.successName);
        tvScore = findViewById(R.id.successScore);
        tvtimeTaken = findViewById(R.id.succesTimeTaken);
        Gotomain = findViewById(R.id.Gotomain);
        Gotomain.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (doUpdate) {
            loc = (location.getLatitude() + "," + location.getLongitude());

            UserModel model = new UserModel();
            model.setId(Integer.valueOf(user.getStaticUserId()));
            model.setPassword(user.getStaticUserPassword());
            model.setUserName(user.getStaticUserName());
            model.setScore(GameResult.getStaticScore());


            model.setLocation(loc);

            AppDatabase.getInstance(this).getNoteDao().update(model);

            doUpdate = false;

        }
        Gotomain.setVisibility(View.VISIBLE);

        Gotomain.setOnClickListener(v -> {
            sendUserToMainActivity();
        });

    }

    public void sendUserToMainActivity() {
        Intent mainActivityIntent = new Intent(this, MainMenu.class);
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(mainActivityIntent);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}