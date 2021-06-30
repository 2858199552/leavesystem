package org.fuller.entity;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class Role {
    private int id;
    private String name;
    private String remark;
    private int areaType;

    public void parse(HttpServletRequest request) {
        name = request.getParameter("name");
        remark = request.getParameter("remark");
        areaType = Integer.parseInt(request.getParameter("areaType"));
    }
}
