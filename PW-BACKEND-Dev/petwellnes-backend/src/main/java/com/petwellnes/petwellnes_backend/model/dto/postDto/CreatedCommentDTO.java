package com.petwellnes.petwellnes_backend.model.dto.postDto;

import lombok.Data;

@Data
public class CreatedCommentDTO {
    private String content;
    private Long commentId;
    private Long userId;
    private Long postId;
    private String userName;

    public CreatedCommentDTO(String content, Long commentId, Long userId, Long postId, String userName) {
        this.content = content;
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
        this.userName = userName;
    }
}
