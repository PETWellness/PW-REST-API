package com.grupo2.PetWellness.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grupo2.PetWellness.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}