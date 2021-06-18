package org.fuller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void init() throws SQLException {
//        初始化数据表
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
}
