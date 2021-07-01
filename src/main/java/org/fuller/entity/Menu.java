package org.fuller.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {
    private int id;
    private String name;
    private String permission;
    private int pId;
    private String pIds;
    private String url;

    private List<Menu> subMenus;

}
