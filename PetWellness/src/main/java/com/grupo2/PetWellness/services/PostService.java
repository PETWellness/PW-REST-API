package com.grupo2.PetWellness.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo2.PetWellness.models.Post;
import com.grupo2.PetWellness.repositories.PostRepository;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}