package com.petwellnes.petwellnes_backend.model.dto.petDto;

import lombok.Data;

@Data
public class PetDto {
    private String name;
    private String species;
    private String breed;
    private Integer age;
    private String photo;
}
