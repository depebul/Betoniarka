package com.betoniarka.biblioteka.book;

import com.betoniarka.biblioteka.book.dto.BookCreateDto;
import com.betoniarka.biblioteka.book.dto.BookResponseDto;
import com.betoniarka.biblioteka.book.dto.BookUpdateDto;
import com.betoniarka.biblioteka.category.CategoryRepository;
import com.betoniarka.biblioteka.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper mapper;

    public List<BookResponseDto> getAll() {
        return bookRepository.findAll().stream().map(mapper::toDto).toList();
    }

    public BookResponseDto getById(Long id) {
        return bookRepository.findById(id).map(mapper::toDto).orElseThrow(() ->
                new ResourceNotFoundException("Book with id '%d' not found".formatted(id)));
    }

    public BookResponseDto create(BookCreateDto createDto) {
        var entityToSave = mapper.toEntity(createDto);
        if (createDto.categoryIds() != null) {
            var categories = categoryRepository.findAllById(createDto.categoryIds());
            entityToSave.setCategories(categories);
        }

        var savedEntity = bookRepository.save(entityToSave);
        return mapper.toDto(savedEntity);
    }

    public BookResponseDto update(Long id, BookUpdateDto updateDto) {
        var existingEntity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id '%d' not found".formatted(id)));

        mapper.update(updateDto, existingEntity);
        if (updateDto.categoryIds() != null) {
            var categories = categoryRepository.findAllById(updateDto.categoryIds());
            existingEntity.setCategories(categories);
        }

        var savedEntity = bookRepository.save(existingEntity);
        return mapper.toDto(savedEntity);
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id))
            throw new ResourceNotFoundException("Book with id '%d' not found".formatted(id));

        bookRepository.deleteById(id);
    }

}
