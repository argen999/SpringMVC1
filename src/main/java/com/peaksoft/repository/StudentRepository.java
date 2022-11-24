package com.peaksoft.repository;

import com.peaksoft.entity.Student;

import java.util.List;

public interface StudentRepository {
    List<Student> getAllStudent();
    List<Student> getAllStudent(Long groupId);
    Student getStudentById(Long id);
    void saveStudent(Long groupId, Student student);
    void updateStudent(Long id, Student student);
    void deleteStudent(Long id);
}
