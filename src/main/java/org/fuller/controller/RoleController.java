package org.fuller.controller;

import org.fuller.entity.Menu;
import org.fuller.entity.Role;
import org.fuller.entity.RoleType;
import org.fuller.framework.GetMapping;
import org.fuller.framework.ModelAndView;
import org.fuller.framework.PostMapping;
import org.fuller.service.MenuService;
import org.fuller.service.RoleService;
import org.fuller.servlet.InitiateServlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RoleController {
    private static final long serialVersionUID = 1;
//    private enum Option {
//        OPTION_QUERY,
//        OPTION_ADD,
//        OPTION_DO_ADD,
//        OPTION_UPDATE,
//        OPTION_DO_UPDATE,
//        OPTION_DELETE,
//        OPTION_AUTHORIZE,
//        OPTION_DO_AUTHORIZE,
//        OPTION_DETAIL
//    }
// 该方法见CollegeController演示
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
    public ModelAndView query(HttpServletRequest request) throws SQLException {
        List<Role> roles = RoleService.getInstance().getAll();
        List<RoleType> roleTypes = (List<RoleType>) request.getServletContext().getAttribute("roleTypes");
        return new ModelAndView("roleList.html", Map.of("roles", roles, "roleTypes", roleTypes));
    }

    @GetMapping("/role/add")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("addForm.html", "roleTypes", request.getServletContext().getAttribute("roleTypes"/* InitiateServlet.SET_ROLE_RESULTS */));
    }

    @PostMapping("/role/add")
    public ModelAndView doAdd(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {
        Role role = new Role();
        role.parse(request);
        if (RoleService.getInstance().add(role)) {
            session.setAttribute("errors", Map.of("title", "添加角色", "message", "添加成功", "url", "/role/query"));
        } else {
            session.setAttribute("errors", Map.of("title", "添加角色", "message", "添加失败", "url", "/role/query"));
        }
        return new ModelAndView("redirect:/error");
    }

    @GetMapping("/role/update")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int roleId = Integer.parseInt(request.getParameter("id"));
        Role role = RoleService.getInstance().getById(roleId);
        List<RoleType> roleTypes = (List<RoleType>) request.getServletContext().getAttribute("roleTypes");
        return new ModelAndView("updateForm.html", Map.of("role", role, "roleTypes", roleTypes));
    }

    @PostMapping("/role/update")
    public ModelAndView doUpdate(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {
        Role role = new Role();
        role.parse(request);
        if (RoleService.getInstance().update(role)) {
            session.setAttribute("errors", Map.of("title", "修改角色", "message", "修改成功", "url", "/role/query"));
        } else {
            session.setAttribute("errors", Map.of("title", "修改角色", "message", "修改失败", "url", "/role/query"));
        }
        return new ModelAndView("redirect:/error");
    }

    @GetMapping("/role/delete")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {
        int roleId = Integer.parseInt(request.getParameter("id"));
        if (RoleService.getInstance().deleteById(roleId)) {
            session.setAttribute("errors", Map.of("title", "删除角色", "message", "删除成功", "url", "/role/query"));
        } else {
            session.setAttribute("errors", Map.of("title", "删除角色", "message", "删除失败", "url", "/role/query"));
        }
        return new ModelAndView("redirect:/error");
    }

    @GetMapping("/role/authorize")
    public ModelAndView authorize(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        List<Menu> menus = MenuService.getInstance().getAll();
        int roleId = Integer.parseInt(request.getParameter("id"));
        Role role = RoleService.getInstance().getById(roleId);
        return new ModelAndView("authorize.html", Map.of("menus", menus, "role", role));
    }

    @PostMapping("/role/authorize")
    public ModelAndView doAuthorize(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {
        int roleId = Integer.parseInt(request.getParameter("id"));
        String[] permissions = request.getParameterValues("permission");
        if (RoleService.getInstance().updateRolePermissionById(roleId, permissions)) {
            session.setAttribute("errors", Map.of("title", "修改权限", "message", "修改成功", "url", "/role/query"));
        } else {
            session.setAttribute("errors", Map.of("title", "修改权限", "message", "修改失败", "url", "/role/query"));
        }
        return new ModelAndView("redirect:/error");
    }
//
//    @GetMapping("/role/detail")
//    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
//
//    }
    // endregion
}
