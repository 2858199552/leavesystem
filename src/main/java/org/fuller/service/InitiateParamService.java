package org.fuller.service;

import org.fuller.dao.InitiateParamDao;
import org.fuller.entity.*;

import java.sql.SQLException;
import java.util.List;

public class InitiateParamService {
    private static InitiateParamService instance;
    private InitiateParamService(){};
    static {
        instance = new InitiateParamService();
    }

    public static InitiateParamService getInstance() {
        return instance;
    }

    public List<ApplicationResult> getApplicationResult() throws SQLException {
        return InitiateParamDao.getInstance().getApplicationResult();
    }

    public List<GenderType> getGenderType() throws SQLException {
        return InitiateParamDao.getInstance().getGenderType();
    }

    public List<MenuType> getMenuType() throws SQLException {
        return InitiateParamDao.getInstance().getMenuType();
    }

    public List<RoleType> getRoleType() throws SQLException {
        return InitiateParamDao.getInstance().getRoleType();
    }

    public List<LeaveApplicationType> getLeaveApplicationType() throws SQLException {
        return InitiateParamDao.getInstance().getLeaveApplicationType();
    }

}
