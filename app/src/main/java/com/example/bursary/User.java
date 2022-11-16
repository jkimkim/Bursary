package com.example.bursary;

import java.io.Serializable;

public class User implements Serializable {
    public String name, email, date;

    public User(){

    }
    public User(String name, String email, String date){
        this.date = date;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

}

