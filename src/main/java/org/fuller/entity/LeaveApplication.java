package org.fuller.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class LeaveApplication {
    private int id;
    private int studentId;
    private byte type;
    private String reason;
    private Date beginTime;
    private Date endTime;
    private byte status;
}
