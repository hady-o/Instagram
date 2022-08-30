package com.example.instapp;

public class Post {
    private String userName;
    private int userImage;
    private String post;
    private int postImage;


    public Post(String userName, int userImage, String post, int postImage) {
        this.userName = userName;
        this.userImage = userImage;
        this.post = post;
        this.postImage = postImage;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserImage() {
        return userImage;
    }

    public String getPost() {
        return post;
    }

    public int getPostImage() {
        return postImage;
    }
}
