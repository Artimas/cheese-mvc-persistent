package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private MenuDao menuDao;

    @RequestMapping(value = "")
    public String index(Model model){
        model.addAttribute("title", "Menus");
        model.addAttribute("menus", menuDao.findAll());
        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddMenuForm(Model model) {
        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());
        model.addAttribute("menus", menuDao.findAll());
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addMenuForm(Model model, @Valid Menu newMenu, Errors errors){
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }
        menuDao.save(newMenu);
        return "redirect:view/" + newMenu.getId();
    }

    @RequestMapping(value = "view/{menuId}", method = RequestMethod.GET)
    public  String viewMenu(Model model, @PathVariable int menuId){
        Menu aMenu = menuDao.findOne(menuId);

        model.addAttribute("menu", aMenu);
        model.addAttribute("title", "Menu");

        return "menu/view";

    }

    @RequestMapping(value = "add-item/{menuId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int menuId) {

        Menu aMenu = menuDao.findOne(menuId);

        AddMenuItemForm addMenuItemForm = new AddMenuItemForm(aMenu, cheeseDao.findAll());

        model.addAttribute("title", "Add item to menu:" + aMenu.getId());
        model.addAttribute("form", addMenuItemForm);

        return "menu/add-item";
    }

    @RequestMapping(value = "add-item/{menuId}", method = RequestMethod.POST)
    public String addItem(Model model, @Valid AddMenuItemForm menuItemForm, Errors errors, @RequestParam int menuId){
        if (errors.hasErrors()) {
            Menu aMenu = menuDao.findOne(menuId);

            AddMenuItemForm addMenuItemForm = new AddMenuItemForm(aMenu, cheeseDao.findAll());
            model.addAttribute("title", "Add Item");
            return "menu/add-item";
        }

        Cheese newCheese = cheeseDao.findOne(menuItemForm.getCheeseId());
        Menu newMenu = menuDao.findOne(menuItemForm.getMenuId());
        newMenu.addItem(newCheese);
        menuDao.save(newMenu);
        return "redirect:/menu/view/" + newMenu.getId();
    }

}
