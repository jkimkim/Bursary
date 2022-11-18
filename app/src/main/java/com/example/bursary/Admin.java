package com.example.bursary;

import java.io.Serializable;

public class Admin implements Serializable {
    public String name, email;

    public Admin(){
    }
    public Admin(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}

