package com.example.balltry1java.ui.board;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.balltry1java.R;
import com.example.balltry1java.Utils.GameResult;
import com.example.balltry1java.ui.GameWin;

import java.util.concurrent.TimeUnit;

import static com.example.balltry1java.Utils.GamePostions.GAMEPLAYING;
import static com.example.balltry1java.Utils.GamePostions.GAMESTART;
import static com.example.balltry1java.Utils.GamePostions.GAMESTOP;
import static com.example.balltry1java.Utils.MusicActions.PAUSE;
import static com.example.balltry1java.Utils.MusicActions.PLAY;
import static com.example.balltry1java.Utils.MusicActions.STOP;
import static com.example.balltry1java.Utils.constants.BallRadius;
import static com.example.balltry1java.Utils.constants.CenterDistance;
import static com.example.balltry1java.Utils.constants.HoleRadius;
import static com.example.balltry1java.Utils.constants.SecondMuliplyByScore;


@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class BoardGameActivity extends AppCompatActivity implements SensorEventListener2 {


    private long score = 0;

    private SensorManager sensorManager;

    private float xPos, xAccel, xVel = 0.0f;
    private float yPos, yAccel, yVel = 0.0f;
    private float xMax, yMax;
    private float centerx;
    private float centery;

    Bitmap hole;


    Boolean gotWin = false;
    //Game Position
    String gamePosotion = GAMESTART;

    MediaPlayer mediaPlayer;

    Long startTime, stopTime;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_game);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        BallView ballView = new BallView(this);
        setContentView(ballView);


        //Getting Display size and setting board size
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        xMax = (float) size.x - 100;
        yMax = (float) size.y - 100;


        //finding center
        centerx = xMax / 2;
        centery = yMax / 2;


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Start time
        startTime = System.currentTimeMillis();


        handleMediaPlayer(PLAY);

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }


    @Override
    public void onFlushCompleted(Sensor sensor) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    //Playing game
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            gamePosotion = GAMEPLAYING;


            xAccel = event.values[0];
            yAccel = -event.values[1];


            updateBall();


        }
    }

    private void updateBall() {
        float frameTime = 0.666f;
        xVel += (xAccel * frameTime);
        yVel += (yAccel * frameTime);

        float xS = (xVel / 2) * frameTime;
        float yS = (yVel / 2) * frameTime;

        xPos -= xS;
        yPos -= yS;

        if (xPos > xMax) {
            xPos = xMax;
        } else if (xPos < 0) {
            xPos = 0;
        }

        if (yPos > yMax) {
            yPos = yMax;
        } else if (yPos < 0) {
            yPos = 0;
        }

        CheckUserWin();
    }

    private void CheckUserWin() {
        Boolean ConditionOFX = (xPos > (centerx - CenterDistance)) && (xPos < (centerx + CenterDistance));
        Boolean ConditionOfY = (yPos > (centery - CenterDistance)) && (yPos < (centery + CenterDistance));


        if (ConditionOFX && ConditionOfY) {

            if (!gotWin) {
                gotWin = true;
                gamePosotion = GAMESTOP;

                //Stop Time
                stopTime = System.currentTimeMillis();

                //Calculate Time Taken By User
                CalculateScore();


            }
        }
    }

    private class BallView extends View {

        public BallView(Context context) {
            super(context);

            //ball = Bitmap.createScaledBitmap(ballSrc, 100, 100, true);



        }


        @SuppressLint("ResourceAsColor")
        @Override
        protected void onDraw(Canvas canvas) {

            //playing game
            if (!gotWin) {
                drawBallUsingCanvas(canvas, Color.RED, xPos, yPos, BallRadius);
            }
            //game at start of game
            else if (gamePosotion.equals(GAMESTART)) {
                drawBallUsingCanvas(canvas, Color.RED, 0, 0, BallRadius);
            }
            //if game is ended
            else {
                drawBallUsingCanvas(canvas, R.color.white, 0, 0, 0);
            }


            try {
                Bitmap HoleSrc = BitmapFactory.decodeResource(getResources(), R.drawable.hole);
                final int HoleWidth = 130;
                final int HoleHeight = 130;
                hole = Bitmap.createScaledBitmap(HoleSrc, HoleWidth, HoleHeight, true);

                canvas.drawBitmap(hole, centerx, centery, null);
            } catch (Exception e) {
                e.printStackTrace();

                drawBallUsingCanvas(canvas, R.color.black, centerx, centery,  HoleRadius);
            }




            invalidate();

        }
    }


    private void drawBallUsingCanvas(Canvas canvas, int p, float xPos, float yPos, int ballRadius) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(p);
        paint.setFakeBoldText(true);

        @SuppressLint("DrawAllocation") Paint wonGame = new Paint(Paint.ANTI_ALIAS_FLAG);
        wonGame.setColor(p);
        canvas.drawCircle(xPos, yPos, ballRadius, paint);
    }


    //Stop game
    private void CalculateScore() {
        Log.e("Time", "Start time :-" + startTime + "\n End time " + stopTime + "\n");
        long TotalTimeTaken = stopTime - startTime;
        long timeTakenInSec = TimeUnit.MILLISECONDS.toSeconds(TotalTimeTaken);

        score = 500 - (timeTakenInSec * SecondMuliplyByScore);

        GameResult.setStaticScore(String.valueOf(score));
        GameResult.setStaticTakenTime(String.valueOf(timeTakenInSec));

        //Toast.makeText(this, "You won , Your Score is  " + score, Toast.LENGTH_SHORT).show();


        Intent wonGameIntent = new Intent(this, GameWin.class);
        wonGameIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(wonGameIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handleMediaPlayer(PAUSE);
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);

        handleMediaPlayer(STOP);
        super.onStop();
    }

    private void handleMediaPlayer(String action) {
        if (action.equals(PLAY)) {
            mediaPlayer = MediaPlayer.create(this, R.raw.gamemusic);
            mediaPlayer.start();
        } else if (action.equals(PAUSE)) {
            mediaPlayer.pause();
        } else if (action.equals(STOP)) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        } else {
            Toast.makeText(this, "Invalid option of media player is choosed", Toast.LENGTH_SHORT).show();
        }
    }
}