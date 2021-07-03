package org.fuller.controller;

import org.fuller.framework.GetMapping;
import org.fuller.framework.ModelAndView;
import org.fuller.session.LeaveSession;
import org.fuller.unit.MessageUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class TeacherController {
    private enum Option {
        OPTION_QUERY,
        OPTION_ADD,
        OPTION_DO_ADD,
        OPTION_UPDATE,
        OPTION_DO_UPDATE,
        OPTION_ROLE,
        OPTION_DO_ROLE,
        OPTION_DETAIL
    }

    @GetMapping("/teacher")
    public ModelAndView option(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        LeaveSession leaveSession = (LeaveSession) session.getAttribute("leaveSession");
        int option = Integer.parseInt(request.getParameter("option"));
        try {
            switch (Option.values()[option]) {
                case OPTION_QUERY:
//                    if (!leaveSession.hasPermission("baseInfo.grade.query")) {
//                        return MessageUnit.setMessage("查询班级", "没有访问权限", "/grade?option=1", session);
//                    }
                    return query(request, response);
                case OPTION_ADD:
                    return add(request, response);
                case OPTION_DO_ADD:
                    return doAdd(request, response);
                case OPTION_UPDATE:
                    return update(request, response);
                case OPTION_DO_UPDATE:
                    return doUpdate(request, response);
                case OPTION_DETAIL:
                    return detail(request, response);
                case OPTION_ROLE:
                    return role(request, response);
                case OPTION_DO_ROLE:
                    return doRole(request, response);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ModelAndView("/grade/list.html");
    }

    private ModelAndView query(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    private ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
        return null;

    }

    private ModelAndView doAdd(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        return null;

    }

    private ModelAndView update(HttpServletRequest request, HttpServletResponse response) {
        return null;

    }

    private ModelAndView doUpdate(HttpServletRequest request, HttpServletResponse response) {
        return null;

    }

    private ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        return null;

    }

    private ModelAndView role(HttpServletRequest request, HttpServletResponse response) {
        return null;

    }

    private ModelAndView doRole(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
