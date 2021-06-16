package org.fuller.entity;

import lombok.Data;

@Data
public class Student {
    private int id;
    private String name;
    private String num;
    private String password;
    private int gradeId;
    private int gender;
}
