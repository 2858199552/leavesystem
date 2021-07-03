package org.fuller.service;

import org.fuller.dao.TeacherDao;
import org.fuller.entity.Teacher;

import java.sql.SQLException;
import java.util.List;

public class TeacherService {
    private TeacherService(){};
    private static TeacherService instance;
    static {
        instance = new TeacherService();
    }

    public static TeacherService getInstance() {
        return instance;
    }

    public Teacher getByNum(String num) throws SQLException {
        return TeacherDao.getInstance().getByNum(num);
    }

    public List<Teacher> getByName(String name) throws SQLException {
        return TeacherDao.getInstance().getByName(name);
    }
}
