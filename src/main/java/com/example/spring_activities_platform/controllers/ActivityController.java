package com.example.spring_activities_platform.controllers;

import com.example.spring_activities_platform.dto.CommentDTO;
import com.example.spring_activities_platform.dto.RatingDTO;
import com.example.spring_activities_platform.entities.Activity;
import com.example.spring_activities_platform.repositories.ActivityRepository;
import com.example.spring_activities_platform.repositories.CityRepository;
import com.example.spring_activities_platform.services.ActivityService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@RequestMapping("/activities")
public class ActivityController implements WebMvcConfigurer {

    private final ActivityRepository activityRepository;
    private final CityRepository cityRepository;
    private final ActivityService activityService;

    public ActivityController(ActivityRepository activityRepository, CityRepository cityRepository, ActivityService activityService){
        this.activityRepository = activityRepository;
        this.cityRepository = cityRepository;
        this.activityService = activityService;
    }

    @GetMapping("/add")
    public String addActivityForm(Model model){
        model.addAttribute("activity", new Activity());
        model.addAttribute("cities", cityRepository.findAll());

        return "activities/add_form";
    }

    @PostMapping("/submit")
    public String submitActivityToDB(@Valid @ModelAttribute Activity activity, BindingResult bindingResult, Model model){
        return activityService.submitActivityToDB(activity, bindingResult, model);
    }

    @PostMapping("/delete")
    public String deleteActivity(Model model, @RequestParam("activity_id") Long activityId){
        activityRepository.deleteById(activityId);
        model.addAttribute("activities", activityRepository.findAll());

        return "activities/activities_table";
    }

    @PostMapping("/update")
    public String updateActivity(@Valid @ModelAttribute Activity activity, BindingResult bindingResult, Model model) {
        return activityService.updateActivity(activity, bindingResult, model);
    }

    @GetMapping("/edit")
    public String editActivity(Model model, @RequestParam("activity_id") Long activityId){
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("activityEdit", activityRepository.findById(activityId));

        return "activities/edit_activity";
    }

    @GetMapping("/feedback")
    public String feedbackActivity(Model model, @RequestParam("activity_id") Long activityId){
        return activityService.feedbackActivity(model, activityId);
    }

    @GetMapping("/rate")
    public String rateActivity(@RequestParam("activity_id") Long activityId, @Valid @ModelAttribute RatingDTO rateDTO, BindingResult bindingResult, Model model){
        return activityService.rateActivity(activityId, rateDTO, bindingResult, model);
    }

    @GetMapping("/comment")
    public String commentActivity(@RequestParam("activity_id") Long activityId, @Valid @ModelAttribute CommentDTO commentDTO, BindingResult bindingResult, Model model){
        return activityService.commentActivity(activityId, commentDTO, bindingResult, model);
    }

    @GetMapping("/get-activities")
    public String getUserActivities(Model model){
        model.addAttribute("activities", activityRepository.findAll());

        return "users/activities_users_table";
    }

    @GetMapping("/get")
    public String getAllActivities(Model model){
        model.addAttribute("activities", activityRepository.findAll());

        return "activities/activities_table";
    }
    
}
