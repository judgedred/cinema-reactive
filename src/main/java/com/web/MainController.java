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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebSession;

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
    public String adminView() {
        return "admin";
    }

    @RequestMapping("/admin/login")
    public String adminLogin(@ModelAttribute User user, WebSession webSession) {
        if (user.getLogin() != null && user.getPassword() != null && !user.getLogin().isEmpty() && !user
                .getPassword()
                .isEmpty()) {
            User adminUser = userService.authenticateAdmin(user);
            if (adminUser != null) {
                webSession.getAttributes().put("adminUser", adminUser);
                return "adminMain";
            }
        }
        return "redirect:/admin";
    }

    @RequestMapping("/admin/logout")
    public String adminLogout(WebSession webSession) {
        webSession.invalidate().block();    // TODO
        return "redirect:/admin";
    }

    @RequestMapping("/reserveTicket")
    public String reserveTicket(@ModelAttribute Reservation reservation, WebSession webSession,
            @RequestParam(required = false) BigInteger filmshowId, Model model) {
        User user = webSession.getAttribute("validUser");
        Optional.of(reservation)
                .filter(r -> r.getTicket() != null)
                .map(r -> r.setUser(user))
                .ifPresent(reservationService::save);
        Optional.ofNullable(filmshowId)
                .flatMap(filmshowService::getFilmshowById)
                .ifPresent(filmshow -> {
                    model.addAttribute("filteredTicketList", ticketService.getTicketFreeByFilmshow(filmshow));
                    model.addAttribute("filmshow", filmshow);
                });
        return "reserveTicket";
    }

    @RequestMapping("/reservationList")
    public String listUserReservations(WebSession webSession, Model model) {
        User user = webSession.getAttribute("validUser");
        if (user != null) {
            List<Reservation> reservationList = reservationService.getReservationAllByUser(user);
            model.addAttribute("filteredReservationList", reservationList);
        }
        return "userReservationList";
    }

    @RequestMapping(value = "/authCheck", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String authCheck(WebSession webSession) {
        User validUser = webSession.getAttribute("validUser");
        if (validUser == null) {
            return "Войдите в систему";
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/loginCheck", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String checkLogin(@RequestBody User user, WebSession webSession) {
        if (user.getLogin() != null
                && user.getPassword() != null
                && !user.getLogin().isEmpty()
                && !user.getPassword().isEmpty()) {
            User validUser = userService.authenticateUser(user);
            if (validUser != null) {
                webSession.getAttributes().put("validUser", validUser);
            }
        }
        User validUser = webSession.getAttribute("validUser");
        if (validUser != null) {
            return validUser.getLogin();
        } else {
            return null;
        }
    }

    @RequestMapping("/logout")
    public ServerResponse logout(WebSession webSession) {
        webSession.invalidate().block();
        return ServerResponse.ok().build().block();
    }

    @RequestMapping("/main")
    public String navigateMain(Model model) {
        List<Filmshow> filmshowList = filmshowService.getFilmshowToday();
        model.addAttribute("filmshowToday", filmshowList);
        return "main";
    }

    @RequestMapping("/registerForm")
    public String registerUserForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.save(user);
        model.addAttribute("registered", "Вы зарегистрированы");
        model.addAttribute("user", new User());
        return "register";
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
    public String navigateFilmshow(Model model) {
        return Optional.of(filmshowService.getFilmshowWeek()
                .stream()
                .collect(Collectors.groupingBy(
                        filmshow -> filmshow.getDateTime().toLocalDate(),
                        TreeMap::new,
                        Collectors.toList())))
                .filter(filmshowMap -> !filmshowMap.isEmpty())
                .map(filmshowMap -> {
                    model.addAttribute("filmshowMap", filmshowMap);
                    return "filmshow";
                })
                .orElse("filmshow");
    }

    @RequestMapping("film")
    public String navigateFilm(Model model) {
        List<Film> filmList = filmService.getFilmAll();
        model.addAttribute("filmList", filmList);
        return "film";
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
