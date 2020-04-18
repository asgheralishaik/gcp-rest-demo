package com.people.restdemo.service;

import com.people.restdemo.domain.Course;
import com.people.restdemo.exception.CourseNotFoundException;
import com.people.restdemo.repository.CourseRepository;
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
public class CourseServiceTest {

    @Autowired
    private CourseRepository courseRepository;

    private CourseService courseService;

    @BeforeEach
    public void init() {
        courseService = new CourseServiceImpl(courseRepository);
    }

    @Test
    public void shouldGetAllCoursesInSinglePageWhenUnpaged() {
        Page<Course> singlePage = courseService.findAllCourses();
        assertEquals(12, singlePage.getNumberOfElements());
        assertEquals(1, singlePage.getTotalPages());

    }

    @Test
    public void shouldGetPageWiseCoursesWhenPaged() {
        Pageable pageable = PageRequest.of(0, 5);

        Page<Course> firstPage = courseService.findAllCoursesByPage(pageable);
        assertEquals(5, firstPage.getNumberOfElements());
        assertEquals(3, firstPage.getTotalPages());

    }

    @Test
    public void shouldGetCourseById() {
        Course course = courseService.findCourseByCourseCode("C006");
        assertNotNull(course);
        assertEquals("Course6", course.getCourseName());
        assertEquals("C006", course.getCourseCode());

    }



    @Test
    public void shouldUpdateExistingCourse() {
        Course course = courseService.updateCourse(Course.builder().courseName("Course03Updated").courseCode("C003").build());
        assertNotNull(course);
        assertEquals("Course03Updated", course.getCourseName());
        assertEquals("C003", course.getCourseCode());

    }

    @Test
    public void shouldDeleteCourseById() {
        assertThrows(CourseNotFoundException.class,()->courseService.deleteCourseByCourseCode("C100"));

    }

    @Test
    public void shouldCreateCourse() {
        Course course = courseService.createCourse(Course.builder().courseName("Course14").courseCode("C014").build());
        assertNotNull(course);
        assertEquals("Course14", course.getCourseName());
        assertEquals("C014", course.getCourseCode());

    }


}
