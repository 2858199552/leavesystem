package org.fuller.controller;

import org.fuller.entity.*;
import org.fuller.framework.GetMapping;
import org.fuller.framework.ModelAndView;
import org.fuller.service.CollegeService;
import org.fuller.service.GradeService;
import org.fuller.service.TeacherService;
import org.fuller.session.LeaveSession;
import org.fuller.unit.MessageUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GradeController {

    private enum Option {
        OPTION_QUERY,
        OPTION_ADD,
        OPTION_DO_ADD,
        OPTION_UPDATE,
        OPTION_DO_UPDATE,
        OPTION_DELETE,
        OPTION_HEADTEACHER,
        OPTION_DETAIL,
        OPTION_QUERY_HEADTEACHER,
        OPTION_DO_HEADTEACHER
    }

    @GetMapping("/grade")
    public ModelAndView option(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        LeaveSession leaveSession = (LeaveSession) session.getAttribute("leaveSession");
        int option = Integer.parseInt(request.getParameter("option"));
        try {
            switch (Option.values()[option]) {
                case OPTION_QUERY:
                    if (!leaveSession.hasPermission("baseInfo.grade.query")) {
                        return MessageUnit.setMessage("查询班级", "没有访问权限", "/grade?option=1", session);
                    }
                    return query(request, response);
                case OPTION_ADD:
                    return add(request, response);
                case OPTION_DO_ADD:
                    return doAdd(request, response);
                case OPTION_UPDATE:
                    return update(request, response);
                case OPTION_DO_UPDATE:
                    return doUpdate(request, response);
                case OPTION_HEADTEACHER:
                    return headTeacher(request, response);
                case OPTION_DETAIL:
                    return detail(request, response);
                case OPTION_QUERY_HEADTEACHER:
                    return queryHeadTeacher(request, response);
                case OPTION_DO_HEADTEACHER:
                    return doHeadTeacher(request, response);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ModelAndView("/grade/gradeList.html");
    }

    private ModelAndView query(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        LeaveSession leaveSession = (LeaveSession) request.getSession().getAttribute("leaveSession");
        String whereClause = getWhereClause(leaveSession);
        Grade grade = new Grade();
        grade.parse(request);
        List<Grade> grades = GradeService.getInstance().getAll(grade, whereClause);
        List<Period> periods = GradeService.getInstance().getPeriods();
        List<College> colleges = CollegeService.getInstance().getAll();
        return new ModelAndView("/grade/gradeList.html", Map.of("grades", grades, "leaveSession", leaveSession, "periods", periods, "colleges", colleges, "grade", grade));
    }

    private String getWhereClause(LeaveSession leaveSession) {
        int dataArea = leaveSession.getDataArea();
        StringBuilder whereClause = new StringBuilder();
        switch (dataArea) {
            case LeaveSession.DATA_AREA_PERSON:
                if (leaveSession.getUserType() == LeaveSession.USER_TYPE_STUDENT) {
                    Student student = leaveSession.getStudent();
                    whereClause.append("id = " + student.getGradeId());
                } else if (leaveSession.getUserType() == LeaveSession.USER_TYPE_TEACHER) {
                    whereClause.append("belongToCollegeId = " + leaveSession.getCollegeId());
                }
                break;
            case LeaveSession.DATA_AREA_GRADE:
                whereClause.append("id in (" + leaveSession.getManageGrades() + ")");
                break;
            case LeaveSession.DATA_AREA_COLLEGE:
                whereClause.append("belongToCollegeId = " + leaveSession.getCollegeId());
                break;
            case LeaveSession.DATA_AREA_SYSTEM:
                return null;
        }
        return whereClause.toString();
    }

    private ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        List<Period> periods = GradeService.getInstance().getPeriods();
        List<College> colleges = CollegeService.getInstance().getAll();
        return new ModelAndView("/grade/addForm.html", Map.of( "option", 2, "periods", periods, "colleges", colleges));
    }

    private ModelAndView doAdd(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        Grade grade = new Grade();
        grade.parse(request);
        if (GradeService.getInstance().add(grade)) {
            session.setAttribute("errors", Map.of("title", "添加班级", "message", "添加成功", "url", "/grade?option=0"));
        } else {
            session.setAttribute("errors", Map.of("title", "添加班级", "message", "添加失败", "url", "/grade?option=0"));
        }
        return new ModelAndView("redirect:/error");
    }

    private ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Grade grade = GradeService.getInstance().getById(id);
        List<Period> periods = GradeService.getInstance().getPeriods();
        List<College> colleges = CollegeService.getInstance().getAll();
        return new ModelAndView("/grade/updateForm.html", Map.of("option", 4, "grade", grade, "periods", periods, "colleges", colleges));
    }

    private ModelAndView doUpdate(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        Grade grade = new Grade();
        grade.parse(request);
        if (GradeService.getInstance().update(grade)) {
            session.setAttribute("errors", Map.of("title", "修改班级", "message", "修改成功", "url", "/grade?option=0"));
        } else {
            session.setAttribute("errors", Map.of("title", "修改班级", "message", "修改失败", "url", "/grade?option=0"));
        }
        return new ModelAndView("redirect:/error");
    }

    private ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Grade grade = GradeService.getInstance().getDetailById(id);
        return new ModelAndView("/grade/detail.html", "grade", grade);
    }

    private ModelAndView headTeacher(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        return new ModelAndView("/grade/headTeacher.html", "id", id);
    }

    private ModelAndView queryHeadTeacher(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String name = request.getParameter("teacherName");
        int id = Integer.parseInt(request.getParameter("id"));
        List<Teacher> teachers = TeacherService.getInstance().getByName(name);
        return new ModelAndView("/grade/headTeacher.html", Map.of("teachers", teachers, "defaultName", name, "id", id));
    }

    private ModelAndView doHeadTeacher(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        int gradeId = Integer.parseInt(request.getParameter("gradeId"));
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));
        if (GradeService.getInstance().setHeadTeacher(gradeId, teacherId)) {
            session.setAttribute("errors", Map.of("title", "指定班主任", "message", "指定成功", "url", "/grade?option=0"));
        } else {
            session.setAttribute("errors", Map.of("title", "指定班主任", "message", "指定失败", "url", "/grade?option=0"));
        }
        return new ModelAndView("redirect:/error");
    }
}