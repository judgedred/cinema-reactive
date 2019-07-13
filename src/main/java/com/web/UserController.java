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
import org.springframework.web.reactive.result.view.Rendering;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/admin/addUserForm")
    public Rendering addUserForm() {
        return Rendering.view("addUser").modelAttribute("user", new User()).build();
    }

    @RequestMapping("/admin/addUser")
    public Rendering addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
        } else {
            userService.save(user);
            model.addAttribute("user", new User());
        }
        return Rendering.view("addUser").build();
    }

    @RequestMapping("/admin/deleteUser")
    public Rendering deleteUser(@ModelAttribute User user) {
        if (user.getUserId() != null && !user.getUserId().equals(BigInteger.ZERO)) {
            userService.getUserById(user.getUserId()).ifPresent(userService::delete);
        }
        return Rendering.view("deleteUser").modelAttribute("userList", userService.getUserAll()).build();
    }

    @RequestMapping("/admin/userList")
    public Rendering listUsers() {
        return Rendering.view("userList").modelAttribute("userList", userService.getUserAll()).build();
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
