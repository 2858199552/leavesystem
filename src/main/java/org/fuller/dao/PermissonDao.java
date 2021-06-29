package org.fuller.dao;

import org.fuller.entity.Menu;
import org.fuller.unit.JdbcUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class PermissonDao {
    public static final int STUDENT_ROLE = 1;
    private static PermissonDao instance;
    private PermissonDao(){};
    static {
        instance = new PermissonDao();
    }

    public static PermissonDao getInstance() {
        return instance;
    }

    public Set<String> getPermissionByTeacherId(int teacherId) throws SQLException {
        Set<String> set = new HashSet<>();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT permission FROM menus "
                    + "WHERE id in (SELECT rolemenurelations.menuId FROM rolemenurelations "
                    + "WHERE roleId in (SELECT roleid from userrolerelations WHERE userid = ?))")) {
                ps.setInt(1, teacherId);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                    String permission = result.getString(1);
                    set.add(permission);
                }
            }
        }
        return set;
    }

    public Set<String> getPermissionByStudentRoleId() throws SQLException{
        return getPermissionByRoleId(STUDENT_ROLE);
    }
    public Set<String> getPermissionByRoleId(int id) throws SQLException{
        Set<String> set = new HashSet<>();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT menus.permission FROM menus "
                    + "WHERE id in (SELECT rolemenurelations.menuId FROM rolemenurelations "
                    + "WHERE roleId = ?)")) {
                ps.setInt(1, id);
                ResultSet result = ps.executeQuery();
                while(result.next()){
                    String permission = result.getString(1);
                    set.add(permission);
                }
            }
        }
        return set;
    }
}
