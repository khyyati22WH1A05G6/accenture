package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.model.Student;
import org.example.repository.StudentRepository;

import java.util.List;

@ApplicationScoped
public class StudentService {

    @Inject
    StudentRepository studentRepository;

    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public void save(Student student) {
        studentRepository.save(student);
    }

    public List<Student> getAll() {
        return studentRepository.getAll();
    }

    public Student findById(Long id) {
        return studentRepository.getById(id);
    }

    public boolean delete(Long id) {
        return studentRepository.deleteByIdCustom(id);
    }
}
