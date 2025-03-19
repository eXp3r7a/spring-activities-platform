package com.example.spring_activities_platform.repositories;

import com.example.spring_activities_platform.entities.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RatingRepository extends CrudRepository<Rating,Long> {

    List<Rating> findByActivity_Id(Long activityId);
}
