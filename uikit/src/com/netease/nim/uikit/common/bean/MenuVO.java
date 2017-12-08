package com.netease.nim.uikit.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 78560 on 2017/8/15.
 */

public class MenuVO implements Serializable {
    private String menuId;
    private String menuName;
    private String menuUrl;
    private String menuTableName;
    private String iconNo;
    public List<MenuVO> children;

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuTableName(String menuTableName) {
        this.menuTableName = menuTableName;
    }

    public String getMenuTableName() {
        return menuTableName;
    }

    public void setIconNo(String iconNo) {
        this.iconNo = iconNo;
    }

    public String getIconNo() {
        return iconNo;
    }

    public void setChildren(List<MenuVO> children) {
        this.children = children;
    }

    public List<MenuVO> getChildren() {
        return children;
    }
}
