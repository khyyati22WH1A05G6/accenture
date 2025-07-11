package org.example.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.model.Registration;
import org.example.service.RegistrationService;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("/registrations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistrationResource {

    @Inject
    RegistrationService registrationService;

    @GET
    public List<Registration> getAll() {
        return registrationService.getAllRegistrations();
    }

    @POST
    @Transactional
    public Response create(Registration registration) {
        if (registration.getStudentId() == null || registration.getStudentId() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", "Invalid or missing studentId."))
                    .build();
        }

        if (registration.getCourseId() == null || registration.getCourseId() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", "Invalid or missing courseId."))
                    .build();
        }

        String result = registrationService.saveRegistration(registration);

        if (result.equals("ALREADY_ENROLLED")) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of("message", "Selected course is already active in enrolled courses."))
                    .build();
        }

        URI uri = URI.create("/registrations/" + registration.getRegistrationId());
        return Response.created(uri)
                .entity(Map.of(
                        "message", "Registration saved successfully!",
                        "registration", registration
                ))
                .build();
    }
}
