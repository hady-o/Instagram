package com.example.instapp.classes;

public class User {
    String id;
    String name;
    String email;
    String Photo;

    public User(String id, String name, String email, String photo) {
        this.id = id;
        this.name = name;
        this.email = email;
        Photo = photo;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return Photo;
    }
}
