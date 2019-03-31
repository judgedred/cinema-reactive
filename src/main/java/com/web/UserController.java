package com.web;

import com.domain.User;
import com.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/admin/addUserForm")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @RequestMapping("/admin/addUser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
        } else {
            userService.save(user);
            model.addAttribute("user", new User());
        }
        return "addUser";
    }

    @RequestMapping("/admin/deleteUser")
    public String deleteUser(@ModelAttribute User user, Model model) {
        if (user.getUserId() != null && !user.getUserId().equals(BigInteger.ZERO)) {
            userService.getUserById(user.getUserId()).ifPresent(userService::delete);
        }
        List<User> userList = userService.getUserAll();
        model.addAttribute("userList", userList);
        return "deleteUser";
    }

    @RequestMapping("/admin/userList")
    public String listUsers(Model model) {
        List<User> userList = userService.getUserAll();
        model.addAttribute("userList", userList);
        return "userList";
    }

    @RequestMapping(value = "/admin/checkUser/{userId}", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String checkUser(@PathVariable BigInteger userId) {
        return Optional
                .ofNullable(userId)
                .flatMap(userService::getUserById)
                .filter(userService::checkUserInReservation)
                .map(user -> "У пользователя есть брони. Сначала удалите бронь.")
                .orElse(null);
    }
}
