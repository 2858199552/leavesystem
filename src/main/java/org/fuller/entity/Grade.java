package org.fuller.entity;

import lombok.Data;

@Data
public class Grade {
    private int id;
    private String num;
    private int belongToCollege;
    private int headTeacherId;
}
