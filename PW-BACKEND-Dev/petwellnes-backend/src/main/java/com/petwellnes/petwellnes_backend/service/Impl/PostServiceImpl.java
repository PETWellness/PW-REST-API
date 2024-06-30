package com.petwellnes.petwellnes_backend.service.Impl;

import com.petwellnes.petwellnes_backend.infra.exception.ResourceNotFoundException;
import com.petwellnes.petwellnes_backend.infra.repository.PostRepository;
import com.petwellnes.petwellnes_backend.mapper.PostMapper;
import com.petwellnes.petwellnes_backend.model.dto.postDto.PostDTO;
import com.petwellnes.petwellnes_backend.model.entity.Post;
import com.petwellnes.petwellnes_backend.model.entity.User;
import com.petwellnes.petwellnes_backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<PostDTO> viewMyPosts(User user) {
        return postRepository.findAllByUserOrderByDateDesc(user).stream()
                .map(postMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Post findPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    @Override
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    @Override
    public int reactPost(Long id) {
        Post post = findPost(id);
        post.setReactions(post.getReactions() + 1);
        postRepository.save(post);
        return post.getReactions();
    }

    @Override
    public List<PostDTO> filterPosts(String category, String petType, String breed, String topic) {
        // Implement the filter logic
        return null;
    }
}
