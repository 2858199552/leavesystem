package org.fuller.service;

import org.fuller.dao.MenuDao;
import org.fuller.entity.Menu;

import java.sql.SQLException;
import java.util.List;

public class MenuService {
    private static MenuService instance;
    private MenuService(){};
    static {
        instance = new MenuService();
    }

    public static MenuService getInstance() {
        return instance;
    }

    public List<Menu> getAll() throws SQLException {
        return MenuDao.getInstance().getAll();
    }
}
