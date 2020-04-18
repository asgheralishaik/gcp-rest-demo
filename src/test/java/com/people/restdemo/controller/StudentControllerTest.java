package com.people.restdemo.controller;

import com.people.restdemo.domain.Student;
import com.people.restdemo.exception.StudentNotFoundException;
import com.people.restdemo.service.StudentService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test-application.properties")
public class StudentControllerTest {


    @Autowired
    private MockMvc mockmvc;


    @MockBean
    private StudentService studentService;

    private String token = "";

    @BeforeEach
    public void setUp() throws Exception {
        MvcResult result = mockmvc.perform(MockMvcRequestBuilders.get("/token")).andExpect(status().isOk()).andReturn();
        token = result.getResponse().getContentAsString();
    }

    @Test
    public void shouldReturnHttpOkWhenAllStudentsRetrieved() throws Exception {

        List<Student> Students = prepareStudents();
        Page<Student> page = new PageImpl(Students, Pageable.unpaged(), 1);
        when(studentService.findAllStudents()).thenReturn(page);
        mockmvc.perform(MockMvcRequestBuilders.get("/students").header("authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnHttpNotFoundWhenStudentNotFound() throws Exception {

        when(studentService.findStudentById(anyLong())).thenThrow(new StudentNotFoundException("student not found"));
        String message = mockmvc.perform(MockMvcRequestBuilders.get("/students/1").header("authorization", "Bearer " + token))
                .andExpect(status().isNotFound()).andReturn().getResolvedException().getMessage();
        assertEquals("student not found", message);

    }

    @Test
    public void shouldReturnHttCreatedWhenStudentIsCreated() throws Exception {
        String json = "{\n" +
                "            \"rut\": \"24.444.888-5\",\n" +
                "            \"name\": \"student1\",\n" +
                "            \"lastName\": \"student1LastName\",\n" +
                "            \"age\": 19,\n" +
                "            \"code\": \"C001\"\n" +
                "        }";
        when(studentService.createStudent(any(Student.class))).thenReturn(prepareStudents().get(0));
        mockmvc.perform(MockMvcRequestBuilders.post("/students").content(json)
                .contentType("application/json").header("authorization", "Bearer " + token)).andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnHttStatusOKWhenStudentIsUpdated() throws Exception {
        String json = "{\n" +
                "            \n" +
                "            \"rut\": \"24.444.888-5\",\n" +
                "            \"name\": \"student122\",\n" +
                "            \"lastName\": \"student1Updated\",\n" +
                "            \"age\": 21,\n" +
                "            \"code\": \"C001\"\n" +
                "        }";
        when(studentService.updateStudent(any(Student.class))).thenReturn(prepareStudents().get(0));
        mockmvc.perform(MockMvcRequestBuilders.put("/students").content(json)
                .contentType("application/json").header("authorization", "Bearer " + token)).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnHttStatusBadRequestWhenStudentAgeIsLessThan18() throws Exception {
        String json = "{\n" +
                "            \n" +
                "            \"rut\": \"24.444.888-5\",\n" +
                "            \"name\": \"student122\",\n" +
                "            \"lastName\": \"student1Updated\",\n" +
                "            \"age\": 15,\n" +
                "            \"code\": \"C001\"\n" +
                "        }";
        when(studentService.updateStudent(any(Student.class))).thenReturn(prepareStudents().get(0));
        mockmvc.perform(MockMvcRequestBuilders.put("/students").content(json)
                .contentType("application/json").header("authorization", "Bearer " + token)).andExpect(status().isBadRequest());
    }


    @Test
    public void shouldReturnHttStatusOKWhenStudentIsDeleted() throws Exception {
        doNothing().when(studentService).deleteStudentById(anyLong());
        mockmvc.perform(MockMvcRequestBuilders.delete("/students/1")
                .contentType("application/json").header("authorization", "Bearer " + token)).andExpect(status().isOk());
    }


    private List<Student> prepareStudents() {
        Student student = Student.builder().studentCourseCode("C001").age(19).studentFirstName("firstName").studentLastName("lastName").rut("24.333.555-4").build();


        return Arrays.asList(student);
    }


}