package org.fuller.entity;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class Grade {
    private int id;
    private String num;
    private int periodId;
    private String period;
    private int belongToCollegeId;
    private String belongToCollege;
    private int headTeacherId;
    private String headTeacher;

    public void parse(HttpServletRequest request) {
        String strId = request.getParameter("id");
        num = request.getParameter("num");
        String strPeriodId = request.getParameter("periodId");
        String strCollegeId = request.getParameter("collegeId");
        if (strId != null) {
            id = Integer.parseInt(strId);
        }
        if (strPeriodId != null) {
            periodId = Integer.parseInt(strPeriodId);
        }
        if (strCollegeId != null) {
            belongToCollegeId = Integer.parseInt(strCollegeId);
        }
    }
}
