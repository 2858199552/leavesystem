package org.fuller.controller;

import org.fuller.entity.College;
import org.fuller.entity.Grade;
import org.fuller.entity.Period;
import org.fuller.framework.GetMapping;
import org.fuller.framework.ModelAndView;
import org.fuller.service.CollegeService;
import org.fuller.service.GradeService;

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
        OPTION_DETAIL
    }

    @GetMapping("/grade")
    public ModelAndView option(HttpServletRequest request, HttpServletResponse response) {
        int option = Integer.parseInt(request.getParameter("option"));
        try {
            switch (Option.values()[option]) {
                case OPTION_QUERY:
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
                    break;
                case OPTION_DETAIL:
                    return detail(request, response);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ModelAndView("/grade/gradeList.html");
    }

    private ModelAndView query(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        List<Grade> grades = GradeService.getInstance().getAll();
        return new ModelAndView("/grade/gradeList.html", "grades", grades);
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
}
