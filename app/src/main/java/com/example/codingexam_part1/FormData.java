package com.example.codingexam_part1;

public class FormData {
    private String fullName;
    private String email;
    private String mobileNumber;
    private String dob;
    private String gender;
    private int age;

    public FormData(String fullName, String email, String mobileNumber, String dob, String gender, int age) {
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.dob = dob;
        this.gender = gender;
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}
