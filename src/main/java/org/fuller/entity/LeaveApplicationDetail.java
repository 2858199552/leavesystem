package org.fuller.entity;

import lombok.Data;

@Data
public class LeaveApplicationDetail {
    private int id;
    private int applicationId;
    private int teacherId;
    private byte result;
    private String opinion;
}
