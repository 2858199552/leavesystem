package org.fuller.Interceptor;

import org.fuller.framework.HandlerInterceptor;
import org.fuller.framework.ModelAndView;
import org.fuller.page.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String pageNo = request.getParameter("pageNo");
        System.out.println("拦截器开始 pageNo" + pageNo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Page page = (Page) request.getAttribute("page");
        if (modelAndView != null) {
            modelAndView.addModel("page", page);
        }
    }
}
