package com.example.spring_activities_platform.repositories;

import com.example.spring_activities_platform.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    public User getUserByEmail(@Param("email") String email);
}
