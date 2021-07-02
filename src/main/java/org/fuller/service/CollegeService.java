package org.fuller.service;

import org.fuller.dao.CollegeDao;
import org.fuller.entity.College;

import java.sql.SQLException;
import java.util.List;

public class CollegeService {
    private static CollegeService instance;
    private CollegeService(){};
    static {
        instance = new CollegeService();
    }

    public static CollegeService getInstance() {
        return instance;
    }

    public List<College> getAll() throws SQLException {
        return CollegeDao.getInstance().getAll();
    }

    public boolean add(College college) throws SQLException {
        return CollegeDao.getInstance().add(college);
    }

    public College getById(int id) throws SQLException {
        return CollegeDao.getInstance().getById(id);
    }

    public boolean update(College college) throws SQLException {
        return CollegeDao.getInstance().update(college);
    }
}
