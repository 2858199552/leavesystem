package org.fuller.framework;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(urlPatterns = {"/favicon.ico", "/static/*"})
public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext ctx = req.getServletContext();
        String urlPath = req.getRequestURI().substring(ctx.getContextPath().length());
        String filePath = ctx.getRealPath(urlPath);
        if (filePath == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Path path = Paths.get(filePath);
        if (!path.toFile().isFile()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String mime = Files.probeContentType(path);
        if (mime == null) {
            mime = "application/octet-stream";
        }
        resp.setContentType(mime);
        OutputStream output = resp.getOutputStream();
        try(InputStream input = new BufferedInputStream(new FileInputStream(filePath))) {
            input.transferTo(output);
        }
        output.flush();
    }
}
