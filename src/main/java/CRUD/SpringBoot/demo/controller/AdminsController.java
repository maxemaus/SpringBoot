package CRUD.SpringBoot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import CRUD.SpringBoot.demo.model.Role;
import CRUD.SpringBoot.demo.model.User;
import CRUD.SpringBoot.demo.service.RoleService;
import CRUD.SpringBoot.demo.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminsController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("")
    public String getUsers(Model model) {
        List<User> users = userService.getUsersList();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(@PathVariable Long id, Model model) {
        User userEdit = userService.findById(id);
        List<Role> roles = roleService.getRolesList();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", userEdit);
        return "update";
    }

    @PostMapping("/adduser")
    public String addUser(@Validated(User.class) @ModelAttribute("user") User user,
                          @RequestParam("authorities") List<String> values,
                          BindingResult result) {
        if(result.hasErrors()) {
            return "error";
        }
        Set<Role> roleSet = userService.getSetOfRoles(values);
        user.setRoles(roleSet);
        userService.add(user);
        return "redirect:/admin";
    }

    @PostMapping("update")
    public String editUser(@Validated(User.class) @ModelAttribute("user") User user,
                           @RequestParam("authorities") List<String> values,
                           BindingResult result) {
        if(result.hasErrors()) {
            return "error";
        }
        Set<Role> roleSet = userService.getSetOfRoles(values);
        user.setRoles(roleSet);
        userService.edit(user);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute(new User());
        List<Role> roles = roleService.getRolesList();
        model.addAttribute("allRoles", roles);
        return "create";
    }
    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        if(user != null) {
            userService.delete(id);
            model.addAttribute("message", "user " + user.getUsername() + " successfully deleted");
        } else {
            model.addAttribute("message", "no such user");
        }

        return "redirect:/admin";
    }

}
