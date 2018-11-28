package ru.alexandrov.miniuniversity.view.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PostControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void formGroup_WhenOk() throws Exception {
        mockMvc.perform(
                post("/formGroup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \"group\" : \"C\" ,\n" +
                                "  \"students\" : [\n" +
                                "    {\"age\": 30,\n" +
                                "    \"name\" : \"TestStudent1\"},\n" +
                                "    {\"age\": 40,\n" +
                                "    \"name\" : \"TestStudent2\"}\n" +
                                "    ]\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void formGroup_WhenGroupExists() throws Exception {
        mockMvc.perform(
                post("/formGroup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \n" +
                                "   \"group\" : \"B\" ,\n" +
                                "   \"students\" : [\n" +
                                "    {\"age\": 30,\n" +
                                "    \"name\" : \"TestStudent1\"},\n" +
                                "    {\"age\": 40,\n" +
                                "    \"name\" : \"TestStudent2\"}\n" +
                                "    ]\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addTeacherToGroup_WhenOk() throws Exception {
        mockMvc.perform(
                post("/addTeacherToGroup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \n" +
                                "  \"teacher\" : \"TestTeacher\" ,\n" +
                                "  \"group\" : \"A\"\n" +
                                "}"
                        ))
                .andExpect(status().isOk());
    }

    @Test
    public void addTeacherToGroup_WhenGroupNotFound() throws Exception {
        mockMvc.perform(
                post("/addTeacherToGroup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \n" +
                                "  \"teacher\" : \"TestTeacher\" ,\n" +
                                "  \"group\" : \"Z\"\n" +
                                "}"
                        ))
                .andExpect(status().isNotFound());
    }


    @Test
    public void getGroupsByTeacher_WhenOk() throws Exception {
        mockMvc.perform(
                post("/getGroupsByTeacher")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \n" +
                                "  \"teacher\" : \"Teacher1\" \n" +
                                "}"
                        ))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "        \"id\": 1,\n" +
                        "        \"name\": \"A\"\n" +
                        "    }\n" +
                        "]"));
    }

    @Test
    public void getGroupsByTeacher_WhenTeacherNotFound() throws Exception {
        mockMvc.perform(
                post("/getGroupsByTeacher")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \n" +
                                "  \"teacher\" : \"Teacher50\" \n" +
                                "}"
                        ))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getStudentsByGroup_WhenOk() throws Exception {
        mockMvc.perform(
                post("/getStudentsByGroup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("[\n" +
                                "    {\n" +
                                "        \"id\": 2,\n" +
                                "        \"name\": \"Student2\",\n" +
                                "        \"age\": 19,\n" +
                                "        \"groupName\": \"A\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "        \"id\": 1,\n" +
                                "        \"name\": \"Student1\",\n" +
                                "        \"age\": 18,\n" +
                                "        \"groupName\": \"A\"\n" +
                                "    }\n" +
                                "]"));
    }

    @Test
    public void getStudentsByGroup_WhenGroupNotFound() throws Exception {
        mockMvc.perform(
                post("/getStudentsByGroup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \n" +
                                "  \"group\" : \"Z\" \n" +
                                "}"
                        ))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getStudentsByTeacher_WhenOk() throws Exception {
        mockMvc.perform(
                post("/getStudentsByTeacher")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \n" +
                                "  \"teacher\" : \"Teacher1\" \n" +
                                "}"
                        ))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "        \"id\": 2,\n" +
                        "        \"name\": \"Student2\",\n" +
                        "        \"age\": 19,\n" +
                        "        \"groupName\": \"A\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": 1,\n" +
                        "        \"name\": \"Student1\",\n" +
                        "        \"age\": 18,\n" +
                        "        \"groupName\": \"A\"\n" +
                        "    }\n" +
                        "]"));
    }

    @Test
    public void getStudentsByTeacher_WhenTeacherNotFound() throws Exception {
        mockMvc.perform(
                post("/getStudentsByTeacher")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \n" +
                                "  \"teacher\" : \"Teacher50\" \n" +
                                "}"
                        ))
                .andExpect(status().isNotFound());
    }
}