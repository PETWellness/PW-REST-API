package com.grupo2.PetWellness.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grupo2.PetWellness.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}