package edu.neu.babycare.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    public String userName;
    public String relationship;
    public String latestLoginTime;
    public String familyCode;

    public User() {
    }

    public User(String username, String relationship, String familyCode) {
        this.userName = username;
        this.relationship = relationship;
        this.familyCode = familyCode;
        this.latestLoginTime = getCurrentTime();
    }

    public String getUserName() {
        return userName;
    }

    public static String getCurrentTime() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        return ft.format(dNow);
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getFamilyCode() {
        return familyCode;
    }

    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
    }
}
