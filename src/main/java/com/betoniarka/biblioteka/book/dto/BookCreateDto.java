package com.betoniarka.biblioteka.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

// TODO add author

public record BookCreateDto(
        @NotBlank String title,
        @NotNull @Min(0) Integer count,
        Set<Long> categoryIds
) {

    public BookCreateDto {
        if (categoryIds == null) {
            categoryIds = Set.of();
        }
    }

}
