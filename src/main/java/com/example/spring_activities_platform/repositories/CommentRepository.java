package com.example.spring_activities_platform.repositories;

import com.example.spring_activities_platform.entities.Comment;
import com.example.spring_activities_platform.entities.Rating;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findByActivity_Id(Long activityId);
}
