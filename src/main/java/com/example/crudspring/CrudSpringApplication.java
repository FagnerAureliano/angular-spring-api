package com.example.crudspring;

import com.example.crudspring.enums.Category;
import com.example.crudspring.model.Course;
import com.example.crudspring.model.Lesson;
import com.example.crudspring.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudSpringApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(CourseRepository courseRepository) {
        return args -> {
            courseRepository.deleteAll();

            Course c = new Course();
            c.setName("Angular com Spring");
            c.setCategory(Category.WEB_DEVELOPMENT);

            Lesson l = new Lesson();
            l.setName("Introdução");
            l.setYoutubeUrl("watch?v=1");
            l.setCourse(c);
            c.getLessons().add(l);

             Lesson l2 = new Lesson();
            l2.setName("Angular");
            l2.setYoutubeUrl("watch?v=2");
            l2.setCourse(c);
            c.getLessons().add(l2);

            courseRepository.save(c);
        };
    }

}
