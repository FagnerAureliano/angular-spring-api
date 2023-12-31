package com.example.crudspring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.crudspring.dto.CourseDTO;
import com.example.crudspring.dto.mapper.CourseMapper;
import com.example.crudspring.exception.RecordNotFoundException;
import com.example.crudspring.model.Course;
import com.example.crudspring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class CourseService {

    private final CourseRepository repository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository repository, CourseMapper courseMapper) {
        this.repository = repository;
        this.courseMapper = courseMapper;
    }

    public List<CourseDTO> list() {
        return repository.findAll().stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO findById(@NotNull @Positive Long id) {
        return repository.findById(id)
                .map(courseMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@Valid @NotNull CourseDTO course) {
        return courseMapper.toDTO(repository.save(courseMapper.toEntity(course)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO) {
        return repository
                .findById(id)
                .map(recordFound -> {
                    Course course = courseMapper.toEntity(courseDTO);
                    recordFound.setName(courseDTO.name());
                    recordFound.setCategory(courseMapper.convertCategoryValue(courseDTO.category()));
                    //Não pode adicionar diretamente, primeiramente deve limpar a lista 
                    // recordFound.setLessons(course.getLessons());

                    recordFound.getLessons().clear();
                    // course.getLessons().forEach(lesson -> recordFound.getLessons().add(lesson));   
                    course.getLessons().forEach(recordFound.getLessons()::add);  // forma mais clean
                    return courseMapper.toDTO(repository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        repository.delete(
                repository.findById(id)
                        .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
