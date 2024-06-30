package com.petwellnes.petwellnes_backend.model.dto.postDto;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class CreatedPostDTO {
    private Date date;
    private Time time;
    private String category;
    private Long userId;
    private Long topic;
    private Long petType;
    private String content;
    private String image;
    private int reactions;
    private Long breed;

    public CreatedPostDTO(Date date, Time time, String category, Long userId, Long topic, Long petType, String content, String image, int reactions, Long breed) {
        this.date = date;
        this.time = time;
        this.category = category;
        this.userId = userId;
        this.topic = topic;
        this.petType = petType;
        this.content = content;
        this.image = image;
        this.reactions = reactions;
        this.breed = breed;
    }
}
