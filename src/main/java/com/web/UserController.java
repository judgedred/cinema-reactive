package com.web;

import com.domain.User;
import com.service.ReservationService;
import com.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigInteger;

@Controller
public class UserController {

    private final UserService userService;
    private final ReservationService reservationService;

    public UserController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @RequestMapping("/admin/addUserForm")
    public Rendering addUserForm() {
        return Rendering.view("addUser").modelAttribute("user", new User()).build();
    }

    @RequestMapping("/admin/addUser")
    public Mono<Rendering> addUser(@Valid User user, BindingResult result, Model model) {
        return Mono.just(user)
                .filter(f -> !result.hasErrors())
                .flatMap(userService::save)
                .map(u -> new User())
                .defaultIfEmpty(user)
                .map(u -> Rendering.view("addUser").modelAttribute("user", u).build());
    }

    @RequestMapping("/admin/deleteUser")
    public Mono<Rendering> deleteUser(@ModelAttribute User user) {
        return Mono.justOrEmpty(user.getUserId())
                .filter(userId -> !userId.equals(BigInteger.ZERO))
                .flatMap(userService::getUserById)
                .flatMap(userService::delete)
                .thenMany(userService.getUserAll())
                .collectList()
                .map(users -> Rendering.view("deleteUser").modelAttribute("userList", users).build());

    }

    @RequestMapping("/admin/userList")
    public Rendering listUsers() {
        return Rendering.view("userList").modelAttribute("userList", userService.getUserAll()).build();
    }

    @RequestMapping(value = "/admin/checkUser/{userId}", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public Mono<String> checkUser(@PathVariable BigInteger userId) {
        return Mono.justOrEmpty(userId)
                .flatMap(userService::getUserById)
                .flatMapMany(reservationService::getReservationAllByUser)
                .collectList()
                .filter(reservations -> !reservations.isEmpty())
                .map(reservations -> "У пользователя есть брони. Сначала удалите бронь.");
    }
}
