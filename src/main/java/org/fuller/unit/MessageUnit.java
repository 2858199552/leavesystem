package org.fuller.unit;

import org.fuller.framework.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class MessageUnit {
    public static ModelAndView setMessage(String title, String message, String url, HttpSession session) {
        session.setAttribute("errors", Map.of("title", title, "message", message, "url", url));
        return new ModelAndView("redirect:/error");
    }

    public static Map<String, Object> showMessage(HttpSession session) {
        return (Map<String, Object>) session.getAttribute("errors");
    }
}
