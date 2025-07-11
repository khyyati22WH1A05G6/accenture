package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.model.Student;

import java.util.List;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student> {

    public Student findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public void save(Student student) {
        persist(student);
    }

    public List<Student> getAll() {
        return listAll();
    }

    public Student getById(Long id) {
        return findById(id);
    }

    public boolean deleteByIdCustom(Long id) {
        return deleteById(id);
    }
}
