package com.example.instapp.classes;

public class Post {
    private String userName;
    private String userImage;
    private String post;
    private String postImage;
    public String date;


    public Post(String userName, String userImage, String post, String postImage,String date) {
        this.userName = userName;
        this.userImage = userImage;
        this.post = post;
        this.postImage = postImage;
        this.date = date;
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
