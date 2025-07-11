package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.model.Course;
import org.example.repository.CourseRepository;

import java.util.List;

@ApplicationScoped
public class CourseService {

    @Inject
    CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.getAllCourses(); // moved to repo
    }

    public void addCourse(Course course) {
        courseRepository.save(course); // moved to repo
    }
}
