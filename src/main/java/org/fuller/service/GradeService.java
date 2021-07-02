package org.fuller.service;

import org.fuller.dao.GradeDao;
import org.fuller.entity.Grade;
import org.fuller.entity.Period;

import java.sql.SQLException;
import java.util.List;

public class GradeService {
    private static GradeService instance;
    private GradeService(){};
    static {
        instance = new GradeService();
    }

    public static GradeService getInstance() {
        return instance;
    }

    public List<Grade> getGradesByUserId(int userId) throws SQLException {
        return GradeDao.getInstance().getGradesByUserId(userId);
    }

    public List<Grade> getAll() throws SQLException {
        return GradeDao.getInstance().getAll();
    }

    public boolean add(Grade grade) throws SQLException {
        return GradeDao.getInstance().add(grade);
    }

    public Grade getById(int id) throws SQLException {
        return GradeDao.getInstance().getById(id);
    }

    public boolean update(Grade grade) throws SQLException {
        return GradeDao.getInstance().update(grade);
    }

    public List<Period> getPeriods() throws SQLException {
        return GradeDao.getInstance().getPeriods();
    }

    public Grade getDetailById(int id) throws SQLException {
        return GradeDao.getInstance().getDetailById(id);
    }
}
