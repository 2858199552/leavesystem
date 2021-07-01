package org.fuller.entity;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Data
public class Role {
    private int id;
    private String name;
    private String remark;
    private int areaType;
    private Set<String> menus;

    public void parse(HttpServletRequest request) {
        String strId = request.getParameter("id");
        if (null != strId) {
            id = Integer.parseInt(strId);
        }
        name = request.getParameter("name");
        remark = request.getParameter("remark");
        areaType = Integer.parseInt(request.getParameter("areaType"));
    }

    public String getTypeName(List<RoleType> types, int type) {
        for (RoleType roleType : types) {
            if (roleType.getId() == type) {
                return roleType.getName();
            }
        }
        return "";
    }

    public boolean hasPermission(String permission) {
        return menus.contains(permission);
    }
}
