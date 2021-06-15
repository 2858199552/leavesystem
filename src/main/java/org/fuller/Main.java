package org.fuller;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.fuller.bean.User;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        HikariStart();
        tomcatStart();

        DatabaseInitializer.init();
    }

    private static void tomcatStart() throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.getInteger("port", 8080));
        tomcat.getConnector();

        Context ctx = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());
        WebResourceRoot resourceRoot = new StandardRoot(ctx);
        resourceRoot.addPreResources(new DirResourceSet(resourceRoot, "/WEB-INF/classes", new File("target/classes").getAbsolutePath(), "/"));
        ctx.setResources(resourceRoot);
        tomcat.start();
        tomcat.getServer().await();
    }

    private static void HikariStart() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(jdbcUsername);
        config.setPassword(jdbcPassword);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "100");
        config.addDataSourceProperty("maximumPoolSize", "10");
        ds = new HikariDataSource(config);
    }

    @Deprecated
    public static Connection openConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
        return conn;
    }

    static final String jdbcUrl = "jdbc:mysql://localhost:3306/leavesystem";
    static final String jdbcUsername = "root";
    static final String jdbcPassword = "password";

    public static DataSource ds;
}
