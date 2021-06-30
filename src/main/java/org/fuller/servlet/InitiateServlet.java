package org.fuller.servlet;

import org.fuller.service.InitiateParamService;

import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;

public class InitiateServlet implements Servlet {
    public static final String SET_APPLICATION_RESULTS = "applicationResults";
    public static final String SET_GENDER_TYPES = "genderTypes";
    public static final String SET_MENU_RESULTS = "menuTypes";
    public static final String SET_ROLE_RESULTS = "roleTypes";
    public static final String SET_LEAVE_APPLICATION_RESULTS = "leaveApplicationTypes";

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ServletContext context = servletConfig.getServletContext();
        try {
            context.setAttribute(SET_APPLICATION_RESULTS, InitiateParamService.getInstance().getApplicationResult());
            context.setAttribute(SET_GENDER_TYPES, InitiateParamService.getInstance().getGenderType());
            context.setAttribute(SET_MENU_RESULTS, InitiateParamService.getInstance().getMenuType());
            context.setAttribute(SET_ROLE_RESULTS, InitiateParamService.getInstance().getRoleType());
            context.setAttribute(SET_LEAVE_APPLICATION_RESULTS, InitiateParamService.getInstance().getLeaveApplicationType());
        } catch (SQLException e) {
            // TODO: 2021/6/29
        }
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
