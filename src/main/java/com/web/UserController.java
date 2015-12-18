package com.web;

import com.domain.Reservation;
import com.domain.User;
import com.service.ReservationService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.List;

@Controller
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @RequestMapping("/admin/addUserForm")
    public ModelAndView addUserForm() throws Exception
    {
        return new ModelAndView("addUser", "user", new User());
    }

    @RequestMapping("/admin/addUser")
    public ModelAndView addUser(@Valid User user, BindingResult result) throws Exception
    {
        if(result.hasErrors())
        {
            return new ModelAndView("addUser", "user", user);
        }
        userService.create(user);
        return new ModelAndView("addUser", "user", new User());
    }

    @RequestMapping("/admin/deleteUser")
    public ModelAndView deleteUser(@ModelAttribute User user) throws Exception
    {
        if(user.getUserId() != null && user.getUserId() != 0)
        {
            user = userService.getUserById(user.getUserId());
            if(user != null)
            {
                userService.delete(user);
            }
        }
        List<User> userList = userService.getUserAll();
        return new ModelAndView("deleteUser", "userList", userList);
    }

    @RequestMapping("/admin/userList")
    public ModelAndView listUsers() throws Exception
    {
        List<User> userList = userService.getUserAll();
        return new ModelAndView("userList", "userList", userList);
    }

    @RequestMapping(value = "/admin/checkUser/{userId}", produces = "text/html; charset=UTF-8")
    public @ResponseBody String checkUser(@PathVariable Integer userId) throws Exception
    {
        if(userId != null)
        {
            User user = userService.getUserById(userId);
            if(user != null && userService.checkUserInReservation(user))
            {
                return "У пользователя есть брони. Сначала удалите бронь.";
            }
        }
        return null;
    }
}
