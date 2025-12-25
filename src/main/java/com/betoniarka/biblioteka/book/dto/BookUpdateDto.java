package com.betoniarka.biblioteka.book.dto;

import jakarta.validation.constraints.Min;

import java.util.Set;

public record BookUpdateDto(
        String title,
        @Min(0) Integer count,
        Set<Long> categoryIds
) {
}
