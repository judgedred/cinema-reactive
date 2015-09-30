package com.web;

import com.domain.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.List;

@Controller
public class UserController
{
    @Autowired
    private UserService userService;

    @RequestMapping("/admin/addUser")
    public @ResponseBody ModelAndView addUser(@RequestBody User user) throws Exception
    {
        ModelAndView mav = new ModelAndView("addUser");
        if(user != null && user.getLogin() != null
                && user.getPassword() != null && user.getEmail() != null)
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            byte[] hash = digest.digest(user.getPassword().getBytes("UTF-8"));
            String passwordHash = DatatypeConverter.printHexBinary(hash);
            List<User> userList = userService.getUserAll();
            boolean userValid = true;
            user.setPassword(passwordHash);
            for(User u : userList)
            {
                if(u.getLogin().equals(user.getLogin()) || u.getEmail().equals(user.getEmail()))
                {
                    userValid = false;
                }
            }
            if(userValid)
            {
                userService.create(user);
                mav.addObject("userJson", user);
            }
        }
        return mav;
    }

//    @RequestMapping("/admin/updateUser")

    @RequestMapping("/admin/deleteUser/{userId}")
    public @ResponseBody ModelAndView deleteUser(@PathVariable int userId) throws Exception
    {
        List<User> userList = userService.getUserAll();
        User user = userService.getUserById(userId);
        if(user != null)
        {
            userService.delete(user);
        }
        return new ModelAndView("deleteUser", "userListJson", userList);
    }

    @RequestMapping("/admin/userList")
    public @ResponseBody ModelAndView listUsers() throws Exception
    {
        List<User> userList = userService.getUserAll();
        return new ModelAndView("userList", "userListJson", userList);
    }
}
