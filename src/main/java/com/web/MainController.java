package com.web;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import com.service.FilmService;
import com.service.FilmshowService;
import com.service.ReservationService;
import com.service.TicketService;
import com.service.UserService;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final UserService userService;
    private final ReservationService reservationService;
    private final TicketService ticketService;
    private final FilmshowService filmshowService;
    private final FilmService filmService;
    private final TicketEditor ticketEditor;

    public MainController(UserService userService, ReservationService reservationService, TicketService ticketService,
            FilmshowService filmshowService, FilmService filmService, TicketEditor ticketEditor) {
        this.userService = userService;
        this.reservationService = reservationService;
        this.ticketService = ticketService;
        this.filmshowService = filmshowService;
        this.filmService = filmService;
        this.ticketEditor = ticketEditor;
    }

    @RequestMapping("/admin")
    public ModelAndView adminView() {
        return new ModelAndView("admin");
    }

    @RequestMapping("/admin/login")
    public ModelAndView adminLogin(@ModelAttribute User user, HttpServletRequest request) {
        if (user.getLogin() != null && user.getPassword() != null && !user.getLogin().isEmpty() && !user
                .getPassword()
                .isEmpty()) {
            User adminUser = userService.authenticateAdmin(user);
            if (adminUser != null) {
                request.getSession().setAttribute("adminUser", adminUser);
                return new ModelAndView("adminMain");
            }
        }
        return new ModelAndView(new RedirectView("/admin", true));
    }

    @RequestMapping("/admin/logout")
    public ModelAndView adminLogout(HttpSession session) {
        session.invalidate();
        return new ModelAndView(new RedirectView("/admin", true));
    }

    @RequestMapping("/reserveTicket")
    public ModelAndView reserveTicket(@ModelAttribute Reservation reservation, HttpServletRequest request,
            @RequestParam(required = false) Integer filmshowId) {
        ModelAndView mav = new ModelAndView("reserveTicket");
        User user = (User) request.getSession().getAttribute("validUser");
        Optional.of(reservation)
                .filter(r -> r.getTicket() != null)
                .map(r -> r.setUser(user))
                .ifPresent(reservationService::create);
        Optional.ofNullable(filmshowId)
                .map(BigInteger::valueOf)
                .flatMap(filmshowService::getFilmshowById)
                .ifPresent(filmshow -> {
                    mav.addObject("filteredTicketList", ticketService.getTicketFreeByFilmshow(filmshow));
                    mav.addObject("filmshow", filmshow);
                });
        return mav;
    }

    @RequestMapping("/reservationList")
    public ModelAndView listUserReservations(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("userReservationList");
        User user = (User) request.getSession().getAttribute("validUser");
        if (user != null) {
            List<Reservation> reservationList = reservationService.getReservationAllByUser(user);
            mav.addObject("filteredReservationList", reservationList);
        }
        return mav;
    }

    @RequestMapping(value = "/authCheck", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String authCheck(HttpServletRequest request) {
        User validUser = (User) request.getSession().getAttribute("validUser");
        if (validUser == null) {
            return "Войдите в систему";
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/loginCheck", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String checkLogin(@RequestBody User user, HttpServletRequest request) {
        if (user.getLogin() != null
                && user.getPassword() != null
                && !user.getLogin().isEmpty()
                && !user.getPassword().isEmpty()) {
            User validUser = userService.authenticateUser(user);
            if (validUser != null) {
                request.getSession().setAttribute("validUser", validUser);
            }
        }
        User validUser = (User) request.getSession().getAttribute("validUser");
        if (validUser != null) {
            return validUser.getLogin();
        } else {
            return null;
        }
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @RequestMapping("/main")
    public ModelAndView navigateMain() {
        List<Filmshow> filmshowList = filmshowService.getFilmshowToday();
        return new ModelAndView("main", "filmshowToday", filmshowList);
    }

    @RequestMapping("/registerForm")
    public ModelAndView registerUserForm() {
        return new ModelAndView("register", "user", new User());
    }

    @RequestMapping("/register")
    public ModelAndView registerUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("register", "user", user);
        }
        userService.save(user);
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("registered", "Вы зарегистрированы");
        mav.addObject("user", new User());
        return mav;
    }

    @RequestMapping(value = "/registerCheck", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String registerCheck(@RequestParam(required = false) String login, @RequestParam(required = false) String email) {
        if (login != null && !login.isEmpty()) {
            if (!userService.checkLogin(login)) {
                return "Логин свободен";
            } else {
                return "Логин занят";
            }
        }
        if (email != null && !email.isEmpty()) {
            if (userService.checkEmail(email)) {
                return "Логин с таким email уже есть";
            }
        }
        return null;
    }

    @RequestMapping("filmshow")
    public ModelAndView navigateFilmshow() {
        return Optional.of(filmshowService.getFilmshowWeek()
                .stream()
                .collect(
                        Collectors.groupingBy(filmshow -> new LocalDate(filmshow.getDateTime()),
                        TreeMap::new,
                        Collectors.toList())))
                .filter(filmshowMap -> !filmshowMap.isEmpty())
                .map(filmshowMap -> new ModelAndView("filmshow").addObject("filmshowMap", filmshowMap))
                .orElse(new ModelAndView("filmshow"));
    }

    @RequestMapping("film")
    public ModelAndView navigateFilm() {
        List<Film> filmList = filmService.getFilmAll();
        return new ModelAndView("film", "filmList", filmList);
    }

    @RequestMapping("news")
    public String navigateNews() {
        return "news";
    }

    @RequestMapping("about")
    public String navigateAbout() {
        return "about";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Ticket.class, ticketEditor);
    }
}
