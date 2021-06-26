package org.fuller.controller;

import org.fuller.bean.SignInBean;
import org.fuller.bean.User;
import org.fuller.framework.GetMapping;
import org.fuller.framework.ModelAndView;
import org.fuller.framework.PostMapping;
import org.fuller.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController {

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
    public ModelAndView login() {
        return new ModelAndView("login.html");
    }

    @PostMapping("/login")
    public ModelAndView doLogin(SignInBean bean, HttpServletResponse response, HttpSession session) throws Exception {
        // TODO: 2021/6/26 按照老师要求：需要检测是否是超级管理员 video:2/2/48:00
        try {
            User user = userService.getUserByEmail(bean.email);
            session.setAttribute(KEY_USER, user);
        } catch (Exception e) {
            return new ModelAndView("login.html");
        }
        return new ModelAndView("redirect:/index");
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
    public ModelAndView topPage() {
        return new ModelAndView("top.html");
    }

    @GetMapping("/left")
    public ModelAndView leftPage() {
        return new ModelAndView("left.html");
    }

    @GetMapping("/right")
    public ModelAndView rightPage() {
        return new ModelAndView("right.html");
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

    @GetMapping("/form")
    public ModelAndView formPage() {
        return new ModelAndView("form.html");
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
