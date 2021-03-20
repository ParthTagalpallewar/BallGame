package com.example.balltry1java.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import androidx.lifecycle.ViewModel;


import com.example.balltry1java.data.room.AppDatabase;
import com.example.balltry1java.data.room.entity.UserModel;
import com.example.balltry1java.ui.MainMenu;

public class AuthViewModel extends ViewModel {


    public long signUpUser(Context ctx, String userName, String Password) {
        UserModel signUpModel = new UserModel();
        signUpModel.setUserName(userName);
        signUpModel.setPassword(Password);
        long adderUserModel = AppDatabase.getInstance(ctx).getNoteDao().insert(signUpModel);
        return adderUserModel;
    }


    public UserModel loginUser(Context ctx, String userName, String Password) {
        UserModel adderUserModel = AppDatabase.getInstance(ctx).getNoteDao().login(userName, Password);
        return adderUserModel;
    }

    public Boolean checkNameAndSetError(String data, EditText et) {
        if (TextUtils.isEmpty(data)) {
            et.setError("Please fill data here");
            return false;
        }
        return true;
    }

    public void sendUserToMainActivity(Context ctx) {
        Intent mainActivityIntent = new Intent(ctx, MainMenu.class);
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ctx.startActivity(mainActivityIntent);
    }

    public void GoToOtherAuth(Context ctx, GoToAnotherAuthEnum destination) {
        Class<?> destinationClass;

        if (destination == GoToAnotherAuthEnum.Login_2_SignUp) {
            destinationClass = SignUpActivity.class;
        } else {
            destinationClass = LoginActivity.class;
        }

        Intent destinationIntent = new Intent(ctx, destinationClass);
        destinationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ctx.startActivity(destinationIntent);
    }

    public enum GoToAnotherAuthEnum {
        Login_2_SignUp,
        SignUp_2_Login
    }
}
