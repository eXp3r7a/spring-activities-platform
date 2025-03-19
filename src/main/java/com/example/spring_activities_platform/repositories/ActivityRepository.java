package com.example.spring_activities_platform.repositories;

import com.example.spring_activities_platform.entities.Activity;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<Activity, Long>  {
}
