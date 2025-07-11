package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.model.CourseAudit;
import org.example.model.Registration;
import org.example.repository.CourseAuditRepository;
import org.example.repository.RegistrationRepository;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class RegistrationService {

    @Inject
    RegistrationRepository registrationRepository;

    @Inject
    CourseAuditRepository auditRepository;

    public List<Registration> getAllRegistrations() {
        return registrationRepository.listAll();  // Panache default method
    }

    public String saveRegistration(Registration registration) {
        LocalDate today = LocalDate.now();

        List<CourseAudit> existingAudits = auditRepository.findByStudentAndCourse(
                registration.getStudentId(), registration.getCourseId());

        for (CourseAudit audit : existingAudits) {
            if (audit.getCourseStartDate() != null && audit.getCourseEndDate() != null) {
                if (!today.isBefore(audit.getCourseStartDate()) && !today.isAfter(audit.getCourseEndDate())) {
                    return "ALREADY_ENROLLED";
                }
            }
        }

        registration.setRegistrationDate(today);
        registrationRepository.save(registration);
        return "SUCCESS";
    }
}
