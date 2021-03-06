package org.launchcode.models.forms;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;

import javax.validation.constraints.NotNull;

public class AddMenuItemForm {

    private Menu menu;

    private Iterable<Cheese> cheeses;

    @NotNull
    private int cheeseId;

    @NotNull
    private int menuId;

    public AddMenuItemForm(){}

    public AddMenuItemForm(Menu aMenu, Iterable<Cheese> cheeseList){
        this.menu = aMenu;
        this.cheeses = cheeseList;
    }

    public int getCheeseId() { return cheeseId; }

    public void setCheeseId(int cheeseId) { this.cheeseId = cheeseId; }

    public int getMenuId() { return menuId; }

    public void setMenuId(int menuId) { this.menuId = menuId; }

    public Menu getMenu() { return menu; }

    public void setMenu(Menu menu) { this.menu = menu; }

    public Iterable<Cheese> getCheeses() { return cheeses; }

    public void setCheeses(Iterable<Cheese> cheeses) { this.cheeses = cheeses; }

}
