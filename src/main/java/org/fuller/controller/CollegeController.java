package org.fuller.controller;

import org.fuller.entity.College;
import org.fuller.framework.GetMapping;
import org.fuller.framework.ModelAndView;
import org.fuller.service.CollegeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CollegeController {

    private enum Option {
        OPTION_QUERY,
        OPTION_ADD,
        OPTION_DO_ADD,
        OPTION_UPDATE,
        OPTION_DO_UPDATE,
        OPTION_DELETE,
        OPTION_DETAIL
    }

    @GetMapping("/college")
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
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ModelAndView("/college/collegeList.html");
    }


    private ModelAndView query(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        List<College> colleges = CollegeService.getInstance().getAll();
        return new ModelAndView("/college/collegeList.html", "colleges", colleges);
    }

    private ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/college/addForm.html", "option", 2);
    }

    private ModelAndView doAdd(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        College college = new College();
        college.parse(request);
        if (CollegeService.getInstance().add(college)) {
            session.setAttribute("errors", Map.of("title", "添加学院", "message", "添加成功", "url", "/college?option=0"));
        } else {
            session.setAttribute("errors", Map.of("title", "添加学院", "message", "添加失败", "url", "/college?option=0"));
        }
        return new ModelAndView("redirect:/error");
    }

    private ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        College college = CollegeService.getInstance().getById(id);
        return new ModelAndView("/college/updateForm.html", Map.of("option", 4, "college", college));
    }

    private ModelAndView doUpdate(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        College college = new College();
        college.parse(request);
        if (CollegeService.getInstance().update(college)) {
            session.setAttribute("errors", Map.of("title", "添加学院", "message", "添加成功", "url", "/college?option=0"));
        } else {
            session.setAttribute("errors", Map.of("title", "添加学院", "message", "添加失败", "url", "/college?option=0"));
        }
        return new ModelAndView("redirect:/error");
    }
}
