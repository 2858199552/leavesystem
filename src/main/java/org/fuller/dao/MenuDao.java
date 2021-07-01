package org.fuller.dao;

import org.fuller.entity.Menu;
import org.fuller.unit.JdbcUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDao {
    public static final int MODULE_MENU_PARENT = 0;
    private static MenuDao instance;
    private MenuDao(){};
    static {
        instance = new MenuDao();
    }

    public static MenuDao getInstance() {
        return instance;
    }

    public List<Menu> getAll() throws SQLException {
        List<Menu> allMenu;
        //1、查询模块
        allMenu = getByPId(MODULE_MENU_PARENT);
        //2、查询菜单项
        List<Menu> menuItems;
        List<Menu> menuButtons;

        for (Menu menu : allMenu) {
            menuItems = getByPId(menu.getId());
            menu.setSubMenus(menuItems);
            for (Menu item : menuItems) {
                menuButtons = getByPId(item.getId());
                item.setSubMenus(menuButtons);
            }
        }
        return allMenu;
    }

    public List<Menu> getByPId(int pid) throws SQLException{
        List<Menu> menus = new ArrayList<>();
        try (Connection conn = JdbcUnit.getInstance().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM menus WHERE pId = ?")) {
                ps.setInt(1, pid);
                ResultSet set = ps.executeQuery();
                while(set.next()){
                    Menu menu = new Menu();
                    menu.setId(set.getInt(1));
                    menu.setName(set.getString(2));
                    menu.setPermission(set.getString(3));
                    menu.setPId(set.getInt(4));
                    menu.setPIds(set.getString(5));
                    menu.setUrl(set.getString(6));
                    menus.add(menu);
                }
            }
        }
        return menus;
    }
}
