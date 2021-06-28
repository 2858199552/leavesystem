package org.fuller.dao;

import org.fuller.entity.Student;
import org.fuller.unit.JdbcUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDao {
    private static StudentDao instance;
    private StudentDao(){};
    static {
        instance = new StudentDao();
    }

    public static StudentDao getInstance() {
        return instance;
    }

    public Student getByNum(String num) throws SQLException {
        Student student = new Student();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM students WHERE num = ?")) {
                ps.setString(1, num);
                ResultSet set = ps.executeQuery();
                if (set.next()) {
                    student.setId(set.getInt(1));
                    student.setName(set.getString(2));
                    student.setNum(set.getString(3));
                    student.setPassword(set.getString(4));
                    student.setGradeId(set.getInt(5));
                    student.setGender(set.getInt(6));
                } else {
                    student = null;
                }
            }
        }
        return student;
    }
}
