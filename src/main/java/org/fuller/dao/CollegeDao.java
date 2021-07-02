package org.fuller.dao;

import org.fuller.entity.College;
import org.fuller.entity.Role;
import org.fuller.service.CollegeService;
import org.fuller.unit.JdbcUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollegeDao {
    private static CollegeDao instance;
    private CollegeDao(){};
    static {
        instance = new CollegeDao();
    }

    public static CollegeDao getInstance() {
        return instance;
    }

    public List<College> getAll() throws SQLException {
        List<College> colleges = new ArrayList<>();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM colleges ")) {
                ResultSet set = ps.executeQuery();
                while (set.next()) {
                    College college = new College();
                    college.setId(set.getInt(1));
                    college.setName(set.getString(2));
                    college.setNum(set.getString(3));
                    college.setPhone(set.getString(4));
                    colleges.add(college);
                }
            }
        }
        return colleges;
    }

    public boolean add(College college) throws SQLException {
        int count = 0;
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO colleges (name, num) VALUES (?, ?)")) {
                ps.setString(1, college.getName());
                ps.setString(2, college.getNum());
                count = ps.executeUpdate();
            }
        }
        return count == 1;
    }

    public College getById(int id) throws SQLException {
        College college = new College();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM colleges WHERE id = ?")) {
                ps.setInt(1, id);
                ResultSet set = ps.executeQuery();
                if (set.next()) {
                    college.setId(set.getInt(1));
                    college.setName(set.getString(2));
                    college.setNum(set.getString(3));
                    college.setPhone(set.getString(4));
                } else {
                    college = null;
                }
            }
        }
        return college;
    }

    public boolean update(College college) throws SQLException {
        int count = 0;
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE colleges SET name = ?, num = ? WHERE id = ?")) {
                ps.setString(1, college.getName());
                ps.setString(2, college.getNum());
                ps.setInt(3, college.getId());
                // 懒得添加Phone
                count = ps.executeUpdate();
            }
        }
        return count == 1;
    }
}
