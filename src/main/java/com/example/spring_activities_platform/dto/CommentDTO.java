package com.example.spring_activities_platform.dto;

import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
public class CommentDTO {
    @Size(min = 2, max = 511, message = "Enter comment. Can`t send empty field!")
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
