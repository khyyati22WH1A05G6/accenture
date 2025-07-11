package org.example.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.model.User;
import org.example.service.UserService;

import java.util.Map;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserService userService;

    @POST
    @Path("/signup")
    public Response signup(User user) {
        try {
            Map<String, String> result = userService.signup(user);
            return Response.ok(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT)
                           .entity(Map.of("message", e.getMessage()))
                           .build();
        }
    }

    @POST
    @Path("/login")
    public Response login(User user) {
        try {
            Map<String, Object> result = userService.login(user);
            return Response.ok(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity(Map.of("message", e.getMessage()))
                           .build();
        }
    }
}
