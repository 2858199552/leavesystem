package org.fuller.dao;

import org.fuller.entity.College;
import org.fuller.entity.Grade;
import org.fuller.entity.Period;
import org.fuller.entity.Role;
import org.fuller.page.Page;
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
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM grades WHERE headTeacherId = ?")) {
                ps.setInt(1, userId);
                ResultSet set = ps.executeQuery();
                while (set.next()) {
                    Grade grade = new Grade();
                    grade.setId(set.getInt(1));
                    grade.setNum(set.getString(2));
                    grade.setBelongToCollegeId(set.getInt(3));
                    grade.setHeadTeacherId(set.getInt(4));
                    grades.add(grade);
                }
            }
        }
        return grades;
    }

    public List<Grade> getAll(Grade gradeCondition, String whereClause, Page page) throws SQLException {
        List<Grade> grades = new ArrayList<>();
        int belongToCollegeId = gradeCondition.getBelongToCollegeId();
        int periodId = gradeCondition.getPeriodId();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM grade_view ");
        if (whereClause == null) {
            if (belongToCollegeId != 0 && periodId != 0) {
                sql.append("WHERE belongToCollegeId = " + belongToCollegeId + " AND periodId = " + periodId);
            } else if (belongToCollegeId != 0) {
                    sql.append("WHERE belongToCollegeId = " + belongToCollegeId);
                } else if (periodId != 0) {
                    sql.append("WHERE periodId = " + periodId);
                }
        } else {
            sql.append("WHERE " + whereClause);
            if (belongToCollegeId != 0) {
                sql.append(" AND belongToCollegeId = " + belongToCollegeId);
            }
            if (periodId != 0) {
                sql.append(" AND periodId = " + periodId);
            }
        }
        sql.append(" ORDER BY id DESC ");
        if (page != null) {
            sql.append("LIMIT " + (page.getPageNo() - 1) + ", " + Page.PAGE_SIZE);
        }
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                ResultSet set = ps.executeQuery();
                while (set.next()) {
                    Grade grade = new Grade();
                    setDetail(set, grade);
                    grades.add(grade);
                }
            }
        }
        return grades;
    }

    private void setDetail(ResultSet set, Grade grade) throws SQLException {
        grade.setId(set.getInt(1));
        grade.setNum(set.getString(2));
        grade.setPeriodId(set.getInt(3));
        grade.setHeadTeacherId(set.getInt(4));
        grade.setBelongToCollegeId(set.getInt(5));
        grade.setPeriod(set.getString(6));
        grade.setBelongToCollege(set.getString(7));
        grade.setHeadTeacher(set.getString(8));
    }

    public boolean add(Grade grade) throws SQLException {
        int count = 0;
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO grades (num, periodId, belongToCollegeId) VALUES (?, ?, ?)")) {
                ps.setString(1, grade.getNum());
                ps.setInt(2, grade.getPeriodId());
                ps.setInt(3, grade.getBelongToCollegeId());
                count = ps.executeUpdate();
            }
        }
        return count == 1;
    }

    public Grade getById(int id) throws SQLException {
        Grade grade = new Grade();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM grades WHERE id = ?")) {
                ps.setInt(1, id);
                ResultSet set = ps.executeQuery();
                if (set.next()) {
                    grade.setId(set.getInt(1));
                    grade.setNum(set.getString(2));
                    grade.setPeriodId(set.getInt(3));
                    grade.setHeadTeacherId(set.getInt(4));
                    // headTeacher为空时则默认为0
                    grade.setBelongToCollegeId(set.getInt(5));
                } else {
                    grade = null;
                }
            }
        }
        return grade;
    }

    public boolean update(Grade grade) throws SQLException {
        int count = 0;
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE grades SET num = ?, periodId = ?, headTeacherId = ?, belongToCollegeId = ? WHERE id = ?")) {
                ps.setString(1, grade.getNum());
                ps.setInt(2, grade.getPeriodId());
                try {
                    ps.setInt(3, grade.getHeadTeacherId());
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
                ps.setInt(4, grade.getBelongToCollegeId());
                ps.setInt(5, grade.getId());
                count = ps.executeUpdate();
            }
        }
        return count == 1;
    }

    public List<Period> getPeriods() throws SQLException {
        List<Period> periods = new ArrayList<>();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM periods ORDER BY id DESC ")) {
                ResultSet set = ps.executeQuery();
                while (set.next()) {
                    Period period = new Period();
                    period.setId(set.getInt(1));
                    period.setPeriod(set.getString(2));
                    periods.add(period);
                }
            }
        }
        return periods;
    }

    public Grade getDetailById(int id) throws SQLException {
        Grade grade = new Grade();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM grade_view WHERE id = ?")) {
                ps.setInt(1, id);
                ResultSet set = ps.executeQuery();
                if (set.next()) {
                    setDetail(set, grade);
                } else {
                    grade = null;
                }
            }
        }
        return grade;
    }

    public boolean setHeadTeacher(int gradeId, int teacherId) throws SQLException {
        int count = 0;
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE grades SET headTeacherId = ? WHERE id = ?")) {
                ps.setInt(1, teacherId);
                ps.setInt(2, gradeId);
                count = ps.executeUpdate();
            }
        }
        return count == 1;
    }

    public int getTotalRecord(Grade gradeCondition, String whereClause, Page page) throws SQLException {
        int totalRecord = 0;
        int belongToCollegeId = gradeCondition.getBelongToCollegeId();
        int periodId = gradeCondition.getPeriodId();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(*) FROM grade_view ");
        if (whereClause == null) {
            if (belongToCollegeId != 0 && periodId != 0) {
                sql.append("WHERE belongToCollegeId = " + belongToCollegeId + " AND periodId = " + periodId);
            } else if (belongToCollegeId != 0) {
                sql.append("WHERE belongToCollegeId = " + belongToCollegeId);
            } else if (periodId != 0) {
                sql.append("WHERE periodId = " + periodId);
            }
        } else {
            sql.append("WHERE " + whereClause);
            if (belongToCollegeId != 0) {
                sql.append(" AND belongToCollegeId = " + belongToCollegeId);
            }
            if (periodId != 0) {
                sql.append(" AND periodId = " + periodId);
            }
        }
        sql.append(" ORDER BY id DESC ");
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                ResultSet set = ps.executeQuery();
                if (set.next()) {
                    totalRecord = set.getInt(1);
                }
            }
        }
        return totalRecord;
    }
}
