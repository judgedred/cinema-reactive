package com.web;

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
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private static final String SESSION_VALID_USER = "validUser";
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
    public Rendering adminView() {
        return Rendering.view("admin").build();
    }

    @RequestMapping("/admin/login")
    public Rendering adminLogin(@ModelAttribute User user, WebSession webSession) {
        if (isLoginAndPasswordPresent(user)) {
            User adminUser = userService.authenticateAdmin(user);
            if (adminUser != null) {
                webSession.getAttributes().put("adminUser", adminUser);
                return Rendering.view("adminMain").build();
            }
        }
        return Rendering.redirectTo("/admin").build();
    }

    @RequestMapping("/admin/logout")
    public Mono<Rendering> adminLogout(WebSession webSession) {
        return webSession.invalidate()
                .thenReturn(Rendering.redirectTo("/admin").build());
    }

    @RequestMapping("/reserveTicket")
    public Rendering reserveTicket(@ModelAttribute Reservation reservation, WebSession webSession,
            @RequestParam(required = false) BigInteger filmshowId, Model model) {
        User user = webSession.getAttribute(SESSION_VALID_USER);
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
        return Rendering.view("reserveTicket").build();
    }

    @RequestMapping("/reservationList")
    public Mono<Rendering> listUserReservations(WebSession webSession) {
        return Mono.justOrEmpty(webSession.<User>getAttribute(SESSION_VALID_USER))
                .map(reservationService::getReservationAllByUser)
                .switchIfEmpty(Mono.just(Collections.emptyList()))
                .map(reservations -> Rendering.view("userReservationList")
                        .modelAttribute("filteredReservationList", reservations)
                        .build());
    }

    @RequestMapping(value = "/authCheck", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String authCheck(WebSession webSession) {
        User validUser = webSession.getAttribute(SESSION_VALID_USER);
        if (validUser == null) {
            return "Войдите в систему";
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/loginCheck", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String checkLogin(@RequestBody User user, WebSession webSession) {
        if (isLoginAndPasswordPresent(user)) {
            User validUser = userService.authenticateUser(user);
            if (validUser != null) {
                webSession.getAttributes().put(SESSION_VALID_USER, validUser);
            }
        }
        User validUser = webSession.getAttribute(SESSION_VALID_USER);
        if (validUser != null) {
            return validUser.getLogin();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public Mono<Void> logout(WebSession webSession) {
        return webSession.invalidate();
    }

    @RequestMapping("/main")
    public Rendering navigateMain() {
        return Rendering.view("main")
                .modelAttribute("filmshowToday", filmshowService.getFilmshowToday())
                .build();
    }

    @RequestMapping("/registerForm")
    public Rendering registerUserForm() {
        return Rendering.view("register")
                .modelAttribute("user", new User())
                .build();
    }

    @RequestMapping("/register")
    public Rendering registerUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return Rendering.view("register")
                    .modelAttribute("user", user)
                    .build();
        }
        userService.save(user);
        return Rendering.view("register")
                .modelAttribute("registered", "Вы зарегистрированы")
                .modelAttribute("user", new User())
                .build();
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
    public Rendering navigateFilmshow(Model model) {
        return Optional.of(filmshowService.getFilmshowWeek()
                .stream()
                .collect(Collectors.groupingBy(
                        filmshow -> filmshow.getDateTime().toLocalDate(),
                        TreeMap::new,
                        Collectors.toList())))
                .filter(filmshowMap -> !filmshowMap.isEmpty())
                .map(filmshowMap -> Rendering.view("filmshow").modelAttribute("filmshowMap", filmshowMap).build())
                .orElse(Rendering.view("filmshow").build());
    }

    @RequestMapping("film")
    public Rendering navigateFilm(Model model) {
        return Rendering.view("film")
                .modelAttribute("filmList", filmService.getFilmAll())
                .build();
    }

    @RequestMapping("news")
    public Rendering navigateNews() {
        return Rendering.view("news").build();
    }

    @RequestMapping("about")
    public Rendering navigateAbout() {
        return Rendering.view("about").build();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Ticket.class, ticketEditor);
    }

    private boolean isLoginAndPasswordPresent(User user) {
        return user.getLogin() != null
                && user.getPassword() != null
                && !user.getLogin().isEmpty()
                && !user.getPassword().isEmpty();
    }
}
