package com.example.spring_activities_platform;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testLoginForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<label for=\"email\">Email address</label>")))
                .andExpect(content().string(containsString("<input type=\"email\" class=\"form-control\" id=\"email\" name=\"email\" aria-describedby=\"emailHelp\" placeholder=\"Enter email\">")))
                .andExpect(content().string(containsString("<label for=\"password\">Password</label>")))
                .andExpect(content().string(containsString("<button type=\"submit\" class=\"btn btn-primary\">Sign in</button>")))
                .andExpect(content().string(containsString("<input type=\"password\" class=\"form-control\" id=\"password\" name=\"password\" placeholder=\"Password\">")));
    }

    @Test
    void testRegisterForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<label for=\"email\">Email address</label>")))
                .andExpect(content().string(containsString("<input type=\"email\" class=\"form-control\" id=\"email\" aria-describedby=\"emailHelp\" placeholder=\"Enter email\" name=\"email\" value=\"\">")))
                .andExpect(content().string(containsString("<label for=\"password\">Password</label>")))
                .andExpect(content().string(containsString("<label for=\"repeatPassword\">Repeat password</label>")))
                .andExpect(content().string(containsString("<input type=\"password\" class=\"form-control\" id=\"password\" placeholder=\"Password\" name=\"password\" value=\"\">")))
                .andExpect(content().string(containsString("<button type=\"submit\" class=\"btn btn-primary\">Sign Up</button>")))
                .andExpect(content().string(containsString("<input type=\"password\" class=\"form-control\" id=\"repeatPassword\" placeholder=\"Repeat password\" name=\"checkPassword\" value=\"\">")));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    void testProfilePage() throws Exception {
        mockMvc.perform(get("/profile"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Welcome to Profile Page</title>")))
                .andExpect(content().string(containsString("<h2>Welcome</h2>")))
                .andExpect(content().string(containsString("<p>User Profile</p>")))
                .andExpect(content().string(containsString("<form action=\"/logout\" method=\"post\">")))
                .andExpect(content().string(containsString("<input type=\"submit\" value=\"Sign Out\"/>")))
                .andExpect(content().string(containsString("<p>Authenticated user's role: <span>[ROLE_USER]</span></p>")));
    }
}
