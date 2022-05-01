package edu.neu.babycare;

import android.app.Application;

public class MyApplication extends Application {
    private static MyApplication mApp;
    private String loginUserName;
    private int score;
    private boolean[] videoCheckedList = new boolean[3];
    private String babyName;


    public static MyApplication getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean[] getVideoCheckedList() {
        return videoCheckedList;
    }

    public void setVideoCheckedList(boolean checked, int index) {
        if (index < 0 || index >=3) return;
        this.videoCheckedList[index] = checked;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

}
