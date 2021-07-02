package org.fuller.dao;

import org.fuller.entity.College;
import org.fuller.entity.Grade;
import org.fuller.unit.JdbcUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDao {
    private GradeDao(){};
    private static GradeDao instance;
    static {
        instance = new GradeDao();
    }

    public static GradeDao getInstance() {
        return instance;
    }

    public List<Grade> getGradesByUserId(int userId) throws SQLException {
        List<Grade> grades = new ArrayList<>();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM grades WHERE headTeacher = ?")) {
                ps.setInt(1, userId);
                ResultSet set = ps.executeQuery();
                while (set.next()) {
                    Grade grade = new Grade();
                    grade.setId(set.getInt(1));
                    grade.setNum(set.getString(2));
                    grade.setBelongToCollege(set.getInt(3));
                    grade.setHeadTeacherId(set.getInt(4));
                    grades.add(grade);
                }
            }
        }
        return grades;
    }

    public List<Grade> getAll() throws SQLException {
        List<Grade> grades = new ArrayList<>();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM grades ")) {
                ResultSet set = ps.executeQuery();
                while (set.next()) {
                    Grade grade = new Grade();
                    grade.setId(set.getInt(1));
                    grade.setNum(set.getString(2));
                    grade.setPeriodId(set.getInt(3));
                    grade.setHeadTeacherId(set.getInt(4));
                    grade.setBelongToCollege(set.getInt(5));
                    grades.add(grade);
                }
            }
        }
        return grades;
    }
}
