package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.model.Course;

import java.util.List;

@ApplicationScoped
public class CourseRepository implements PanacheRepository<Course> {

    public List<Course> getAllCourses() {
        return listAll(); 
    }

    public void save(Course course) {
        persist(course); 
    }
}
