package org.fuller.entity;

import lombok.Data;

@Data
public class Role {
    private int id;
    private String name;
    private String remark;
    private int areaType;
}
