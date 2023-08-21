package com.example.crudspring.dto.mapper;

import org.springframework.stereotype.Component;

import com.example.crudspring.dto.CourseDTO;
import com.example.crudspring.enums.Category;
import com.example.crudspring.model.Course;

@Component
public class CourseMapper {
    public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }
        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(), course.getLessons());
    }

    public Course toEntity(CourseDTO courseDTO) {
        if (courseDTO == null)
            return null;

        /* Com @Builder */
        Course course = Course.builder()
                .id(courseDTO.id() != null ? courseDTO.id() : null)
                .name(courseDTO.name())
                .category(convertCategoryValue(courseDTO.category()))
                .build();

        /* Forma tradicional */
        // Course course = new Course();
        // if (courseDTO.id() != null) {
        // course.setId(courseDTO.id());
        // }
        // course.setName(courseDTO.name());
        // course.setCategory(courseDTO.category());

        return course;
    }

    public Category convertCategoryValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Web Development" -> Category.WEB_DEVELOPMENT;
            case "Mobile Development" -> Category.MOBILE_DEVELOPMENT;
            default -> throw new IllegalArgumentException("Categoria inválida: " + value);
        };
    }
}
