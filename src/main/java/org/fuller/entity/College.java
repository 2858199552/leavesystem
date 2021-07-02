package org.fuller.entity;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class College {
    private int id;
    private String name;
    private String num;
    private String phone;

    public void parse(HttpServletRequest request) {
        String strId = request.getParameter("id");
        if (strId != null) {
            id = Integer.parseInt(strId);
        }
        name = request.getParameter("name");
        num = request.getParameter("num");
        phone = request.getParameter("phone");
    }
}
