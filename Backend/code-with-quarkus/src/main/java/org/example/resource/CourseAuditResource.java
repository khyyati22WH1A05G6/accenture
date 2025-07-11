package org.example.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.model.CourseAudit;
import org.example.service.CourseAuditService;

import java.util.List;
import java.util.Map;

@Path("/course-audits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseAuditResource {

    @Inject
    CourseAuditService auditService;

    @GET
    public List<CourseAudit> getAll() {
        return auditService.getAll();
    }

    @POST
    @Transactional
    public Response create(CourseAudit audit) {
        try {
            Map<String, Object> result = auditService.createAudit(audit);
            return Response.status(Response.Status.CREATED).entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(Map.of("message", e.getMessage()))
                           .build();
        }
    }
}
