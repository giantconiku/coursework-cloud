package com.giant.userportal.controller;

import com.giant.userportal.validations.CreateUserGroup;
import com.giant.userportal.model.User;
import com.giant.userportal.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("public")
public class PublicController {

    private final UserService userService;

    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {

        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/createUser")
    public String register(@Validated(CreateUserGroup.class) @ModelAttribute("user") User user,
                           Errors errors) {

        if (errors.hasErrors()) {
            return "register";
        }

        boolean isSaved = userService.createUser(user);

        if (isSaved) {
            return "redirect:/login?register=true";
        } else {
            return "register";
        }
    }
}