package com.people.restdemo.controller;

import com.people.restdemo.domain.Course;
import com.people.restdemo.exception.CourseNotFoundException;
import com.people.restdemo.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test-application.properties")
public class CourseControllerTest {

    @Autowired
    private MockMvc mockmvc;


    @MockBean
    private CourseService courseService;

    private String token = "";

    @BeforeEach
    public void setUp() throws Exception {
        MvcResult result = mockmvc.perform(MockMvcRequestBuilders.get("/token")).andExpect(status().isOk()).andReturn();
        token = result.getResponse().getContentAsString();
    }

    @Test
    public void shouldReturnHttpOkWhenAllCoursesRetrieved() throws Exception {

        List<Course> courses = prepareCourses();
        Page<Course> page = new PageImpl(courses, Pageable.unpaged(), 1);
        when(courseService.findAllCourses()).thenReturn(page);
        mockmvc.perform(MockMvcRequestBuilders.get("/courses").header("authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnHttpNotFoundWhenCourseNotFound() throws Exception {

        when(courseService.findCourseByCourseCode(anyString())).thenThrow(new CourseNotFoundException("course not found"));
        String message = mockmvc.perform(MockMvcRequestBuilders.get("/courses/C101").header("authorization", "Bearer " + token))
                .andExpect(status().isNotFound()).andReturn().getResolvedException().getMessage();
        assertEquals("course not found", message);

    }

    @Test
    public void shouldReturnHttCreatedWhenCourseIsCreated() throws Exception {
        String json = "{\"id\":3,\"code\":\"C876\",\"name\":\"sometest\"}";
        when(courseService.createCourse(any(Course.class))).thenReturn(prepareCourses().get(0));
        mockmvc.perform(MockMvcRequestBuilders.post("/courses").content(json)
                .contentType("application/json").header("authorization", "Bearer " + token)).andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnHttStatusOKWhenCourseIsUpdated() throws Exception {
        String json = "{\"code\":\"C876\",\"name\":\"updatedValue\"}";
        when(courseService.updateCourse(any(Course.class))).thenReturn(prepareCourses().get(0));
        mockmvc.perform(MockMvcRequestBuilders.put("/courses").content(json)
                .contentType("application/json").header("authorization", "Bearer " + token)).andExpect(status().isOk());
    }


    @Test
    public void shouldReturnHttStatusOKWhenCourseIsDeleted() throws Exception {
        doNothing().when(courseService).deleteCourseByCourseCode(anyString());
        mockmvc.perform(MockMvcRequestBuilders.delete("/courses/C101")
                .contentType("application/json").header("authorization", "Bearer " + token)).andExpect(status().isOk());
    }


    private List<Course> prepareCourses() {
        Course course1 = Course.builder().courseCode("C001").courseName("course1").build();
        Course course2 = Course.builder().courseCode("C002").courseName("course2").build();
        Course course3 = Course.builder().courseCode("C003").courseName("course2").build();
        Course course4 = Course.builder().courseCode("C004").courseName("course2").build();
        Course course5 = Course.builder().courseCode("C005").courseName("course2").build();
        Course course6 = Course.builder().courseCode("C006").courseName("course2").build();
        Course course7 = Course.builder().courseCode("C007").courseName("course2").build();
        Course course8 = Course.builder().courseCode("C008").courseName("course2").build();
        Course course9 = Course.builder().courseCode("C009").courseName("course2").build();
        Course course10 = Course.builder().courseCode("C010").courseName("course2").build();
        Course course11 = Course.builder().courseCode("C011").courseName("course2").build();

        return Arrays.asList(course1, course2, course3, course4, course5, course6, course7, course8, course9, course10, course11);
    }


}
