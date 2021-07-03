package org.fuller.page;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class PageFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String strPageNo = request.getParameter("pageNo");
        if (strPageNo != null) {
            int pageNo = Integer.parseInt(strPageNo);
            Page page = new Page();
            page.setPageNo(pageNo);
            request.setAttribute("page", page);
        }
//        判断request中是否有page属性
        Page page = (Page) request.getAttribute("page");
        if (page != null) {
            if (page.getPageRecords() == 0) {
                page.setPageRecords(0);
            } else {
                page.setPageRecords(page.getPageRecords() / Page.PAGE_SIZE + 1);
            }
//        创建一个PageUtil对象
            PageUtil pageUtil = new PageUtil();
            pageUtil.init(page, request);
            request.setAttribute("pageUtil", pageUtil);
//        forward到目标页面
//            String url = (String) request.getAttribute("forwardUrl");
//            request.getRequestDispatcher(url).forward(request, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
