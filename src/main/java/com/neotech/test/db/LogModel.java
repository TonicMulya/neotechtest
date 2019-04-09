package com.neotech.test.db;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "log")
public class LogModel {
    private Date date;

    public LogModel(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
