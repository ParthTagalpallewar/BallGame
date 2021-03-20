package com.example.balltry1java.ui;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.balltry1java.R;
import com.example.balltry1java.Utils.user;
import com.example.balltry1java.ui.auth.LoginActivity;
import com.example.balltry1java.ui.auth.SignUpActivity;
import com.example.balltry1java.ui.board.BoardGameActivity;
import com.example.balltry1java.ui.maps.MapsActivity;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MainMenu extends AppCompatActivity {

    Button loginScreen, SignUpScreen, Exit, Maps,logOut;
    ImageView play;

    MainMenuViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainMenuViewModel.class);


        Boolean fistCondion = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED;
        Boolean SecondCondion = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED;


        if (fistCondion && SecondCondion) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 4);
        }

        initViews();

        checkUserAuthComplete();

        playGame();

        loginUser();

        signUpUser();

        maps();

        exitGame();
    }

    private void maps() {
        Maps.setOnClickListener(v -> {
            move(getApplication(), MapsActivity.class);
        });
    }

    private void checkUserAuthComplete(){
        if (user.getStaticUserName() != null){
            loginScreen.setVisibility(View.INVISIBLE);
            SignUpScreen.setVisibility(View.INVISIBLE);
        }
    }

    private void exitGame() {

        logOut.setOnClickListener(v -> {
            user.setStaticUserId(null);
            user.setStaticUserName(null);
            user.setStaticUserPassword(null);

            Intent i = new Intent(this,MainMenu.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });



        Exit.setOnClickListener(v -> {


            this.finishAffinity();
        });
    }

    private void signUpUser() {
        SignUpScreen.setOnClickListener(v -> {
            move(getApplication(), SignUpActivity.class);
        });
    }

    private void loginUser() {
        loginScreen.setOnClickListener(v -> {
            move(getApplication(), LoginActivity.class);
        });
    }

    private void playGame() {
        play.setOnClickListener(v -> {

            if (user.getStaticUserName() != null){
                move(getApplication(), BoardGameActivity.class);
            }else{
                Toast.makeText(this,"Plese LoginIn or SignUp Before Playing Game",Toast.LENGTH_SHORT).show();
            }


        });
    }

    private void initViews() {

        loginScreen = findViewById(R.id.mainLogin);
        SignUpScreen = findViewById(R.id.mainSignUp);
        Exit = findViewById(R.id.mainExit);
        Maps = findViewById(R.id.mainMaps);
        play = findViewById(R.id.mainPlayGame);
        logOut = findViewById(R.id.logout);


    }

    public void move(Context ctx, Class<?> cls){
        Intent BoardGameIntent = new Intent(this, cls);
        this.startActivity(BoardGameIntent   );
    }
}