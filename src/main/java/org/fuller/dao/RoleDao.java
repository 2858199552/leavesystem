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
}
