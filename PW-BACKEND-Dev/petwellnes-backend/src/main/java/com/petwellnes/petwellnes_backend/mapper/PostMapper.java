package com.petwellnes.petwellnes_backend.mapper;

import com.petwellnes.petwellnes_backend.model.dto.postDto.CreatePostDTO;
import com.petwellnes.petwellnes_backend.model.dto.postDto.PostDTO;
import com.petwellnes.petwellnes_backend.model.entity.Post;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PostMapper {

    private final ModelMapper modelMapper;

    public Post convertToEntity(CreatePostDTO createPostDTO) {
        return modelMapper.map(createPostDTO, Post.class);
    }

    public PostDTO convertToDTO(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }

    public List<PostDTO> convertToDTO(List<Post> posts) {
        return posts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
