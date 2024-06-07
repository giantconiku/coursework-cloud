package com.giant.userportal.controller;

import com.giant.userportal.validations.UpdateUserGroup;
import com.giant.userportal.model.Profile;
import com.giant.userportal.model.User;
import com.giant.userportal.repository.UserRepository;
import com.giant.userportal.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProfileController {

    UserRepository userRepository;
    UserService userService;

    @Autowired
    public ProfileController(UserRepository userRepository,
                             UserService userService) {

        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/displayProfile")
    public ModelAndView displayProfile(Authentication authentication,
                                        HttpSession session) {

        Optional<User> optionalUser = userRepository
                .findByEmailOrPhoneNumber(authentication.getName(), authentication.getName());

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();
            session.setAttribute("loggedInUser", user);

            Profile profile = new Profile();
            profile.setFirstName(user.getFirstName());
            profile.setFathersName(user.getFathersName());
            profile.setLastName(user.getLastName());
            profile.setPhoneNumber(user.getPhoneNumber());
            profile.setBirthday(user.getBirthday());
            profile.setEmail(user.getEmail());

            if(user.getProfileImagePath() != null) {
                profile.setProfileImagePath(user.getProfileImagePath());
            } else {
                profile.setProfileImagePath("default-profile-picture.jpeg");
            }

            ModelAndView modelAndView = new ModelAndView("profile");
            modelAndView.addObject("profile", profile);
            return modelAndView;
        } else {
            throw new RuntimeException();
        }
    }

    @PostMapping(value = "/updateProfile")
    public String updateProfile(@Validated(UpdateUserGroup.class) @ModelAttribute("profile") Profile profile,
                                @RequestParam("profile-photo") MultipartFile file,
                                Errors errors,
                                HttpSession session) {

        if(errors.hasErrors()){
            return "profile";
        }

        User user = (User) session.getAttribute("loggedInUser");
        user.setFirstName(profile.getFirstName());
        user.setFathersName(profile.getFathersName());
        user.setLastName(profile.getLastName());
        user.setPhoneNumber(profile.getPhoneNumber());
        user.setBirthday(profile.getBirthday());
        user.setEmail(profile.getEmail());

        try {
            User updatedUser = userService.updateUser(user, file);
            session.setAttribute("loggedInUser", updatedUser);
            return "redirect:/displayUpdatedProfile";
        } catch (Exception e) {
            return "redirect:/displayUpdatedProfile";
        }
    }

    @GetMapping("/displayUpdatedProfile")
    public ModelAndView displayProfile(HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        Profile profile = new Profile();
        profile.setFirstName(user.getFirstName());
        profile.setFathersName(user.getFathersName());
        profile.setLastName(user.getLastName());
        profile.setPhoneNumber(user.getPhoneNumber());
        profile.setBirthday(user.getBirthday());
        profile.setEmail(user.getEmail());

        if(user.getProfileImagePath() != null) {
            profile.setProfileImagePath(user.getProfileImagePath());
        } else {
            profile.setProfileImagePath("default-profile-picture.jpeg");
        }

        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("profile", profile);
        return modelAndView;
    }
}

