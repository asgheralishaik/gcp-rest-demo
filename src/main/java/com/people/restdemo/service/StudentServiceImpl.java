package com.people.restdemo.service;

import com.people.restdemo.domain.Student;
import com.people.restdemo.exception.StudentNotFoundException;
import com.people.restdemo.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation Class to implement All Course related operations
 */
@Service
public class StudentServiceImpl implements StudentService {

    private static final String STUDENT_NOT_FOUND_EXCEPTION = "Student with id: %s not found";
    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public Page<Student> findAllStudentByPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Page<Student> findAllStudents() {
        return studentRepository.findAll(Pageable.unpaged());
    }


    @Override
    public Student findStudentById(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new StudentNotFoundException(String.format(STUDENT_NOT_FOUND_EXCEPTION, id));
        }
    }

    public Student createStudent(Student studentToBeCreated) {
        Student newStudent = Student.builder().rut(studentToBeCreated.getRut()).studentFirstName(studentToBeCreated.getStudentFirstName()).studentLastName(studentToBeCreated.getStudentLastName())
                .age(studentToBeCreated.getAge()).studentCourseCode(studentToBeCreated.getStudentCourseCode()).build();
        studentRepository.save(newStudent);
        return newStudent;

    }

    public Student updateStudent(Student studentToBeCreated) {
       Optional<Student> student = studentRepository.findById(studentToBeCreated.getId());

        if (student.isPresent()) {
            Student newStudent = Student.builder().rut(studentToBeCreated.getRut()).studentFirstName(studentToBeCreated.getStudentFirstName()).studentLastName(studentToBeCreated.getStudentLastName())
                    .age(studentToBeCreated.getAge()).studentCourseCode(studentToBeCreated.getStudentCourseCode()).build();
            studentRepository.save(newStudent);
            return newStudent;
        } else {
            throw new StudentNotFoundException(String.format("Course with id : %s does not exists", studentToBeCreated.getId()));
        }

    }

    public void deleteStudentById(long id) {
        Optional<Student> studentToBeDeleted = studentRepository.findById(id);

        if (studentToBeDeleted.isPresent()) {
            studentRepository.deleteById(id);
        } else {
            throw new StudentNotFoundException(String.format(STUDENT_NOT_FOUND_EXCEPTION, id));
        }
    }

}
