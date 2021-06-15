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
}
