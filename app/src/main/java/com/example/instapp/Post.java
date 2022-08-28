package com.example.instapp;

public class Post {
    private String name;
    private int imageResourceID;

    public static final Post[] posts={
            new Post("Hadi Ehab",R.drawable.hadi),
            new Post("Hadi Atef",R.drawable.nature)
    };

    private Post(String name,int imageResourceID){
        this.name=name;
        this.imageResourceID=imageResourceID;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceID() {
        return imageResourceID;
    }
}
