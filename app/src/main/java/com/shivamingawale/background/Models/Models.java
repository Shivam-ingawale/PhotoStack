package com.shivamingawale.background.Models;

public class Models {
    private String user, count, tags, url,userurl, id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private boolean liked=false;

    public Models() {
    }

    public boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public void setTitle(String title) {
        this.user = title;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return user;
    }

    public String getCount() {
        return count;
    }

    public String getTags() {
        return tags;
    }

    public String getUrl() {
        return url;
    }

    public String getUserurl() {
        return userurl;
    }

    public void setUserurl(String userurl) {
        this.userurl = userurl;
    }

    public Models(String user, String count, String tags, String url, String userurl, String id) {
        this.user = user;
        this.id=id;
        this.count = count;
        this.tags = tags;
        this.url = url;
        this.userurl = userurl;
    }

    public Models(String user, String count, String tags, String url, String userurl, boolean liked, String id) {
        this.user = user;
        this.count = count;
        this.tags = tags;
        this.id=id;
        this.url = url;
        this.userurl = userurl;
        this.liked = liked;
    }
}
