package com.example.spring_activities_platform.controllers;

import com.example.spring_activities_platform.dto.UserDTO;
import com.example.spring_activities_platform.entities.Activity;
import com.example.spring_activities_platform.entities.User;
import com.example.spring_activities_platform.mappers.UserMapper;
import com.example.spring_activities_platform.repositories.UserRepository;
import com.example.spring_activities_platform.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/login")
    public String userLoginForm(Model model){
        model.addAttribute("activity", new Activity());
        return "users/login";
    }

    @GetMapping("/profile")
    public String profile(){
        return "users/profile";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("userDTO", new UserDTO());
        return "users/register";
    }

    @PostMapping("/registerUser")
    public String registerUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model){
        return userService.registerUser(userDTO, bindingResult, model);
    }
}
