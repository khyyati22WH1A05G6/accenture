package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.model.CourseAudit;
import org.example.repository.CourseAuditRepository;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class CourseAuditService {

    @Inject
    CourseAuditRepository auditRepository;

    public List<CourseAudit> getAll() {
        return auditRepository.listAll();
    }

    public Map<String, Object> createAudit(CourseAudit audit) {

        if (audit.getRegistrationId() == null || audit.getRegistrationId() <= 0) {
            return Map.of("message", "Invalid or missing registrationId.");
        }

        if (audit.getStudentId() == null || audit.getStudentId() <= 0) {
            return Map.of("message", "Invalid or missing studentId.");
        }

        if (audit.getCourseId() == null || audit.getCourseId() <= 0) {
            return Map.of("message", "Invalid or missing courseId.");
        }

        if (audit.getCourseStartDate() == null || audit.getCourseEndDate() == null) {
            return Map.of("message", "Course start and end dates must not be null.");
        }

        CourseAudit existing = auditRepository.findByRegistrationId(audit.getRegistrationId());
        if (existing != null) {
            return Map.of("message", "Audit already exists for registrationId: " + audit.getRegistrationId());
        }

        auditRepository.persist(audit);

        return Map.of(
            "message", "Audit entry saved successfully!",
            "audit", audit
        );
    }

}
