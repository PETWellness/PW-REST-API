package com.grupo2.PetWellness.services;

import org.springframework.beans.factory.annotation.Autowired;
import com.grupo2.PetWellness.models.Comment;
import com.grupo2.PetWellness.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
