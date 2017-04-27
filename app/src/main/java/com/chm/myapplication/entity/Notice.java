package com.chm.myapplication.entity;

/**
 * Created by ason on 2016/12/28.
 */
public class Notice {
    private int id;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Notice(int id, String content) {
        this.id = id;
        this.content = content;
    }
}
