package org.fuller.bean;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class User {
    private long id;
    private String email;
    private String password;
    private long createdAt;

    public User(String email, String password, long createdAt){
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getCreatedDateTime() {
        return Instant.ofEpochMilli(createdAt).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
