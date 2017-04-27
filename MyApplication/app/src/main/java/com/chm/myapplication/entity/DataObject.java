package com.chm.myapplication.entity;

import java.util.Date;

public class DataObject {
    private Date date;
    private int value;

    public DataObject(Date date, int value) {
        this.date = new Date(date.getTime());
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public int getValue() {
        return value;
    }
}