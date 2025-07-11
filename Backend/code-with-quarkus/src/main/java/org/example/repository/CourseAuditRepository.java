package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.model.CourseAudit;

import java.util.List;

@ApplicationScoped
public class CourseAuditRepository implements PanacheRepository<CourseAudit> {

    public CourseAudit findByRegistrationId(Integer registrationId) {
        return find("registrationId", registrationId).firstResult();
    }

    public List<CourseAudit> findByStudentAndCourse(Integer studentId, Integer courseId) {
        return find("studentId = ?1 and courseId = ?2", studentId, courseId).list();
    }
}
