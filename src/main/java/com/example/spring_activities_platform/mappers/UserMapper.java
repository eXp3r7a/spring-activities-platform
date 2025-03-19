package com.example.spring_activities_platform.mappers;

import com.example.spring_activities_platform.constants.Role;
import com.example.spring_activities_platform.dto.UserDTO;
import com.example.spring_activities_platform.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserMapper(BCryptPasswordEncoder bCryptPasswordEncoder){
        this.bCryptPasswordEncoder =bCryptPasswordEncoder;
    }

    public User toEntity(UserDTO userDTO){
        User user = new User();

        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        user.setRole(Role.USER);
        user.setEnabled(true);

        return user;
    }
}
