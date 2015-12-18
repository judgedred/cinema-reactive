package com.web;

import com.domain.*;
import com.service.*;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.*;

@Controller
public class MainController
{
    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private FilmshowService filmshowService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private TicketEditor ticketEditor;

    @RequestMapping("/admin")
    public ModelAndView adminView()
    {
        return new ModelAndView("admin");
    }

    @RequestMapping("/admin/login")
    public ModelAndView adminLogin(@ModelAttribute User user, HttpServletRequest request) throws Exception
    {
        if(user.getLogin() != null && user.getPassword() != null
                && !user.getLogin().isEmpty() && !user.getPassword().isEmpty())
        {
            User adminUser = userService.authenticateAdmin(user);
            if(adminUser != null)
            {
                request.getSession().setAttribute("adminUser", adminUser);
                return new ModelAndView("adminMain");
            }
        }
        return new ModelAndView(new RedirectView("/admin", true));
    }

    @RequestMapping("/admin/logout")
    public ModelAndView adminLogout(HttpSession session)
    {
        session.invalidate();
        return new ModelAndView(new RedirectView("/admin", true));
    }

    @RequestMapping("/reserveTicket")
    public ModelAndView reserveTicket(@ModelAttribute Reservation reservation, HttpServletRequest request,
                                                    @RequestParam(required = false) Integer filmshowId) throws Exception
    {
        ModelAndView mav = new ModelAndView("reserveTicket");
        User user = (User)request.getSession().getAttribute("validUser");
        if(reservation.getTicket() != null)
        {
            reservation.setUser(user);
            reservationService.create(reservation);
        }
        if(filmshowId!= null)
        {
            Filmshow filmshow = filmshowService.getFilmshowById(filmshowId);
            if(filmshow != null)
            {
                List<Ticket> ticketList = ticketService.getTicketFreeByFilmshow(filmshow);
                mav.addObject("filteredTicketList", ticketList);
                mav.addObject("filmshow", filmshow);
            }
        }
        return mav;
    }

    @RequestMapping("/reservationList")
    public ModelAndView listUserReservations(HttpServletRequest request) throws Exception
    {
        ModelAndView mav = new ModelAndView("userReservationList");
        User user = (User)request.getSession().getAttribute("validUser");
        if(user != null)
        {
            List<Reservation> reservationList = reservationService.getReservationAllByUser(user);
            mav.addObject("filteredReservationList", reservationList);
        }
        return mav;
    }

    @RequestMapping(value ="/authCheck", produces = "text/html; charset=UTF-8")
    public @ResponseBody String authCheck(HttpServletRequest request)
    {
        User validUser = (User)request.getSession().getAttribute("validUser");
        if(validUser == null)
        {
            return "Войдите в систему";
        }
        else
        {
            return null;
        }
    }

    @RequestMapping(value = "/loginCheck", produces = "text/html; charset=UTF-8")
    public @ResponseBody String checkLogin(@RequestBody User user, HttpServletRequest request) throws Exception
    {
        if(user.getLogin() != null && user.getPassword() != null
                && !user.getLogin().isEmpty() && !user.getPassword().isEmpty())
        {
            User validUser = userService.authenticateUser(user);
            if(validUser != null)
            {
                request.getSession().setAttribute("validUser", validUser);
            }
        }
        User validUser = (User) request.getSession().getAttribute("validUser");
        if(validUser != null)
        {
            return validUser.getLogin();
        }
        else
        {
            return null;
        }
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response)
    {
        request.getSession().invalidate();
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @RequestMapping("/main")
    public ModelAndView navigateMain() throws Exception
    {
        List<Filmshow> filmshowList = filmshowService.getFilmshowToday();
        return new ModelAndView("main", "filmshowToday", filmshowList);
    }

    @RequestMapping("/registerForm")
    public ModelAndView registerUserForm()
    {
        return new ModelAndView("register", "user", new User());
    }

    @RequestMapping("/register")
    public ModelAndView registerUser(@Valid User user, BindingResult result) throws Exception
    {
        if(result.hasErrors())
        {
            return new ModelAndView("register", "user", user);
        }
        userService.create(user);
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("registered", "Вы зарегистрированы");
        mav.addObject("user", new User());
        return mav;
    }

    @RequestMapping(value = "/registerCheck", produces = "text/html; charset=UTF-8")
    public @ResponseBody String registerCheck(@RequestParam(required = false) String login, @RequestParam(required = false) String email) throws Exception
    {
        List<User> userList = userService.getUserAll();
        Boolean loginFree = true;
        Boolean emailFree = true;
        if(login != null && !login.isEmpty() || email != null && !email.isEmpty())
        {
            for(User u : userList)
            {
                if(u.getLogin().equals(login))
                {
                    loginFree = false;
                }

                if(u.getEmail().equals(email))
                {
                    emailFree = false;
                }
            }
            if(login != null)
            {
                if(loginFree)
                {
                    return "Логин свободен";
                }
                else
                {
                    return"Логин занят";
                }
            }
            if(email != null)
            {
                if(!emailFree)
                {
                    return "Логин с таким email уже есть";
                }
            }
        }
        return null;
    }

    @RequestMapping("filmshow")
    public ModelAndView navigateFilmshow() throws Exception
    {
        ModelAndView mav = new ModelAndView("filmshow");
        List<Filmshow> filmshowList = filmshowService.getFilmshowWeek();
        if(filmshowList != null)
        {
            Map<LocalDate, List<Filmshow>> filmshowMap = filmshowService.groupFilmshowByDate(filmshowList);
            mav.addObject("filmshowMap", filmshowMap);
        }
        return mav;
    }

    @RequestMapping("film")
    public ModelAndView navigateFilm() throws Exception
    {
        List<Film> filmList = filmService.getFilmAll();
        return new ModelAndView("film", "filmList", filmList);
    }

    @RequestMapping("news")
    public String navigateNews()
    {
        return "news";
    }

    @RequestMapping("about")
    public String navigateAbout()
    {
        return "about";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(Ticket.class, ticketEditor);
    }
}
