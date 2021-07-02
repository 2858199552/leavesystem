package org.fuller.dao;

import org.fuller.entity.Role;
import org.fuller.service.RoleService;
import org.fuller.unit.JdbcUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDao {
    private static RoleDao instance;
    private RoleDao(){};
    static {
        instance = new RoleDao();
    }

    public static RoleDao getInstance() {
        return instance;
    }

    public List<Role> getRolesByUserId(int userId) throws SQLException {
        List<Role> roles = new ArrayList<>();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM roles " +
                    "WHERE roles.id IN (SELECT roleId FROM userrolerelations WHERE userId = ?)")) {
                ps.setInt(1, userId);
                ResultSet set = ps.executeQuery();
                while (set.next()) {
                    Role role = new Role();
                    role.setId(set.getInt(1));
                    role.setName(set.getString(2));
                    role.setRemark(set.getString(3));
                    role.setAreaType(set.getInt(4));
                    roles.add(role);
                }
            }
        }
        return roles;
    }

    public List<Role> getAll() throws SQLException {
        List<Role> roles = new ArrayList<>();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM roles ")) {
                ResultSet set = ps.executeQuery();
                while (set.next()) {
                    Role role = new Role();
                    role.setId(set.getInt(1));
                    role.setName(set.getString(2));
                    role.setRemark(set.getString(3));
                    role.setAreaType(set.getInt(4));
                    roles.add(role);
                }
            }
        }
        return roles;
    }

    public boolean add(Role role) throws SQLException {
        int count = 0;
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO roles (name, remark, areaType) VALUES (?, ?, ?)")) {
                ps.setString(1, role.getName());
                ps.setString(2, role.getRemark());
                ps.setInt(3, role.getAreaType());
                count = ps.executeUpdate();
            }
        }
        return count == 1;
    }

    public boolean update(Role role) throws SQLException {
        List<Role> roles = new ArrayList<>();
        int count = 0;
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE roles SET name = ?, remark = ?, areaType = ? WHERE id = ?")) {
                ps.setString(1, role.getName());
                ps.setString(2, role.getRemark());
                ps.setInt(3, role.getAreaType());
                ps.setInt(4, role.getId());
                count = ps.executeUpdate();
            }
        }
        return count == 1;
    }

    public Role getById(int roleId) throws SQLException {
        Role role = new Role();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM roles WHERE id = ?")) {
                ps.setInt(1, roleId);
                ResultSet set = ps.executeQuery();
                if (set.next()) {
                    role.setId(set.getInt(1));
                    role.setName(set.getString(2));
                    role.setRemark(set.getString(3));
                    role.setAreaType(set.getInt(4));
                    role.setMenus(PermissonDao.getInstance().getPermissionByRoleId(role.getId()));
                } else {
                    role = null;
                }
            }
        }
        return role;
    }

    public boolean deleteById(int roleId) throws SQLException {
        int result;
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM roles WHERE id = ?")) {
                ps.setInt(1, roleId);
                result = ps.executeUpdate();
            }
        }
        return result == 1;
    }

    public boolean updateRolePermissionById(int roleId, String[] permissions) throws SQLException {
        Connection conn = JdbcUnit.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            deletePermission(roleId);
            for (String permission : permissions) {
                addPermission(roleId, permission);
            }
        } catch (SQLException e) {
            conn.rollback();
            return false;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
        return true;
    }

    // region transaction element method
    private void deletePermission(int roleId) throws SQLException {
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM rolemenurelations WHERE roleId = ?")) {
                ps.setInt(1, roleId);
                ps.executeUpdate();
            }
        }
    }

    private void addPermission(int roleId, String permission) throws SQLException {
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO rolemenurelations (roleId, menuId) VALUES (?, ?)")) {
                ps.setInt(1, roleId);
                ps.setInt(2, Integer.parseInt(permission));
                ps.executeUpdate();
            }
        }
    }
    // endregion
}
