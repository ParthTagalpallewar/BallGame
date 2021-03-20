package com.example.balltry1java.Utils;

public class GameResult {

    private  static String staticTakenTime = null;
    private  static String staticScore = null;

    public static String getStaticTakenTime() {
        return staticTakenTime;
    }

    public static void setStaticTakenTime(String staticTakenTime) {
        GameResult.staticTakenTime = staticTakenTime;
    }

    public static String getStaticScore() {
        return staticScore;
    }

    public static void setStaticScore(String staticScore) {
        GameResult.staticScore = staticScore;
    }
}
