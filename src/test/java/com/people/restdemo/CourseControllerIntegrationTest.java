package com.people.restdemo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.people.restdemo.domain.Course;
import com.people.restdemo.service.CourseService;
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
public class CourseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;



    @Autowired
    private CourseService courseService;

    private String token = "";


    @BeforeEach
    public void setUp() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/token")).andExpect(status().isOk()).andReturn();
        token = result.getResponse().getContentAsString();
    }


    @Test
    void shouldRetrieveAllCoursesByPage() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/courses").header("authorization", "Bearer " + token))
                .andReturn().getResponse();
        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));

        JsonNode rootNode = new ObjectMapper().readTree(response.getContentAsString());

        Gson gson = new Gson();
        assertThat("current page only fetches 5 items(my default page size is 5)",gson.fromJson(rootNode.get("content").toString(), List.class).size(),is(rootNode.get("size").asInt()));
        assertThat("total pages are 3",rootNode.get("totalPages").asInt(), is(3));

    }

    @Test
    void shouldRetrieveAllCoursesInSinglePage() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/courses/all").header("authorization", "Bearer " + token))
                .andReturn().getResponse();
        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));

        JsonNode rootNode = new ObjectMapper().readTree(response.getContentAsString());

        Gson gson = new Gson();
        assertThat("all courses are fetched",gson.fromJson(rootNode.get("content").toString(), List.class).size(),is(Integer.parseInt(rootNode.get("size").toString())));
        assertThat("total pages are 1",rootNode.get("totalPages").asInt(), is(1));

    }

    @Test
    void shouldRetrieveCourseByCourseCode() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/courses/C003").header("authorization", "Bearer " + token))
                .andReturn().getResponse();
        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));

        Gson gson = new Gson();
        Course course = gson.fromJson(response.getContentAsString(),Course.class);
        assertThat(course.getCourseCode(),is("C003"));
        assertThat(course.getCourseName(),is("Course3"));
    }

    @Test
    void shouldCreateCourseWhenPOSTJsonRequestIsPassed() throws Exception {
         String json = "{\"id\":13,\"code\":\"C013\",\"name\":\"Course13\"}";
        MockHttpServletResponse response  = mockMvc.perform(MockMvcRequestBuilders.post("/courses").content(json)
                .contentType("application/json").header("authorization", "Bearer " + token)).andReturn().getResponse();
        assertThat(response.getStatus(), equalTo(HttpStatus.CREATED.value()));
        Gson gson = new Gson();
        Course course = gson.fromJson(response.getContentAsString(),Course.class);
        assertThat(course.getCourseCode(),is("C013"));
        assertThat(course.getCourseName(),is("Course13"));
    }

    @Test
    void shouldUpdateCourseWhenPUTJsonRequestIsPassed() throws Exception {
        String json = "{\"code\":\"C005\",\"name\":\"Course05Updated\"}";
        MockHttpServletResponse response  = mockMvc.perform(MockMvcRequestBuilders.put("/courses").content(json)
                .contentType("application/json").header("authorization", "Bearer " + token)).andReturn().getResponse();
        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
        Gson gson = new Gson();
        Course course = gson.fromJson(response.getContentAsString(),Course.class);
        assertThat(course.getCourseCode(),is("C005"));
        assertThat(course.getCourseName(),is("Course05Updated"));
    }

    @Test
    public void shouldGiveHttpStatusNotFoundWhenUnknownCourseIsPassed() throws Exception {
        String message  = mockMvc.perform(MockMvcRequestBuilders.get("/courses/100")
                .contentType("application/json").header("authorization", "Bearer " + token))
                .andExpect(status().isNotFound()).andReturn().getResolvedException().getMessage();
        assertThat(message,is("Course with id: 100 not found"));

    }

    @Test
    public void shouldGiveHttpStatusBadDataWhenCourseCodeMoreThan4Characters() throws Exception {
        String json = "{\"id\":13,\"code\":\"C34567888\",\"name\":\"Course13\"}";
        String message = mockMvc.perform(MockMvcRequestBuilders.post("/courses").content(json)
                .contentType("application/json").header("authorization", "Bearer " + token)).andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
            assertTrue(message.contains("The course code should be  4 characters"));

    }

    @Test
    public void shouldGiveHttpCourseNotFoundWhenINvalidCoursePassedForDelete() throws Exception {
        String message = mockMvc.perform(MockMvcRequestBuilders.delete("/courses/44")
                .contentType("application/json").header("authorization", "Bearer " + token)).andExpect(status().isNotFound()).andReturn().getResolvedException().getMessage();
        assertTrue(message.contains("Course with id: 44 not found"));

    }



}
