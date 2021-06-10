package org.fuller.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fuller.controller.IndexController;
import org.fuller.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebServlet(urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, GetDispatcher> getMappings = new HashMap<>();

    private Map<String, PostDispatcher> postMapping = new HashMap<>();

    // 可指定package并自动扫描
    private List<Class<?>> controllers = List.of(IndexController.class, UserController.class);

    private ViewEngine viewEngine;

    /**
     * 当Servlet容器创建当前Servlet实例后，会自动调用init(ServletConfig)方法
     */
    @Override
    public void init() throws ServletException {
        logger.info("info{}...", getClass().getSimpleName());
        // TODO: 2021/6/9
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp, this.getMappings);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp, this.postMapping);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp, Map<String, ? extends AbstractDispatcher> dispatcherMap) throws ServletException, IOException {
        // TODO: 2021/6/9
    }

    private static final Set<Class<?>> supportedGetParameterTypes = Set.of(int.class, long.class, boolean.class, String.class, HttpServletRequest.class,
            HttpServletResponse.class, HttpSession.class);

    private static final Set<Class<?>> supportedPostParameterTypes = Set.of(HttpServletRequest.class, HttpServletResponse.class, HttpSession.class);
}

abstract class AbstractDispatcher {
    public abstract ModelAndView invoke(HttpServletRequest request, HttpServletResponse response) throws IOException, ReflectiveOperationException;
}

class GetDispatcher extends AbstractDispatcher {
    final Object instance;
    final Method method;
    final String[] parameterNames;
    final Class<?>[] parameterClasses;

    public GetDispatcher(Object instance, Method method, String[] parameterNames, Class<?>[] parameterClasses) {
        super();
        this.instance = instance;
        this.method = method;
        this.parameterNames = parameterNames;
        this.parameterClasses = parameterClasses;
    }

    @Override
    public ModelAndView invoke(HttpServletRequest request, HttpServletResponse response) throws IOException, ReflectiveOperationException {
        Object[] arguments = new Object[parameterClasses.length];
        for (int i = 0; i < parameterClasses.length; i++) {
            String parameterName = parameterNames[i];
            Class<?> parameterClass = parameterClasses[i];
            if (parameterClass == HttpServletRequest.class) {
                arguments[i] = request;
            } else if (parameterClass == HttpServletResponse.class) {
                arguments[i] = response;
            } else if (parameterClass == HttpSession.class) {
                arguments[i] = request.getSession();
            } else if (parameterClass == int.class) {
                arguments[i] = Integer.valueOf(getOrDefault(request, parameterName, "0"));
            } else if (parameterClass == long.class) {
                arguments[i] = Long.valueOf(getOrDefault(request, parameterName, "0"));
            } else if (parameterClass == boolean.class) {
                arguments[i] = Boolean.valueOf(getOrDefault(request, parameterName, "false"));
            } else if (parameterClass == String.class) {
                arguments[i] = String.valueOf(getOrDefault(request, parameterName, ""));
            } else {
                throw new RuntimeException("Missing handler for type: " + parameterClass);
            }
        }
        return (ModelAndView) this.method.invoke(this.instance, arguments);
    }

    private String getOrDefault(HttpServletRequest request, String name, String defaultValue) {
        String s = request.getParameter(name);
        return s == null ? defaultValue : s;
    }
}

class PostDispatcher extends AbstractDispatcher {
    final Object instance;
    final Method method;
    final Class<?>[] parameterClasses;
    final ObjectMapper objectMapper;

    public PostDispatcher(Object instance, Method method, Class<?>[] parameterClasses, ObjectMapper objectMapper) {
        this.instance = instance;
        this.method = method;
        this.parameterClasses = parameterClasses;
        this.objectMapper = objectMapper;
    }

    @Override
    public ModelAndView invoke(HttpServletRequest request, HttpServletResponse response) throws IOException, ReflectiveOperationException {
        // TODO: 2021/6/9
        return null;
    }
}