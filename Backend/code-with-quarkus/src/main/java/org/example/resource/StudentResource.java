package org.example.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.model.Student;
import org.example.service.StudentService;

import java.util.List;
import java.util.Map;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

    @Inject
    StudentService studentService;

    @POST
    @Transactional
    public Response registerStudent(Student student) {
        if (student.getEmail() == null || !student.getEmail().endsWith("@gmail.com")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", "Email must end with @gmail.com.")).build();
        }

        if (student.getPhone() == null || !student.getPhone().matches("\\d{10}")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", "Phone number must be exactly 10 digits.")).build();
        }

        Student existingStudent = studentService.findByEmail(student.getEmail());
        if (existingStudent != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of("message", "A student with this email already exists.")).build();
        }

        studentService.save(student);
        return Response.ok(Map.of(
                "message", "Student registered successfully!",
                "student", student
        )).build();
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    @GET
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getStudentById(@PathParam("id") Long id) {
        Student student = studentService.findById(id);
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("message", "Student not found with ID: " + id)).build();
        }
        return Response.ok(student).build();
    }

    @GET
    @Path("/search")
    public Response searchByEmail(@QueryParam("email") String email) {
        Student student = studentService.findByEmail(email);
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("message", "No student found with email: " + email)).build();
        }
        return Response.ok(student).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateStudent(@PathParam("id") int id, Student updatedStudent) {
        Student existingStudent = studentService.findById((long) id);
        if (existingStudent == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("message", "Student not found with ID: " + id)).build();
        }

        if (updatedStudent.getEmail() == null || !updatedStudent.getEmail().endsWith("@gmail.com")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", "Email must end with @gmail.com.")).build();
        }

        if (updatedStudent.getPhone() == null || !updatedStudent.getPhone().matches("\\d{10}")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", "Phone number must be exactly 10 digits.")).build();
        }

        Student studentWithSameEmail = studentService.findByEmail(updatedStudent.getEmail());
        if (studentWithSameEmail != null && studentWithSameEmail.getStudentId() != id) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of("message", "A student with this email already exists.")).build();
        }

        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setPhone(updatedStudent.getPhone());
        existingStudent.setCollegeName(updatedStudent.getCollegeName());

        return Response.ok(Map.of(
                "message", "Student updated successfully!",
                "student", existingStudent
        )).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteStudent(@PathParam("id") Long id) {
        boolean deleted = studentService.delete(id);
        if (deleted) {
            return Response.ok(Map.of("message", "Student deleted successfully.")).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("message", "Student not found with ID: " + id)).build();
        }
    }
}
