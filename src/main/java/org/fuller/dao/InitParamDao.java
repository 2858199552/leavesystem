package org.fuller.dao;

import org.fuller.entity.*;
import org.fuller.unit.JdbcUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InitParamDao {
    private InitParamDao(){};
    private static InitParamDao instance;
    static {
        instance = new InitParamDao();
    }

    public static InitParamDao getInstance() {
        return instance;
    }

    public List<ApplicationResult> getApplicationResult() throws SQLException {
        List<ApplicationResult> results = new ArrayList<ApplicationResult>();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM applicationresults")) {
                ResultSet set = ps.executeQuery();
                while (set.next()) {
                    ApplicationResult genderType = new ApplicationResult();
                    genderType.setId(set.getInt(1));
                    genderType.setName(set.getString(2));
                    results.add(genderType);
                }
            }
        }
        return results;
    }

    public List<GenderType> getGenderType() throws SQLException {
        List<GenderType> genderTypes = new ArrayList<>();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM gendertypes")) {
                ResultSet set = ps.executeQuery();
                while (set.next()) {
                    GenderType genderType = new GenderType();
                    genderType.setId(set.getInt(1));
                    genderType.setName(set.getString(2));
                    genderTypes.add(genderType);
                }
            }
        }
        return genderTypes;
    }

    public List<MenuType> getMenuType() throws SQLException {
        List<MenuType> menuTypes = new ArrayList<>();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM menutypes")) {
                ResultSet set = ps.executeQuery();
                while (set.next()) {
                    MenuType menuType = new MenuType();
                    menuType.setId(set.getInt(1));
                    menuType.setName(set.getString(2));
                    menuTypes.add(menuType);
                }
            }
        }
        return menuTypes;
    }

    public List<RoleType> getRoleType() throws SQLException {
        List<RoleType> roleTypes = new ArrayList<>();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM roletype")) {
                ResultSet set = ps.executeQuery();
                while (set.next()) {
                    RoleType roleType = new RoleType();
                    roleType.setId(set.getInt(1));
                    roleType.setName(set.getString(2));
                    roleTypes.add(roleType);
                }
            }
        }
        return roleTypes;
    }

    public List<LeaveApplicationType> getLeaveApplicationType() throws SQLException {
        List<LeaveApplicationType> applicationResults = new ArrayList<>();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM leaveapplicationtypes")) {
                ResultSet set = ps.executeQuery();
                while (set.next()) {
                    LeaveApplicationType applicationResult = new LeaveApplicationType();
                    applicationResult.setId(set.getInt(1));
                    applicationResult.setName(set.getString(2));
                    applicationResults.add(applicationResult);
                }
            }
        }
        return applicationResults;
    }
}
