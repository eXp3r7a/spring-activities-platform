package com.example.spring_activities_platform.services;

import com.example.spring_activities_platform.dto.UserDTO;
import com.example.spring_activities_platform.entities.User;
import com.example.spring_activities_platform.mappers.UserMapper;
import com.example.spring_activities_platform.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public String registerUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("userDTO", new UserDTO());
            return "users/register";
        }
        if(userRepository.getUserByEmail(userDTO.getEmail()) == null){
            if(validatePassword(userDTO.getPassword(),userDTO.getCheckPassword())){
                User user = userMapper.toEntity(userDTO);
                userRepository.save(user);
            }
            else{
                model.addAttribute("passwordCheckerError", "Check password! Passwords is not same!");
                return "users/register";
            }
        }else {
            model.addAttribute("emailCheckerError", "This email is taken!");
            return "users/register";
        }
        return "users/profile";
    }

    private boolean validatePassword(String password, String checkPassword){
        return password.equals(checkPassword);
    }

}
