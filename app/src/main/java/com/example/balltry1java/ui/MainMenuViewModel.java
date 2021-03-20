package com.example.balltry1java.ui;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.ViewModel;

import com.example.balltry1java.ui.board.BoardGameActivity;

public class MainMenuViewModel extends ViewModel {

    public void move(Context ctx, Class<?> cls){
        Intent BoardGameIntent = new Intent(ctx, cls);
        ctx.startActivity(BoardGameIntent   );
    }
}
