package org.fuller.controller;

import org.fuller.framework.GetMapping;
import org.fuller.framework.ModelAndView;

import javax.servlet.http.HttpSession;

public class IndexController {

    @GetMapping("/index")
    public ModelAndView index(HttpSession session) {
        return new ModelAndView("");
    }
}
