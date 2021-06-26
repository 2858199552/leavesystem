package org.fuller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void init() throws SQLException {
        //        初始化数据表
        createTable();
//        只调用一次
//        initializeTable();
    }
    private static void createTable() throws SQLException {
        String sqlStr[] = {"CREATE TABLE IF NOT EXISTS colleges (" +
                "id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "num CHAR(4) NOT NULL," +
                "UNIQUE (name));",
                "CREATE TABLE IF NOT EXISTS grades (" +
                        "id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                        "num VARCHAR(10) NOT NULL," +
                        "belongToCollege INTEGER NOT NULL," +
                        "headTeacher INTEGER NOT NULL," +
                        "UNIQUE (num));",
                "CREATE TABLE IF NOT EXISTS students (" +
                        "id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                        "name VARCHAR(100) NOT NULL," +
                        "num VARCHAR (10) NOT NULL," +
                        "password CHAR(32) NOT NULL," +
                        "gradeId INTEGER NOT NULL," +
                        "gender INTEGER NOT NULL," +
                        "UNIQUE (num));",
                "CREATE TABLE IF NOT EXISTS teachers (" +
                        "id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                        "name VARCHAR(100) NOT NULL," +
                        "num VARCHAR(10) NOT NULL," +
                        "password CHAR(32) NOT NULL," +
                        "collegeId INTEGER NOT NULL," +
                        "UNIQUE (num));",
                "CREATE TABLE IF NOT EXISTS roles (" +
                        "id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                        "name VARCHAR(100) NOT NULL," +
                        "remark VARCHAR(100) NOT NULL," +
                        "areaType INTEGER NOT NULL," +
                        "UNIQUE (name));",
                "CREATE TABLE IF NOT EXISTS menus (" +
                        "id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                        "name VARCHAR(100) NOT NULL," +
                        "permission VARCHAR(100) NOT NULL," +
                        "pId INTEGER NOT NULL," +
                        "pIds VARCHAR(100) NOT NULL," +
                        "url VARCHAR(100) NOT NULL," +
                        "UNIQUE (name));",
                "CREATE TABLE IF NOT EXISTS userRoleRelations (" +
                        "userId INTEGER NOT NULL," +
                        "roleId INTEGER NOT NULL," +
                        "UNIQUE (userId, roleId));",
                "CREATE TABLE IF NOT EXISTS roleMenuRelations (" +
                        "roleId INTEGER NOT NULL," +
                        "menuId INTEGER NOT NULL," +
                        "UNIQUE (roleId, menuId));",
                "CREATE TABLE IF NOT EXISTS leaveApplications (" +
                        "id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                        "studentId INTEGER NOT NULL," +
                        "type INTEGER NOT NULL," +
                        "reason VARCHAR(100) NOT NULL," +
                        "beginTime DATE NOT NULL," +
                        "endTime DATE NOT NULL," +
                        "status INTEGER NOT NULL);",
                "CREATE TABLE IF NOT EXISTS leaveApplicationDetails (" +
                        "id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                        "applicationId INTEGER NOT NULL," +
                        "teacherId INTEGER NOT NULL," +
                        "result INTEGER NOT NULL," +
                        "opinion VARCHAR(100) NOT NULL," +
                        "UNIQUE (applicationId));",
                "CREATE TABLE IF NOT EXISTS menuTypes (" +
                        "id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                        "name VARCHAR(100) NOT NULL," +
                        "UNIQUE (name));",
                "CREATE TABLE IF NOT EXISTS genderTypes (" +
                        "id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                        "name VARCHAR(10) NOT NULL," +
                        "UNIQUE (name));",
                "CREATE TABLE IF NOT EXISTS applicationResults (" +
                        "id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                        "name VARCHAR(10) NOT NULL," +
                        "UNIQUE (name));",
                "CREATE TABLE IF NOT EXISTS leaveApplicationTypes (" +
                        "id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                        "name VARCHAR(10) NOT NULL," +
                        "UNIQUE (name));"};
        try (Connection conn = Main.ds.getConnection()) {
            try (Statement ps = conn.createStatement()) {
/*
                无SQL语法高亮
                for (var sql : sqlStr) {
                    ps.addBatch(sql);
                }
*/
                for (int i = 0; i < sqlStr.length; i++) {
                    ps.addBatch(sqlStr[i]);
                }
                ps.executeBatch();
            }
        }
    }

    private static void initializeTable() throws SQLException {
        try (Connection conn = Main.ds.getConnection()) {
            try (Statement ps = conn.createStatement()) {
                ps.addBatch("INSERT INTO menutypes(name) VALUES ('模块')");
                ps.addBatch("INSERT INTO menutypes(name) VALUES ('菜单项')");
                ps.addBatch("INSERT INTO menutypes(name) VALUES ('按钮')");
                ps.addBatch("INSERT INTO leaveapplicationtypes(name) VALUES ('一天以内')");
                ps.addBatch("INSERT INTO leaveapplicationtypes(name) VALUES ('一天以外三天以内')");
                ps.addBatch("INSERT INTO leaveapplicationtypes(name) VALUES ('三天以外')");
                ps.addBatch("INSERT INTO leaveapplicationtypes(name) VALUES ('出恩施州')");
                ps.addBatch("INSERT INTO gendertypes(name) VALUES ('男')");
                ps.addBatch("INSERT INTO gendertypes(name) VALUES ('女')");
                ps.addBatch("INSERT INTO gendertypes(name) VALUES ('未知')");
                ps.addBatch("INSERT INTO teachers(name, num, password, collegeId) VALUES ('超级管理员', 'admin', '123456', 1)");
                ps.addBatch("INSERT INTO menus(name, permission, pId, pIds, url) VALUES ('基础信息', 'baseInfo', 0, '', '')");
                ps.addBatch("INSERT INTO menus(name, permission, pId, pIds, url) VALUES ('学院信息', 'baseInfo.college', 1, '', '')");
                ps.addBatch("INSERT INTO menus(name, permission, pId, pIds, url) VALUES ('查询', 'baseInfo.college.student.query', 2, '', '')");
                ps.addBatch("INSERT INTO menus(name, permission, pId, pIds, url) VALUES ('添加', 'baseInfo.college.student.add', 2, '', '')");
                ps.addBatch("INSERT INTO menus(name, permission, pId, pIds, url) VALUES ('修改', 'baseInfo.college.student.update', 2, '', '')");
                ps.addBatch("INSERT INTO menus(name, permission, pId, pIds, url) VALUES ('详情', 'baseInfo.college.detail', 2, '', '')");
                ps.executeBatch();
            }
        }
    }
}
