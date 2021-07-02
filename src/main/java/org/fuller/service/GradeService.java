package org.fuller.service;

import org.fuller.dao.GradeDao;
import org.fuller.entity.Grade;

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
}
