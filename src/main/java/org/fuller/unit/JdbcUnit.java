package org.fuller.unit;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUnit {
    private JdbcUnit() {
        HikariStart();
    }
    private static JdbcUnit instance;
    private DataSource dataSource;

    private final String jdbcUrl = "jdbc:mysql://localhost:3306/leavesystem";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "password";

    static {
        instance = new JdbcUnit();
    }

    public static JdbcUnit getInstance() {
        return instance;
    }

    /**
     * 不建议直接获取dataSource
     * @return
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    public void HikariStart() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(jdbcUsername);
        config.setPassword(jdbcPassword);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "100");
        config.addDataSourceProperty("maximumPoolSize", "10");
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

//    @Deprecated
//    public static Connection openConnection() throws SQLException {
//        Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
//        return conn;
//    }
}
