package com.petwellnes.petwellnes_backend.infra.repository;

import com.petwellnes.petwellnes_backend.model.entity.Comment;
import com.petwellnes.petwellnes_backend.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
}
