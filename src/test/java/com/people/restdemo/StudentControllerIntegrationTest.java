package com.people.restdemo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.people.restdemo.domain.Student;
import com.people.restdemo.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test-application.properties")
public class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;



    @Autowired
    private StudentService studentService;

    private String token = "";


    @BeforeEach
    public void setUp() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/token")).andExpect(status().isOk()).andReturn();
        token = result.getResponse().getContentAsString();
    }


    @Test
    void shouldRetrieveAllStudentsByPage() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/students").header("authorization", "Bearer " + token))
                .andReturn().getResponse();
        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));

        JsonNode rootNode = new ObjectMapper().readTree(response.getContentAsString());

        Gson gson = new Gson();
        assertThat("current page only fetches 5 items(my default page size is 5)",gson.fromJson(rootNode.get("content").toString(), List.class).size(),is(rootNode.get("size").asInt()));
        assertThat("total pages are 3",rootNode.get("totalPages").asInt(), is(3));

    }

    @Test
    void shouldRetrieveAllStudentsInSinglePage() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/students/all").header("authorization", "Bearer " + token))
                .andReturn().getResponse();
        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));

        JsonNode rootNode = new ObjectMapper().readTree(response.getContentAsString());

        Gson gson = new Gson();
        assertThat("all courses are fetched",gson.fromJson(rootNode.get("content").toString(), List.class).size(),is(Integer.parseInt(rootNode.get("size").toString())));
        assertThat("total pages are 1",rootNode.get("totalPages").asInt(), is(1));

    }

    @Test
    void shouldRetrieveStudentById() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/students/3").header("authorization", "Bearer " + token))
                .andReturn().getResponse();
        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));

        Gson gson = new Gson();
        Student student = gson.fromJson(response.getContentAsString(),Student.class);
        assertThat(student.getRut(),is("24.444.888-7"));
        assertThat(student.getStudentFirstName(),is("student3"));
        assertThat(student.getStudentLastName(),is("student3LastName"));
        assertThat(student.getAge(),is(21));
        assertThat(student.getStudentCourseCode(),is("C003"));
    }

    @Test
    void shouldCreateStudentWhenPOSTJsonRequestIsPassed() throws Exception {
        String json = "{\n" +
                "            \"rut\": \"24.444.888-5\",\n" +
                "            \"name\": \"student1\",\n" +
                "            \"lastName\": \"student1LastName\",\n" +
                "            \"age\": 19,\n" +
                "            \"code\": \"C001\"\n" +
                "        }";
        MockHttpServletResponse response  = mockMvc.perform(MockMvcRequestBuilders.post("/students").content(json)
                .contentType("application/json").header("authorization", "Bearer " + token)).andReturn().getResponse();
        assertThat(response.getStatus(), equalTo(HttpStatus.CREATED.value()));
        Gson gson = new Gson();
        Student student = gson.fromJson(response.getContentAsString(),Student.class);
        assertThat(student.getStudentFirstName(),is("student1"));
        assertThat(student.getStudentLastName(),is("student1LastName"));
    }

    @Test
    void shouldUpdateStudentWhenPUTJsonRequestIsPassed() throws Exception {
        String json = "{\n" +
                "\"id\": 2,\n" +
                "            \"rut\": \"24.444.888-5\",\n" +
                "            \"name\": \"student1\",\n" +
                "            \"lastName\": \"student1LastNameUpdated\",\n" +
                "            \"age\": 19,\n" +
                "            \"code\": \"C001\"\n" +
                "        }";
        MockHttpServletResponse response  = mockMvc.perform(MockMvcRequestBuilders.put("/students").content(json)
                .contentType("application/json").header("authorization", "Bearer " + token)).andReturn().getResponse();
        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
        Gson gson = new Gson();
        Student student = gson.fromJson(response.getContentAsString(),Student.class);
        assertThat(student.getStudentLastName(),is("student1LastNameUpdated"));
    }

    @Test
    public void shouldGiveHttpStatusNotFoundWhenUnknownStudentIsPassed() throws Exception {
        String message  = mockMvc.perform(MockMvcRequestBuilders.get("/students/100")
                .contentType("application/json").header("authorization", "Bearer " + token))
                .andExpect(status().isNotFound()).andReturn().getResolvedException().getMessage();
        assertThat(message,is("Student with id: 100 not found"));

    }

    @Test
    public void shouldGiveHttpStatusBadDataWhenCourseInvalidCourseIsPassed() throws Exception {
        String json = "{\n" +
                "            \"rut\": \"24.444.888-5\",\n" +
                "            \"name\": \"student1\",\n" +
                "            \"lastName\": \"student1LastNameUpdated\",\n" +
                "            \"age\": 19,\n" +
                "            \"code\": \"C200\"\n" +
                "        }";
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/students").content(json)
                .contentType("application/json").header("authorization", "Bearer " + token)).andExpect(status().isBadRequest()).andReturn().getResponse();
            assertTrue(response.getContentAsString().contains("Check if course being inserted is valid course"));

    }

    @Test
    public void shouldGiveHttpStatusBadDataWhenCourseRutIsInvalid() throws Exception {
        String json = "{\n" +
                "            \"rut\": \"24\",\n" +
                "            \"name\": \"student1\",\n" +
                "            \"lastName\": \"student1LastNameUpdated\",\n" +
                "            \"age\": 19,\n" +
                "            \"code\": \"C200\"\n" +
                "        }";
        String message = mockMvc.perform(MockMvcRequestBuilders.post("/students").content(json)
                .contentType("application/json").header("authorization", "Bearer " + token)).andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
        assertTrue(message.contains("RUT should be in format XX.XXX.XXX-X"));

    }



}
