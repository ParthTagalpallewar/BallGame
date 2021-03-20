package com.example.balltry1java.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balltry1java.R;
import com.example.balltry1java.Utils.user;
import com.example.balltry1java.data.room.entity.UserModel;
import com.example.balltry1java.ui.MainMenu;

public class LoginActivity extends AppCompatActivity {

    AuthViewModel viewModel;

    EditText userName, userPass;
    TextView loginSignUp;
    Button loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        intiViews();

        viewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

        loginBtn.setOnClickListener(v -> loginUser());


        loginSignUp.setOnClickListener(v ->
                viewModel.GoToOtherAuth(getApplicationContext(), AuthViewModel.GoToAnotherAuthEnum.Login_2_SignUp));

    }


    private void loginUser() {
        String name = userName.getText().toString();
        String password = userName.getText().toString();

        if (viewModel.checkNameAndSetError(name, userName) && viewModel.checkNameAndSetError(password, userPass)) {
            UserModel loginUserModel = viewModel.loginUser(this, name, password);

            if(loginUserModel != null){
                if (loginUserModel.getUserName() != null) {
                    user.setStaticUserName(loginUserModel.getUserName());
                    user.setStaticUserPassword(loginUserModel.getPassword());
                    user.setStaticUserId(String.valueOf(loginUserModel.getId()));

                    viewModel.sendUserToMainActivity(getApplicationContext());
                }
            }else{
                Toast.makeText(this,"Username or password not match",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void intiViews() {
        userName = findViewById(R.id.login_userName);
        userPass = findViewById(R.id.login_userPass);
        loginSignUp = findViewById(R.id.login_sign_up);
        loginBtn = findViewById(R.id.login_btn);
    }
}