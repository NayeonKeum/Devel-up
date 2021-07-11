package com.example.retrofit_ex;

public class PostInfo {
    private int __v;
    private String name, title, content, _id, like;
    public PostInfo(){}
    public PostInfo(String name, String title, String content, String like){
        this.name=name;
        this.title=title;
        this.content=content;
        this.like=like;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public int get__v() {
        return __v;
    }

    public String get_id() {
        return _id;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getLike() {
        return like;
    }
}
