package org.example.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.model.Course;
import org.example.service.CourseService;

import java.util.List;

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseResource {

    @Inject
    CourseService courseService;

    @GET
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @POST
    @Transactional
    public Course addCourse(Course course) {
        courseService.addCourse(course);
        return course;
    }
}
