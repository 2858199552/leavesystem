package org.fuller.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class PageUtil {
    private Page page;
    private String pageHref;
    private String firstHref;
    private String preHref;
    private String nextHref;
    private String endHref;

    public boolean init(Page page, HttpServletRequest request) {
        this.page = page;
        String servletPath = request.getServletPath();
        StringBuilder builder = new StringBuilder(servletPath + "?");
        Enumeration<String> names = request.getParameterNames();
        String name;
        String value;
        while (names.hasMoreElements()) {
            name = names.nextElement();
            if (!name.equals("page")) {
                value = request.getParameter(name);
                builder.append(name);
                builder.append("=");
                builder.append(value);
                builder.append("&");
            }
        }
        this.pageHref = builder.toString();
        firstHref = pageHref + "page=1";
        if (page.getPageNo() - 1 <= 0) {
            preHref = pageHref + "page=1";
        } else {
            preHref = pageHref + "page=" + (page.getPageNo() - 1);
        }
        if (page.getPageNo() >= page.getTotalPages()) {
            nextHref = pageHref + "page=" + page.getTotalPages();
        } else {
            nextHref = pageHref + "page=" + (page.getPageNo() + 1);
        }
        endHref = pageHref + "page=" + page.getTotalPages();
        return true;
    }

    public String isShow() {
        return this.page.getTotalPages() > 1 ? "" : "style=display:none";
    }

    public String hasPre() {
        return this.page.getPageNo() > 1 ? "" : "style=display:none";
    }

    public String hasNext() {
        return this.page.getPageNo() < page.getTotalPages() ? "" : "style=display:none";
    }
}
