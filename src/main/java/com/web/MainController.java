package com.web;

import com.domain.Filmshow;
import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import com.service.FilmService;
import com.service.FilmshowService;
import com.service.ReservationService;
import com.service.TicketService;
import com.service.UserService;
import org.springframework.http.MediaType;
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
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigInteger;
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
    public Mono<Rendering> adminLogin(@ModelAttribute User user, WebSession webSession) {
        return Mono.just(user)
                .filter(this::isLoginAndPasswordPresent)
                .flatMap(userService::authenticateAdmin)
                .doOnNext(adminUser -> webSession.getAttributes().put("adminUser", adminUser))
                .map(adminUser -> Rendering.view("adminMain").build())
                .defaultIfEmpty(Rendering.redirectTo("/admin").build());
    }

    @RequestMapping("/admin/logout")
    public Mono<Rendering> adminLogout(WebSession webSession) {
        return webSession.invalidate()
                .thenReturn(Rendering.redirectTo("/admin").build());
    }

    @RequestMapping("/reserveTicket")
    public Mono<Rendering> reserveTicket(@ModelAttribute Reservation reservation, WebSession webSession,
            @RequestParam(required = false) BigInteger filmshowId, Model model) {
        return Mono.just(reservation)
                .filter(r -> r.getTicket() != null)
                .map(r -> r.setUser(webSession.getAttribute(SESSION_VALID_USER)))
                .flatMap(reservationService::save)
                .then(Mono.justOrEmpty(filmshowId))
                .flatMap(filmshowService::getFilmshowById)
                .doOnNext(filmshow -> {
                    model.addAttribute("filteredTicketList", ticketService.getTicketFreeByFilmshow(filmshow));
                    model.addAttribute("filmshow", filmshow);
                })
                .thenReturn(Rendering.view("reserveTicket").build());
    }

    @RequestMapping("/reservationList")
    public Mono<Rendering> listUserReservations(WebSession webSession) {
        return Mono.justOrEmpty(webSession.<User>getAttribute(SESSION_VALID_USER))
                .flatMapMany(reservationService::getReservationAllByUser)
                .collectList()
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
    public Mono<String> checkLogin(@RequestBody User user, WebSession webSession) {
        return Mono.just(user)
                .filter(this::isLoginAndPasswordPresent)
                .flatMap(userService::authenticateUser)
                .doOnNext(validUser -> webSession.getAttributes().put(SESSION_VALID_USER, validUser))
                .then(Mono.fromCallable(() -> webSession.<User>getAttribute(SESSION_VALID_USER)))
                .map(User::getLogin);

    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public Mono<Void> logout(WebSession webSession) {
        return webSession.invalidate();
    }

    @RequestMapping("/main")
    public Rendering navigateMain() {
        return Rendering.view("main").build();
    }

    @RequestMapping(value = "/filmshowToday", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Rendering getFilmshowToday() {
        return Rendering.view("main :: #filmshowToday")
                .modelAttribute("filmshowToday",
                        new ReactiveDataDriverContextVariable(filmshowService.getFilmshowTodayLive(), 1))
                .build();
    }

    @RequestMapping("/registerForm")
    public Rendering registerUserForm() {
        return Rendering.view("register")
                .modelAttribute("user", new User())
                .build();
    }

    @RequestMapping("/register")
    public Mono<Rendering> registerUser(@Valid User user, BindingResult result) {
        return Mono.just(user)
                .filter(u -> !result.hasErrors())
                .flatMap(userService::save)
                .map(u -> Rendering.view("register")
                        .modelAttribute("registered", "Вы зарегистрированы")
                        .modelAttribute("user", new User())
                        .build())
                .defaultIfEmpty(Rendering.view("register").modelAttribute("user", user).build());
    }

    @RequestMapping(value = "/registerCheck", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public Mono<String> registerCheck(@RequestParam(required = false) String login, @RequestParam(required = false) String email) {
        return Mono.justOrEmpty(login)
                .filter(l -> !l.isEmpty())
                .flatMap(l -> userService.getUserByLogin(l)
                        .map(found -> "Логин занят")
                        .defaultIfEmpty("Логин свободен"))
                .switchIfEmpty(Mono.justOrEmpty(email)
                        .filter(e -> !e.isEmpty())
                        .flatMap(userService::getUserByEmail)
                        .map(found -> "Логин с таким email уже есть"));
    }

    @RequestMapping("filmshow")
    public Mono<Rendering> navigateFilmshow() {
        return filmshowService.getFilmshowWeek()
                .collect(Collectors.groupingBy(
                        (Filmshow filmshow) -> filmshow.getDateTime().toLocalDate(),
                        TreeMap::new,
                        Collectors.toList()))
                .filter(filmshowMap -> !filmshowMap.isEmpty())
                .map(filmshowMap -> Rendering.view("filmshow").modelAttribute("filmshowMap", filmshowMap).build())
                .defaultIfEmpty(Rendering.view("filmshow").build());
    }

    @RequestMapping("film")
    public Rendering navigateFilm() {
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
