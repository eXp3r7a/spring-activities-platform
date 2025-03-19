package com.example.spring_activities_platform.mappers;

import com.example.spring_activities_platform.dto.RatingDTO;
import com.example.spring_activities_platform.entities.Rating;
import org.springframework.stereotype.Component;

@Component
public class RatingMapper {

    public Rating toEntity(RatingDTO ratingDTO){
        Rating rating = new Rating();

        rating.setRate(ratingDTO.getRate());

        return rating;
    }
}
