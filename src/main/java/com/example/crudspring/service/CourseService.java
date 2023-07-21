package com.example.crudspring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.crudspring.model.Course;
import com.example.crudspring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public List<Course> list() {
        return repository.findAll();
    }

    public Optional<Course> findById(@NotNull @Positive Long id) {
        return repository.findById(id);
    }

    public Course create(@Valid Course course) {
        return repository.save(course);
    }

    public Optional<Course> update(@NotNull @Positive Long id, @Valid Course course) {
        return repository
                .findById(id)
                .map(recordFound -> {
                    recordFound.setName(course.getName());
                    recordFound.setCategory(course.getCategory());
                    return repository.save(recordFound);
                });
    }

    public boolean delete(@PathVariable @NotNull @Positive Long id) {
        return repository
                .findById(id)
                .map(recordFound -> {
                    repository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }
}