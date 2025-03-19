package com.example.spring_activities_platform.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Component;

@Component
public class RatingDTO {

    @Min(1)
    @Max(10)
    private int rate;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
