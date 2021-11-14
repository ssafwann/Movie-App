package com.example.movieapp;

public class UserHelperClass {

    String username, password, age;
    String credits;

    public UserHelperClass() {
    }

    public UserHelperClass(String username, String password, String age, String credits) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.credits = credits;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }
}

