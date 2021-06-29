package org.fuller.service;

import org.fuller.dao.InitParamDao;
import org.fuller.entity.*;

import java.sql.SQLException;
import java.util.List;

public class InitParamService {
    private static InitParamService instance;
    private InitParamService(){};
    static {
        instance = new InitParamService();
    }

    public static InitParamService getInstance() {
        return instance;
    }

    public List<ApplicationResult> getApplicationResult() throws SQLException {
        return InitParamDao.getInstance().getApplicationResult();
    }

    public List<GenderType> getGenderType() throws SQLException {
        return InitParamDao.getInstance().getGenderType();
    }

    public List<MenuType> getMenuType() throws SQLException {
        return InitParamDao.getInstance().getMenuType();
    }

    public List<RoleType> getRoleType() throws SQLException {
        return InitParamDao.getInstance().getRoleType();
    }

    public List<LeaveApplicationType> getLeaveApplicationType() throws SQLException {
        return InitParamDao.getInstance().getLeaveApplicationType();
    }

}
