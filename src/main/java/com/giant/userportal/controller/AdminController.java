package com.giant.userportal.controller;

import com.giant.userportal.model.Profile;
import com.giant.userportal.model.User;
import com.giant.userportal.repository.UserRepository;
import com.giant.userportal.service.UserService;
import com.giant.userportal.validations.UpdateUserGroup;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/displayUsers")
    public ModelAndView displayUsers() {

        List<User> users = userService.getAllUsers();
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") int userId) {

        userService.deleteUserById(userId);
        return "redirect:/admin/displayUsers";
    }

    @GetMapping("/displayUserProfile")
    public ModelAndView displayUserProfile(@RequestParam("userId") int userId) {

        Profile profile = userService.getUserProfile(userId);
        ModelAndView modelAndView = new ModelAndView("other_user_profile");
        modelAndView.addObject("profile", profile);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping(value = "/updateProfile")
    public String updateProfile(@Validated(UpdateUserGroup.class) @ModelAttribute("profile") Profile profile,
                                @RequestParam("profile-photo") MultipartFile file,
                                @RequestParam("userId") int userId,
                                Errors errors) {

        if(errors.hasErrors()){
            return "other_user_profile";
        }

        User user = userService.getUserById(userId);
        user.setFirstName(profile.getFirstName());
        user.setFathersName(profile.getFathersName());
        user.setLastName(profile.getLastName());
        user.setPhoneNumber(profile.getPhoneNumber());
        user.setBirthday(profile.getBirthday());
        user.setEmail(profile.getEmail());

        try {
            userService.updateUser(user, file);
            return "redirect:/admin/displayUsers";
        } catch (Exception e) {
            return "redirect:/admin/displayUserProfile?userId=" + userId;
        }
    }
}
