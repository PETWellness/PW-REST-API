package com.petwellnes.petwellnes_backend.model.dto.postDto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private Long userId;
    private Long postId;
}
