package com.example.instapp.classes;

public class Post {
    private String userId;
    private String userName;
    private String userEmail;
    private String userImage;
    private String post;
    private String postImage;
    public String date;


    public Post( String userId, String userName,String userEmail, String userImage, String post, String postImage,String date) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userImage = userImage;
        this.post = post;
        this.postImage = postImage;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }


    public String getUserImage() {
        return userImage;
    }

    public String getPost() {
        return post;
    }

    public String getPostImage() {
        return postImage;
    }
}
