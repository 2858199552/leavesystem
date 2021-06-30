package org.fuller.controller;

import org.fuller.entity.Role;
import org.fuller.entity.RoleType;
import org.fuller.framework.GetMapping;
import org.fuller.framework.ModelAndView;
import org.fuller.framework.PostMapping;
import org.fuller.service.RoleService;
import org.fuller.servlet.InitiateServlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class RoleController {
    private static final long serialVersionUID = 1;
    private enum Option {
        OPTION_QUERY,
        OPTION_ADD,
        OPTION_UPDATE,
        OPTION_DELETE,
        OPTION_AUTHORIZE,
        OPTION_DO_ADD,
        OPTION_DO_UPDATE,
        OPTION_DO_AUTHORIZE,
        OPTION_DETAIL
    }

//    @GetMapping("/role")
//    public ModelAndView option(HttpServletRequest request, HttpServletResponse response) {
//        int option = Integer.parseInt(request.getParameter("option"));
//        switch (Option.values()[option]) {
//            case OPTION_QUERY:
//                query(request, response);
//                break;
//            case OPTION_ADD:
//                add(request, response);
//                break;
//            case OPTION_DO_ADD:
//                doAdd(request, response);
//                break;
//            case OPTION_UPDATE:
//                update(request, response);
//                break;
//            case OPTION_DO_UPDATE:
//                doUpdate(request, response);
//                break;
//            case OPTION_DELETE:
//                delete(request, response);
//                break;
//            case OPTION_AUTHORIZE:
//                authorize(request, response);
//                break;
//            case OPTION_DO_AUTHORIZE:
//                doAuthorize(request, response);
//                break;
//            case OPTION_DETAIL:
//                detail(request, response);
//                break;
//        }
//        return new ModelAndView("roleList.html");
//    }
//
//    @PostMapping("/role")
//    public ModelAndView doOption() {
//        return new ModelAndView("roleList.html");
//    }

    // region option method
    @GetMapping("/role/query")
    public ModelAndView query() throws SQLException {
        List<Role> roles = RoleService.getInstance().getAll();
        return new ModelAndView("roleList.html", "roles", roles);
    }

    @GetMapping("/role/add")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("addForm.html", "roleTypes", request.getServletContext().getAttribute("roleTypes"/* InitiateServlet.SET_ROLE_RESULTS */));
    }

    @GetMapping("/role/doAdd")
    public ModelAndView doAdd(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Role role = new Role();
        role.parse(request);
        if (RoleService.getInstance().add(role)) {
            request.setAttribute("title", "添加角色");
            request.setAttribute("message", "添加成功");
            request.setAttribute("url", "/role/query");
            return new ModelAndView("error.html");
        } else {
            // TODO: 2021/6/30 直接看 6
            request.setAttribute("title", "添加角色");
            request.setAttribute("message", "添加失败");
            request.setAttribute("url", "/role/query");
            return new ModelAndView("error.html");
        }
    }

    @GetMapping("/role/query")
    public void update(HttpServletRequest request, HttpServletResponse response) {

    }

    @GetMapping("/role/doUpdate")
    public void doUpdate(HttpServletRequest request, HttpServletResponse response) {

    }

    @GetMapping("/role/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response) {

    }

    @GetMapping("/role/authorize")
    public void authorize(HttpServletRequest request, HttpServletResponse response) {

    }

    @GetMapping("/role/doAuthorize")
    public void doAuthorize(HttpServletRequest request, HttpServletResponse response) {

    }

    @GetMapping("/role/detail")
    public void detail(HttpServletRequest request, HttpServletResponse response) {

    }
    // endregion
}
