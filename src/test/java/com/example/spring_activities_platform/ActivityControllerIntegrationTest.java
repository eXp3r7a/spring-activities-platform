package com.example.spring_activities_platform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ActivityControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void testAddActivityForm() throws Exception {
        mockMvc.perform(get("/activities/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Add activity</title>")))
                .andExpect(content().string(containsString("<label for=\"description\">Description</label>")))
                .andExpect(content().string(containsString("<input type=\"radio\" id=\"sport\" name=\"category\" value=\"SPORT\" class=\"custom-control-input\">")))
                .andExpect(content().string(containsString("<button type=\"submit\" class=\"btn btn-primary\">Submit</button>")));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void testGetActivitiesTable() throws Exception {
        mockMvc.perform(get("/activities/get"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Activities List</title>")))
                .andExpect(content().string(containsString("<form action=\"/activities/edit?id=1\" method=\"get\" style=\"display:inline;\">")))
                .andExpect(content().string(containsString("<button class=\"btn btn-warning\" type=\"submit\">Update</button>")))
                .andExpect(content().string(containsString("<button class=\"btn btn-danger\" type=\"submit\">Delete</button>")));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void testEditActivity() throws Exception {
        mockMvc.perform(get("/activities/edit?activity_id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Edit activity</title>")))
                .andExpect(content().string(containsString("<label for=\"duration\">Duration</label>")))
                .andExpect(content().string(containsString("<input type=\"radio\" id=\"course\" name=\"category\" value=\"COURSE\" class=\"custom-control-input\">")))
                .andExpect(content().string(containsString("<button type=\"submit\" class=\"btn btn-primary\">Update</button>")));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_USER"})
    void testFeedbackActivity() throws Exception {
        mockMvc.perform(get("/activities/feedback?activity_id=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Activity feedback</title>")))
                .andExpect(content().string(containsString("<input type=\"number\" class=\"form-control\" placeholder=\"Rate(1-10)\" aria-label=\"rate\" id=\"rate\" name=\"rate\" value=\"0\">")))
                .andExpect(content().string(containsString("<button class=\"btn btn-primary\" type=\"submit\">Give rate</button>")))
                .andExpect(content().string(containsString("<button class=\"btn btn-primary\" type=\"submit\">Comment</button>")));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_USER"})
    void testUsersGetActivities() throws Exception {
        mockMvc.perform(get("/activities/get-activities"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Activities</title>")))
                .andExpect(content().string(containsString("<form action=\"/activities/feedback?id=2\" method=\"get\" style=\"display:inline;\">")))
                .andExpect(content().string(containsString("<input type=\"hidden\" name=\"activity_id\" value=\"1\">")))
                .andExpect(content().string(containsString("<button class=\"btn btn-success\" type=\"submit\">Give rate and comment</button>")));
    }

}
