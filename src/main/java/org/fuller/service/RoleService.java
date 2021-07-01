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

    public List<Role> getAll() throws SQLException {
        return RoleDao.getInstance().getAll();
    }

    public boolean add(Role role) throws SQLException {
        return RoleDao.getInstance().add(role);
    }

    public boolean update(Role role) throws SQLException {
        return RoleDao.getInstance().update(role);
    }

    public Role getById(int roleId) throws SQLException {
        return RoleDao.getInstance().getById(roleId);
    }

    public boolean deleteById(int roleId) throws SQLException {
        return RoleDao.getInstance().deleteById(roleId);
    }
}
