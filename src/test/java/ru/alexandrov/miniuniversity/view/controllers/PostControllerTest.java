package ru.alexandrov.miniuniversity.view.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.alexandrov.miniuniversity.dao.entity.Student;
import ru.alexandrov.miniuniversity.view.json.request.AddTeacherToGroupRequest;
import ru.alexandrov.miniuniversity.view.json.request.FormGroupRequest;
import ru.alexandrov.miniuniversity.view.json.request.GroupNameRequest;
import ru.alexandrov.miniuniversity.view.json.request.TeacherNameRequest;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PostControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper om = new ObjectMapper();
    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void formGroup_WhenOk() throws Exception {
        FormGroupRequest fgr = new FormGroupRequest("C",
                Arrays.asList(new Student().setAge(30).setName("TestStudent1"),
                        new Student().setAge(40).setName("TestStudent2")));
        mockMvc.perform(
                post("/formGroup")
                        .content(om.writeValueAsString(fgr))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void formGroup_WhenGroupExists() throws Exception {
        FormGroupRequest fgr = new FormGroupRequest("B",
                Arrays.asList(new Student().setAge(30).setName("TestStudent1"),
                        new Student().setAge(40).setName("TestStudent2")));
        mockMvc.perform(
                post("/formGroup")
                        .content(om.writeValueAsString(fgr))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addTeacherToGroup_WhenOk() throws Exception {
        AddTeacherToGroupRequest atgr = new AddTeacherToGroupRequest("TestTeacher", "A");
        mockMvc.perform(
                post("/addTeacherToGroup")
                        .content(om.writeValueAsString(atgr))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void addTeacherToGroup_WhenGroupNotFound() throws Exception {
        AddTeacherToGroupRequest atgr = new AddTeacherToGroupRequest("TestTeacher", "Z");
        mockMvc.perform(
                post("/addTeacherToGroup")
                        .content(om.writeValueAsString(atgr))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }


    @Test
    public void getGroupsByTeacher_WhenOk() throws Exception {
        TeacherNameRequest tnr = new TeacherNameRequest().setTeacher("Teacher1");
        mockMvc.perform(
                post("/getGroupsByTeacher")
                        .content(om.writeValueAsString(tnr))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void getGroupsByTeacher_WhenTeacherNotFound() throws Exception {
        TeacherNameRequest tnr = new TeacherNameRequest().setTeacher("Teacher50");
        mockMvc.perform(
                post("/getGroupsByTeacher")
                        .content(om.writeValueAsString(tnr))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getStudentsByGroup_WhenOk() throws Exception {
        GroupNameRequest gnr = new GroupNameRequest().setGroup("A");
        mockMvc.perform(
                post("/getStudentsByGroup")
                        .content(om.writeValueAsString(gnr))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentsByGroup_WhenGroupNotFound() throws Exception {
        GroupNameRequest gnr = new GroupNameRequest().setGroup("Z");
        mockMvc.perform(
                post("/getStudentsByGroup")
                        .content(om.writeValueAsString(gnr))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getStudentsByTeacher_WhenOk() throws Exception {
        TeacherNameRequest tnr = new TeacherNameRequest().setTeacher("Teacher1");
        mockMvc.perform(
                post("/getStudentsByTeacher")
                        .content(om.writeValueAsString(tnr))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentsByTeacher_WhenTeacherNotFound() throws Exception {
        TeacherNameRequest tnr = new TeacherNameRequest().setTeacher("Teacher50");
        mockMvc.perform(
                post("/getStudentsByTeacher")
                        .content(om.writeValueAsString(tnr))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }
}