package org.fuller.controller;

import org.fuller.bean.SignInBean;
import org.fuller.bean.User;
import org.fuller.entity.Grade;
import org.fuller.entity.Role;
import org.fuller.entity.Student;
import org.fuller.entity.Teacher;
import org.fuller.framework.GetMapping;
import org.fuller.framework.ModelAndView;
import org.fuller.framework.PostMapping;
import org.fuller.service.*;
import org.fuller.session.LeaveSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {

    public enum Login_Sign {
        LOGIN_SIGN_IN,
        LOGIN_SIGN_OUT,
        LOGIN_SIGN_SUCCESS,
        LOGIN_SIGN_USERNAME_ERROR,
        LOGIN_SIGN_PASSWORD_ERROR,
        LOGIN_SIGN_OTHER_ERROR
    }
    public static final int ROLE_SIGN_HEADTEACHER = 2;
    public static final int ROLE_SIGN_FDY = 3;
    public static final int ROLE_SIGN_LANDER = 4;

    public static final String KEY_USER = "__user__";
    public static final String KEY_USER_ADMIN = "admin";

    private UserService userService = new UserService();

    @GetMapping("/")
    public ModelAndView index(HttpSession session) {
        User user = (User) session.getAttribute(KEY_USER);
        Map<String, Object> model = new HashMap<>();
        if (user != null) {
            model.put("user", model);
        }
        return new ModelAndView("index.html", model);
    }

    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest request , HttpSession session) {
//        当前MVC还不能处理/login?option=1,故用get:/loginout
//        int option = Integer.parseInt(request.getParameter("option"));
//        switch (Login_Sign.values()[option]) {
//            case LOGIN_SIGN_OUT:
//                session.invalidate();
//                break;
//        }
        return new ModelAndView("login.html");
    }

    @PostMapping("/login")
    public ModelAndView doLogin(/*SignInBean bean,*/ HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        System.out.println("this is post");
        Login_Sign login_sign = null;
        try {
//            String num = bean.num;
//            String password = bean.password;
            String num = request.getParameter("num");
            String password = request.getParameter("password");
            if (num.trim().length() == 5) {
                login_sign = teacherLogin(num, password, request, response);
            } else {
                login_sign = studentLogin(num, password, request, response);
            }
            if (login_sign != Login_Sign.LOGIN_SIGN_SUCCESS) {
                throw new RuntimeException();
            }
//            User user = userService.getUserByEmail(bean.email);
//            session.setAttribute(KEY_USER, user);
        } catch (Exception e) {
//                request.setAttribute("title", "login");
//                request.setAttribute("error", "sign_false");
//                request.getRequestDispatcher("/error.html").forward(request, response);
            String error = null;
            switch (login_sign) {
                case LOGIN_SIGN_PASSWORD_ERROR: error = "密码错误"; break;
                case LOGIN_SIGN_USERNAME_ERROR: error = "用户不存在"; break;
                case LOGIN_SIGN_OTHER_ERROR: error = "其他错误"; break;
            }
            return new ModelAndView("login.html", "error", error);
        }
        return new ModelAndView("redirect:/main");
    }

    // region login
    private Login_Sign teacherLogin(String num, String password, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Teacher teacher = TeacherService.getInstance().getByNum(num);
        if (teacher == null) {
            return Login_Sign.LOGIN_SIGN_USERNAME_ERROR;
        }
        if (!password.equals(teacher.getPassword())) {
//            这样可能更好，但还是和老师保持统一
//            throw new RuntimeException("密码错误");
            return Login_Sign.LOGIN_SIGN_PASSWORD_ERROR;
        }

        LeaveSession leaveSession = new LeaveSession();
        leaveSession.setCollegeId(teacher.getCollegeId());
        leaveSession.setUserType(LeaveSession.USER_TYPE_TEACHER);
        leaveSession.setTeacher(teacher);
        List<Role> roles = RoleService.getInstance().getRolesByUserId(teacher.getId());
        leaveSession.setRoles(roles);
        for (var role : roles) {
            if (role.getId() == ROLE_SIGN_HEADTEACHER) {
                leaveSession.setHeadTeacher(true);
                List<Grade> grades = GradeService.getInstance().getGradesByUserId(teacher.getId());
                StringBuilder strGrades = new StringBuilder();
                for (var grade : grades) {
                    strGrades.append(grade.getId());
                    strGrades.append(",");
                }
                strGrades.replace(strGrades.length() - 1, strGrades.length(), "");
                leaveSession.setManageGrades(strGrades.toString());
            } else if (role.getId() == ROLE_SIGN_FDY) {
                leaveSession.setFdy(true);
            } else if (role.getId() == ROLE_SIGN_LANDER) {
                leaveSession.setLander(true);
            }
        }

//        4. 查找权限
        leaveSession.setPermissions(PermissionService.getInstance().getPermissionByTeacherId(teacher.getId()));
        HttpSession session = request.getSession();
        session.setAttribute("leaveSession", leaveSession);
        return Login_Sign.LOGIN_SIGN_SUCCESS;
    }

    private Login_Sign studentLogin(String num, String password, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Student student = StudentService.getInstance().getByNum(num);
        if (student == null) {
            return Login_Sign.LOGIN_SIGN_USERNAME_ERROR;
        }
        if (!password.equals(student.getPassword())) {
//            这样可能更好，但还是和老师保持统一
//            throw new RuntimeException("密码错误");
            return Login_Sign.LOGIN_SIGN_PASSWORD_ERROR;
        }
        LeaveSession leaveSession = new LeaveSession();
        leaveSession.setUserType(LeaveSession.USER_TYPE_STUDENT);
        leaveSession.setStudent(student);
        leaveSession.setPermissions(PermissionService.getInstance().getPermissionByStudentId());
        HttpSession session = request.getSession();
        session.setAttribute("leaveSession", leaveSession);
        return Login_Sign.LOGIN_SIGN_SUCCESS;
    }
    // endregion

    @GetMapping("/loginOut")
    public ModelAndView loginOut(HttpSession session) {
//        session.invalidate();
        session.removeAttribute("leaveSession");
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register.html");
    }

    @PostMapping("/register")
    public ModelAndView doRegister(SignInBean bean, HttpServletResponse response, HttpSession session) throws IOException {
        // TODO: 2021/6/15
        return new ModelAndView("error.html");
    }


    //region Unit pages
    @GetMapping("/main")
    public ModelAndView mainPage() {
        return new ModelAndView("main.html");
    }

    @GetMapping("/top")
    public ModelAndView topPage(HttpSession session) {
        LeaveSession leaveSession = (LeaveSession) session.getAttribute("leaveSession");
        return new ModelAndView("top.html", "leaveSession", leaveSession);
    }

    @GetMapping("/left")
    public ModelAndView leftPage(HttpSession session) {
        LeaveSession leaveSession = (LeaveSession) session.getAttribute("leaveSession");
        return new ModelAndView("left.html", "leaveSession", leaveSession);
    }

    @GetMapping("/index")
    public ModelAndView indexPage() {
        return new ModelAndView("index.html");
    }

    @GetMapping("/default")
    public ModelAndView defaultPage() {
        return new ModelAndView("default.html");
    }

    @GetMapping("/imgtable")
    public ModelAndView imgtablePage() {
        return new ModelAndView("imgtable.html");
    }

    @GetMapping("/imglist")
    public ModelAndView imglistPage() {
        return new ModelAndView("imglist.html");
    }

    @GetMapping("/imglist1")
    public ModelAndView imglist1Page() {
        return new ModelAndView("imglist1.html");
    }

    @GetMapping("/tools")
    public ModelAndView toolsPage() {
        return new ModelAndView("tools.html");
    }

    @GetMapping("/filelist")
    public ModelAndView filelistPage() {
        return new ModelAndView("filelist.html");
    }

    @GetMapping("/tab")
    public ModelAndView tabPage() {
        return new ModelAndView("tab.html");
    }

    @GetMapping("/computer")
    public ModelAndView computerPage() {
        return new ModelAndView("computer.html");
    }

    @GetMapping("/error")
    public ModelAndView errorPage() {
        return new ModelAndView("error.html");
    }

    //endregion
}
