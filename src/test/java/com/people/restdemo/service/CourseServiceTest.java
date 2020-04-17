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
        Course course = courseService.findCourseById(6L);
        assertNotNull(course);
        assertEquals(6L, course.getId());
        assertEquals("Course6", course.getName());
        assertEquals("C006", course.getCode());

    }



    @Test
    public void shouldUpdateExistingCourse() {
        Course course = courseService.updateCourse(Course.builder().name("Course14Updated").code("C014").id(10L).build());
        assertNotNull(course);
        assertEquals(10L, course.getId());
        assertEquals("Course14Updated", course.getName());
        assertEquals("C014", course.getCode());

    }

    @Test
    public void shouldDeleteCourseById() {
        courseService.deleteCourseById(1);
        assertThrows(CourseNotFoundException.class,()->courseService.findCourseById(1));

    }

    @Test
    public void shouldCreateCourse() {
        Course course = courseService.createCourse(Course.builder().name("Course14").code("C014").build());
        assertNotNull(course);
        assertEquals(13L, course.getId());
        assertEquals("Course14", course.getName());
        assertEquals("C014", course.getCode());

    }


}
