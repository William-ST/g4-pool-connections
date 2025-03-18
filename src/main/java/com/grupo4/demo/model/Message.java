package com.grupo4.demo.model;

import java.time.OffsetDateTime;

public class Message {

    private Long id;
    private Long userid;
    private String message;
    private OffsetDateTime createddate;

    public Message() {
    }

    public Message(Long userid, String message) {
        this.userid = userid;
        this.message = message;
    }

    public Message(Long id, Long userid, String message, OffsetDateTime createddate) {
        this.id = id;
        this.userid = userid;
        this.message = message;
        this.createddate = createddate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OffsetDateTime getCreateddate() {
        return createddate;
    }

    public void setCreateddate(OffsetDateTime createddate) {
        this.createddate = createddate;
    }
}
