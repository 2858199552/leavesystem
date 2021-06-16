package org.fuller.entity;

import lombok.Data;

@Data
public class Teacher {
    private int id;
    private String name;
    private String num;
    private String password;
    private int collegeId;
}
