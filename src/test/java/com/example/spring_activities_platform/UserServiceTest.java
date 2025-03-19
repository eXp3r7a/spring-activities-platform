package com.example.spring_activities_platform;

import com.example.spring_activities_platform.dto.UserDTO;
import com.example.spring_activities_platform.entities.User;
import com.example.spring_activities_platform.mappers.UserMapper;
import com.example.spring_activities_platform.repositories.UserRepository;
import com.example.spring_activities_platform.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    @Test
    void testRegisterUserWhenHasError(){
        //GIVEN
        when(bindingResult.hasErrors()).thenReturn(true);
        UserDTO userDTO = new UserDTO();

        //WHEN
        String viewName = userService.registerUser(userDTO,bindingResult,model);

        //THEN
        assertEquals("users/register", viewName);
        verify(model).addAttribute(eq("userDTO"),any(UserDTO.class));
    }

    @Test
    void testRegisterUserWhenValid(){
        //GIVEN
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@test.com");
        userDTO.setPassword("pass123");
        userDTO.setCheckPassword("pass123");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userRepository.getUserByEmail(userDTO.getEmail())).thenReturn(null);
        when(userMapper.toEntity(userDTO)).thenReturn(new User());

        //WHEN
        String viewName = userService.registerUser(userDTO, bindingResult, model);

        //THEN
        assertEquals("users/profile", viewName);
        verify(userRepository).save(any(User.class));

    }

    @Test
    void testRegisterUserWhenEmailIsTaken(){
        //GIVEN
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@test.com");
        userDTO.setPassword("pass123");
        userDTO.setCheckPassword("pass123");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userRepository.getUserByEmail(userDTO.getEmail())).thenReturn(new User());

        //WHEN
        String viewName = userService.registerUser(userDTO, bindingResult, model);

        //THEN
        assertEquals("users/register", viewName);
        verify(model).addAttribute(eq("emailCheckerError"), anyString());
    }

    @Test
    void testRegisterUserWhenPasswordMismatch(){
        //GIVEN
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@test.com");
        userDTO.setPassword("pass123");
        userDTO.setCheckPassword("pass456");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userRepository.getUserByEmail(userDTO.getEmail())).thenReturn(null);

        //WHEN
        String viewName = userService.registerUser(userDTO, bindingResult, model);

        //THEN
        assertEquals("users/register", viewName);
        verify(model).addAttribute(eq("passwordCheckerError"), anyString());
    }


}
