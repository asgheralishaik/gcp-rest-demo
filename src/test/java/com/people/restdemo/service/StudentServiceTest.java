package com.people.restdemo.service;

import com.people.restdemo.domain.Student;
import com.people.restdemo.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@TestPropertySource("classpath:test-application.properties")
public class StudentServiceTest {

    @Autowired
    private StudentRepository studentRepository;

    private StudentService studentService;

    @BeforeEach
    public void init() {
        studentService = new StudentServiceImpl(studentRepository);
    }

    @Test
    public void shouldGetAllStudentsInSinglePageWhenUnpaged() {
        Page<Student> singlePage = studentService.findAllStudents();
        assertEquals(12, singlePage.getNumberOfElements());
        assertEquals(1, singlePage.getTotalPages());

    }

    @Test
    public void shouldGetPageWiseStudentsWhenPaged() {
        Pageable pageable = PageRequest.of(0, 5);

        Page<Student> firstPage = studentService.findAllStudentByPage(pageable);
        assertEquals(5, firstPage.getNumberOfElements());
        assertEquals(3, firstPage.getTotalPages());

    }

    @Test
    public void shouldGetStudentById() {
        Student student = studentService.findStudentById(3);
        assertNotNull(student);
        assertEquals("student3", student.getStudentFirstName());
        assertEquals("C003", student.getStudentCourseCode());

    }


    @Test
    public void shouldUpdateExistingStudent() {
        Student student = studentService.updateStudent(Student.builder().studentFirstName("Course14Updated").studentCourseCode("C003").studentLastName("lastName")
                .studentRUT("23.222.333-8").id(9L).age(19).build());
        assertNotNull(student);
        assertEquals("Course14Updated", student.getStudentFirstName());
        assertEquals("C003", student.getStudentCourseCode());

    }

}
