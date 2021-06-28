package org.fuller.dao;

import org.fuller.Main;
import org.fuller.entity.Teacher;
import org.fuller.unit.JdbcUnit;

import java.sql.*;

public class TeacherDao {
    private TeacherDao(){};
    private static TeacherDao instance;
    static {
        instance = new TeacherDao();
    }

    public static TeacherDao getInstance() {
        return instance;
    }
    public Teacher getByNum(String num) throws SQLException {
        Teacher teacher = new Teacher();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM teachers WHERE num = ?")) {
                ps.setObject(1, num);
                ResultSet set = ps.executeQuery();
                if (set.next()) {
                    teacher.setId(set.getInt(1));
                    teacher.setName(set.getString(2));
                    teacher.setNum(set.getString(3));
                    teacher.setPassword(set.getString(4));
                    teacher.setCollegeId(set.getInt(5));
                } else {
                    teacher = null;
                }
            }
        }
        return teacher;
    }
}
