package com.petwellnes.petwellnes_backend.service;

import com.petwellnes.petwellnes_backend.model.dto.postDto.PostDTO;
import com.petwellnes.petwellnes_backend.model.entity.Post;
import com.petwellnes.petwellnes_backend.model.entity.User;

import java.util.List;

public interface PostService {
    Post createPost(Post post);
    List<PostDTO> viewMyPosts(User user);
    Post findPost(Long id);
    void deletePost(Post post);
    int reactPost(Long id);
    List<PostDTO> filterPosts(String category, String petType, String breed, String topic);
}
