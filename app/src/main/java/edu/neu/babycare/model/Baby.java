package edu.neu.babycare.model;

public class Baby {
    public String babyName;
    public String babyBirthday;
    public String gender;

    public Baby() {
    }

    public Baby(String babyName, String babyBirthday, String gender) {
        this.babyName = babyName;
        this.babyBirthday = babyBirthday;
        this.gender = gender;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public String getBabyBirthday() {
        return babyBirthday;
    }

    public void setBabyBirthday(String babyBirthday) {
        this.babyBirthday = babyBirthday;
    }
}
