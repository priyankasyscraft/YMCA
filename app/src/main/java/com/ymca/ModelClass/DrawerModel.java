package com.ymca.ModelClass;

/**
 * Created by Soni on 05-Aug-16.
 */
public class DrawerModel {

    private String menuName;
    private int menuImg;
    private int menuColor;
    private int menuTextBg;

    public int getMenuColor() {
        return menuColor;
    }

    public void setMenuColor(int menuColor) {
        this.menuColor = menuColor;
    }

    public int getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(int menuImg) {
        this.menuImg = menuImg;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuTextBg() {
        return menuTextBg;
    }

    public void setMenuTextBg(int menuTextBg) {
        this.menuTextBg = menuTextBg;
    }
}
