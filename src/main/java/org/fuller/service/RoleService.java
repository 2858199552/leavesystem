package org.fuller.service;

import org.fuller.dao.RoleDao;
import org.fuller.entity.Role;

import java.sql.SQLException;
import java.util.List;

public class RoleService {
    private static RoleService instance;
    private RoleService(){};
    static {
        instance = new RoleService();
    }

    public static RoleService getInstance() {
        return instance;
    }

    public List<Role> getRolesByUserId(int userId) throws SQLException {
        return RoleDao.getInstance().getRolesByUserId(userId);
    }
}
