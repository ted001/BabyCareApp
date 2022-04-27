package edu.neu.babycare.model;

public class Video {
    public String videoName;
    public String url;
    public int points;
    public boolean isChecked;

    public Video(String videoName, String url, int points) {
        this.videoName = videoName;
        this.url = url;
        this.points = points;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
