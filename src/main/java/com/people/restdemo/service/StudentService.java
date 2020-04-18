package com.people.restdemo.service;

import com.people.restdemo.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service to expose Operations on Courses
 */
public interface StudentService {
    Page<Student> findAllStudentByPage(Pageable pageable);
    Page<Student> findAllStudents();
    Student findStudentById(long id);
    Student createStudent(Student student);
    Student updateStudent(Student student);
    void deleteStudentById(long id);


}
