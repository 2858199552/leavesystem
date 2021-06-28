package org.fuller.service;

import org.fuller.dao.PermissonDao;

import java.sql.SQLException;
import java.util.Set;

public class PermissionService {
    private static PermissionService instance;
    private PermissionService(){};
    static {
        instance = new PermissionService();
    }

    public static PermissionService getInstance() {
        return instance;
    }

    public Set<String> getPermissionByTeacherId(int userId) throws SQLException {
        return PermissonDao.getInstance().getPermissionByTeacherId(userId);
    }
}
