package com.example.spring_activities_platform.mappers;

import com.example.spring_activities_platform.dto.CommentDTO;
import com.example.spring_activities_platform.entities.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment toEntity(CommentDTO commentDTO){
        Comment comment = new Comment();

        comment.setComment(commentDTO.getComment());

        return comment;
    }
}
