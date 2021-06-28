package org.fuller.service;

public class MenuService {
    private static MenuService instance;
    private MenuService(){};
    static {
        instance = new MenuService();
    }

    public static MenuService getInstance() {
        return instance;
    }
}
