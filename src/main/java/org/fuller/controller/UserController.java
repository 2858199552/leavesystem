package org.fuller.controller;

import org.fuller.bean.User;
import org.fuller.framework.GetMapping;
import org.fuller.framework.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {

    private Map<String, User> userDatabase = new HashMap<>() {
        {
            List<User> users = List.of(
                    new User("111@qq.com", "sss", "sss", "qwe")
            );
            users.forEach(user -> put(user.email, user));
        }
    };

    @GetMapping("/hello")
    public ModelAndView hello() {
        return new ModelAndView("/index.html");
    }
}
