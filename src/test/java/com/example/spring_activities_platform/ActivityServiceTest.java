package com.example.spring_activities_platform;

import com.example.spring_activities_platform.entities.Activity;
import com.example.spring_activities_platform.repositories.ActivityRepository;
import com.example.spring_activities_platform.repositories.CityRepository;
import com.example.spring_activities_platform.services.ActivityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {
    @InjectMocks
    ActivityService activityService;

    @Mock
    ActivityRepository activityRepository;

    @Mock
    CityRepository cityRepository;

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    @Test
    void testSubmitActivityWhenValid(){
        //GIVEN
        when(bindingResult.hasErrors()).thenReturn(false);
        Activity activity = new Activity();

        //WHEN
        activityService.submitActivityToDB(activity,bindingResult,model);

        //THEN
        verify(activityRepository,times(1)).save(activity);
    }

    @Test
    void testSubmitActivityWhenHasError(){
        //GIVEN
        when(bindingResult.hasErrors()).thenReturn(true);
        Activity activity = new Activity();

        //WHEN
        activityService.submitActivityToDB(activity,bindingResult,model);

        //THEN
        verify(model,times(2)).addAttribute(anyString(), any());
    }

    @Test
    void testUpdateActivityWhenHasError(){
        //GIVEN
        when(bindingResult.hasErrors()).thenReturn(true);
        Activity activity = new Activity();

        //WHEN
        activityService.updateActivity(activity,bindingResult,model);

        //THEN
        verify(model,times(1)).addAttribute(anyString(), any());
    }

    @Test
    void testUpdateActivityWhenValid(){
        //GIVEN
        when(bindingResult.hasErrors()).thenReturn(false);
        Activity activity = new Activity();

        //WHEN
        activityService.updateActivity(activity,bindingResult,model);

        //THEN
        verify(activityRepository,times(1)).save(activity);
        verify(model,times(1)).addAttribute(anyString(), any());
    }


}
