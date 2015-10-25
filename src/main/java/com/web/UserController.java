package com.web;

import com.domain.Reservation;
import com.domain.User;
import com.service.ReservationService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping("/admin/addUser")
    public ModelAndView addUser(@ModelAttribute User user) throws Exception
    {
        if(user != null && user.getLogin() != null && !user.getEmail().isEmpty()
                && user.getPassword() != null && user.getEmail() != null
                && !user.getLogin().isEmpty() && !user.getPassword().isEmpty())
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            byte[] hash = digest.digest(user.getPassword().getBytes("UTF-8"));
            String passwordHash = DatatypeConverter.printHexBinary(hash);
            user.setPassword(passwordHash);
            userService.create(user);
        }
        return new ModelAndView("addUser", "user", new User());
    }

    @RequestMapping("/admin/deleteUser")
    public ModelAndView deleteUser(@ModelAttribute User user, HttpServletResponse response) throws Exception
    {
        try
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
            response.setStatus(HttpServletResponse.SC_OK);
            return new ModelAndView("deleteUser", "userList", userList);
        }
        catch(Exception e)
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/admin/userList")
    public ModelAndView listUsers() throws Exception
    {
        List<User> userList = userService.getUserAll();
        return new ModelAndView("userList", "userList", userList);
    }

    @RequestMapping(value = "/admin/userCheck/{userId}", produces = "text/html; charset=UTF-8")
    public @ResponseBody String userCheck(@PathVariable Integer userId) throws Exception
    {
        if(userId != null)
        {
            User user = userService.getUserById(userId);
            if(user != null)
            {
                List<Reservation> reservationList = reservationService.getReservationAll();
                for(Reservation r : reservationList)
                {
                    if(r.getUser().equals(user))
                    {
                        return "У пользователя есть брони. Сначала удалите бронь.";
                    }
                }
            }
        }
        return null;
    }
}
