package com.petwellnes.petwellnes_backend.model.dto.postDto;

import lombok.Data;

@Data
public class PostDTO {
    private Long id;
    private String category;
    private Long topic;
    private Long petType;
    private String image;
    private String video;
    private String content;
    private Long userId;
    private int reactions;
    private Long breed;
}
