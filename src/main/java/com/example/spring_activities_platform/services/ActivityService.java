package com.example.spring_activities_platform.services;

import com.example.spring_activities_platform.dto.CommentDTO;
import com.example.spring_activities_platform.dto.RatingDTO;
import com.example.spring_activities_platform.entities.Activity;
import com.example.spring_activities_platform.entities.Comment;
import com.example.spring_activities_platform.entities.Rating;
import com.example.spring_activities_platform.mappers.CommentMapper;
import com.example.spring_activities_platform.mappers.RatingMapper;
import com.example.spring_activities_platform.repositories.*;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;

    private final RatingMapper ratingMapper;
    private final CommentMapper commentMapper;

    public ActivityService(ActivityRepository activityRepository, CommentRepository commentRepository, CommentMapper commentMapper, RatingMapper ratingMapper, CityRepository cityRepository, UserRepository userRepository, RatingRepository ratingRepository){
        this.activityRepository = activityRepository;
        this.cityRepository = cityRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.commentRepository = commentRepository;

        this.ratingMapper = ratingMapper;
        this.commentMapper = commentMapper;
    }

    public String feedbackActivity(Model model, @RequestParam("activity_id") Long activityId){
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("activities", activityRepository.findById(activityId).get());
        model.addAttribute("rateDTO", new RatingDTO());
        model.addAttribute("commentDTO", new CommentDTO());

        getRatingsListByActivityId(model, activityId);
        getCommentsListByActivityId(model,activityId);

        return "users/feedback_activity";
    }

    private void getCommentsListByActivityId(Model model, Long activityId){
        List<Comment> commentsListByActivityId = commentRepository.findByActivity_Id(activityId);

        model.addAttribute("comments", commentsListByActivityId);
    }

    private void getRatingsListByActivityId(Model model, Long activityId){
        List<Rating> ratingsListByActivityId = ratingRepository.findByActivity_Id(activityId);
        double overallActivityRate = 0;
        int activityRateCounter = 0;

        for(Rating rate : ratingsListByActivityId){
            overallActivityRate += rate.getRate();
            activityRateCounter++;
        }
        if(activityRateCounter != 0){
            overallActivityRate /= activityRateCounter;
        }

        model.addAttribute("overallRating", Math.round(overallActivityRate * Math.pow(10,2))/Math.pow(10,2));
        model.addAttribute("givenRates", activityRateCounter);
    }

    public String rateActivity(@RequestParam("activity_id") Long activityId, @Valid @ModelAttribute RatingDTO rateDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return feedbackActivity(model,activityId);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();

        Rating rate = ratingMapper.toEntity(rateDto);
        rate.setActivity(activityRepository.findById(activityId).get());
        rate.setUser(userRepository.findById(userDetails.getId()).get());
        ratingRepository.save(rate);

        return feedbackActivity(model,activityId);
    }

    public String commentActivity(@RequestParam("activity_id") Long activityId, @Valid @ModelAttribute CommentDTO commentDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return feedbackActivity(model,activityId);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();

        Comment comment = commentMapper.toEntity(commentDTO);
        comment.setActivity(activityRepository.findById(activityId).get());
        comment.setUser(userRepository.findById(userDetails.getId()).get());
        commentRepository.save(comment);

        return feedbackActivity(model,activityId);
    }

    public String updateActivity(@Valid @ModelAttribute Activity activity, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("activities", activityRepository.findAll());
            return "activities/edit_activity";
        }

        activityRepository.save(activity);
        model.addAttribute("activities", activityRepository.findAll());
        return "activities/activities_table";
    }

    public String submitActivityToDB(@Valid @ModelAttribute Activity activity, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("activity", new Activity());
            model.addAttribute("cities", cityRepository.findAll());

            return "activities/add_form";
        }

        activityRepository.save(activity);
        return "activities/successfully_added";
    }
}
