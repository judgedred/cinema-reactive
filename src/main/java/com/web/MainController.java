package com.web;

import com.domain.*;
import com.service.*;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        ModelAndView mav = new ModelAndView("adminMain");
        List<User> userList = userService.getUserAll();
        boolean userValid = false;
        if(user != null && user.getLogin() != null && user.getPassword() != null)
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            byte[] hash = digest.digest(user.getPassword().getBytes("UTF-8"));
            String passwordHash = DatatypeConverter.printHexBinary(hash);
            if(user.getLogin().equals("admin"))
            {
                for(User u : userList)
                {
                    if(u.getLogin().equals(user.getLogin()) && u.getPassword().toUpperCase().equals(passwordHash))
                    {
                        request.getSession().setAttribute("adminUser", u);
                        userValid = true;
                        break;
                    }
                }
            }
        }
        if(userValid)
        {
            return mav;
        }
        else
        {
            return new ModelAndView("forbidden");
        }
    }

    @RequestMapping("/admin/logout")
    public ModelAndView adminLogout(HttpSession session)
    {
        session.invalidate();
        return new ModelAndView(new RedirectView("/cinema/admin"));
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
                List<Ticket> ticketList = ticketService.getTicketAll();
                List<Reservation> reservationList = reservationService.getReservationAll();
                boolean ticketFree;
                List<Ticket> filteredTicketList = new ArrayList<>();
                {
                    for(Ticket t : ticketList)
                        if(t.getFilmshow().equals(filmshow))
                        {
                            ticketFree = true;
                            for(Reservation r : reservationList)
                            {
                                if(t.equals(r.getTicket()))
                                {
                                    ticketFree = false;
                                    break;
                                }
                            }
                            if(ticketFree)
                            {
                                filteredTicketList.add(t);
                            }
                        }
                }
                mav.addObject("filteredTicketList", filteredTicketList);
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
        List<Reservation> reservationList = reservationService.getReservationAll();
        List<Reservation> filteredList = new ArrayList<>();
        if(user != null && reservationList != null)
        {
            for(Reservation r : reservationList)
            {
                if(r.getUser().equals(user))
                {
                    filteredList.add(r);
                }
            }
            mav.addObject("filteredReservationList", filteredList);
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
    public @ResponseBody String loginCheck(@RequestBody User user, HttpServletRequest request) throws Exception
    {
        List<User> userList = userService.getUserAll();
        if(user.getPassword() != null)
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            byte[] hash = digest.digest(user.getPassword().getBytes("UTF-8"));
            String passwordHash = DatatypeConverter.printHexBinary(hash);

            if(user.getLogin() != null && passwordHash != null && !passwordHash.isEmpty())
            {
                for(User u : userList)
                {
                    if(u.getLogin().equals(user.getLogin()) && u.getPassword().toUpperCase().equals(passwordHash))
                    {
                        request.getSession().setAttribute("validUser", u);
                        break;
                    }
                }
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
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        List<Filmshow> filteredFilmshowList = new ArrayList<>();
        for(Filmshow f : filmshowList)
        {
            Date javaDate = f.getDateTime();
            LocalDate date = new LocalDate(javaDate);
            if(date.equals(LocalDate.now()))
            {
                filteredFilmshowList.add(f);
            }
        }
        return new ModelAndView("main", "filmshowToday", filteredFilmshowList);
    }

    @RequestMapping("/register")
    public ModelAndView registerUser(@ModelAttribute User user) throws Exception
    {
        if(user.getPassword() != null && !user.getPassword().equals(""))
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            byte[] hash = digest.digest(user.getPassword().getBytes("UTF-8"));
            String passwordHash = DatatypeConverter.printHexBinary(hash);
            if(user.getLogin() != null && !user.getLogin().isEmpty()
                    && passwordHash != null
                    && !passwordHash.isEmpty()
                    && user.getEmail() != null && !user.getEmail().isEmpty())
            {
                user.setPassword(passwordHash);
                userService.create(user);
                ModelAndView mav = new ModelAndView("register");
                mav.addObject("registered", "Вы зарегистрированы");
                mav.addObject("user", new User());
                return mav;
            }
        }
        return new ModelAndView("register");
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
        Map<LocalDate, List<Filmshow>> filmshowMap = new TreeMap<>();
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        if(filmshowList != null)
        {
            for(Filmshow f : filmshowList)
            {
                Date javaDate = f.getDateTime();
                LocalDate date = new LocalDate(javaDate);
                List<Filmshow> dateGroupedFilmshow = filmshowMap.get(date);
                if(dateGroupedFilmshow == null)
                {
                    dateGroupedFilmshow = new ArrayList<>();
                    filmshowMap.put(date, dateGroupedFilmshow);
                }
                dateGroupedFilmshow.add(f);
            }
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
