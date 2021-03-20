package com.example.balltry1java.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balltry1java.R;
import com.example.balltry1java.Utils.user;
import com.example.balltry1java.data.room.entity.UserModel;

public class SignUpActivity extends AppCompatActivity {

    AuthViewModel viewModel;

    EditText userName, userPass;
    TextView loginSignUp;
    Button signUpBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sign_up);

        intiViews();

        viewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

        signUpBtn.setOnClickListener(v -> {
            SignUpUser();
        });


        loginSignUp.setOnClickListener(v ->
                viewModel.GoToOtherAuth(getApplicationContext(), AuthViewModel.GoToAnotherAuthEnum.SignUp_2_Login));

    }


    private void SignUpUser() {
        String name = userName.getText().toString();
        String password = userPass.getText().toString();

        if (viewModel.checkNameAndSetError(name, userName) && viewModel.checkNameAndSetError(password, userPass)) {
            long code = viewModel.signUpUser(this, name, password);


            UserModel loginUserModel = viewModel.loginUser(this, name, password);

            if (loginUserModel.getUserName() != null) {
                user.setStaticUserName(loginUserModel.getUserName());
                user.setStaticUserPassword(loginUserModel.getPassword());
                user.setStaticUserId(String.valueOf(loginUserModel.getId()));

                viewModel.sendUserToMainActivity(getApplicationContext());
            }

        }


    }

    private void intiViews() {
        userName = findViewById(R.id.signUpUserName);
        userPass = findViewById(R.id.signUpPassword);
        loginSignUp = findViewById(R.id.signUpLogin);
        signUpBtn = findViewById(R.id.signUpBtn);
    }
}