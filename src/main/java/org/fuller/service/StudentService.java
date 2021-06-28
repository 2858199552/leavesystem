package org.fuller.service;

import org.fuller.dao.StudentDao;
import org.fuller.entity.Student;

import java.sql.SQLException;

public class StudentService {
    private static StudentService instance;
    private StudentService(){};
    static {
        instance = new StudentService();
    }

    public static StudentService getInstance() {
        return instance;
    }

    public Student getByNum(String num) throws SQLException {
        return StudentDao.getInstance().getByNum(num);
    }
}
