package com.giant.userportal.controller;

import com.giant.userportal.model.User;
import com.giant.userportal.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class DashboardController {

    private final UserRepository userRepository;

    @Autowired
    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboardPage(Authentication authentication,
                                    HttpSession session) {

        Optional<User> optionalUser = userRepository
                .findByEmailOrPhoneNumber(authentication.getName(), authentication.getName());

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();
            session.setAttribute("loggedInUser", user);
            return "dashboard";
        } else {
            throw new RuntimeException();
        }
    }
}
