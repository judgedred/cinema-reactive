package com.web;

import com.domain.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/admin/addUserForm")
    public ModelAndView addUserForm() throws Exception {
        return new ModelAndView("addUser", "user", new User());
    }

    @RequestMapping("/admin/addUser")
    public ModelAndView addUser(@Valid User user, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return new ModelAndView("addUser", "user", user);
        }
        userService.create(user);
        return new ModelAndView("addUser", "user", new User());
    }

    @RequestMapping("/admin/deleteUser")
    public ModelAndView deleteUser(@ModelAttribute User user) {
        if (user.getUserId() != null && !user.getUserId().equals(BigInteger.ZERO)) {
            userService.getUserById(user.getUserId()).ifPresent(userService::delete);
        }
        List<User> userList = userService.getUserAll();
        return new ModelAndView("deleteUser", "userList", userList);
    }

    @RequestMapping("/admin/userList")
    public ModelAndView listUsers() throws Exception {
        List<User> userList = userService.getUserAll();
        return new ModelAndView("userList", "userList", userList);
    }

    @RequestMapping(value = "/admin/checkUser/{userId}", produces = "text/html; charset=UTF-8")
    public @ResponseBody
    String checkUser(@PathVariable Integer userId) {
        return Optional
                .ofNullable(userId)
                .map(BigInteger::valueOf)
                .flatMap(userService::getUserById)
                .filter(userService::checkUserInReservation)
                .map(user -> "У пользователя есть брони. Сначала удалите бронь.")
                .orElse(null);
    }
}
