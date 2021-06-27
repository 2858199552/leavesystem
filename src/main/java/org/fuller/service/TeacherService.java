package org.fuller.service;

import org.fuller.dao.TeacherDao;
import org.fuller.entity.Teacher;

import java.sql.SQLException;

public class TeacherService {
    private TeacherDao teacherDao = new TeacherDao();
//    private TeacherService(){};
//    private static TeacherService instance;
//    static {
//        instance = new TeacherService();
//    }
//
//    public static TeacherService getInstance() {
//        return instance;
//    }
    public Teacher getByNum(String num) throws SQLException {
//        return TeacherDao.getInstance().getByNum(num);
        return teacherDao.getByNum(num);
    }
}
