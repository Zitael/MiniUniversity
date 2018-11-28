package ru.alexandrov.miniuniversity.view.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexandrov.miniuniversity.dao.dto.GroupDTO;
import ru.alexandrov.miniuniversity.dao.dto.StudentDTO;
import ru.alexandrov.miniuniversity.dao.dto.TeacherDTO;
import ru.alexandrov.miniuniversity.dao.entity.Group;
import ru.alexandrov.miniuniversity.dao.entity.Student;
import ru.alexandrov.miniuniversity.dao.entity.Teacher;
import ru.alexandrov.miniuniversity.view.json.request.*;
import ru.alexandrov.miniuniversity.dao.repository.GroupRepository;
import ru.alexandrov.miniuniversity.dao.repository.StudentRepository;
import ru.alexandrov.miniuniversity.dao.repository.TeacherRepository;
import ru.alexandrov.miniuniversity.view.json.response.ListResponse;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.ResponseEntity.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @PostMapping("/formGroup")
    public @ResponseBody
    ResponseEntity formGroup(@RequestBody FormGroupRequest request) {
        Group group = groupRepository.findByNameIsLike(request.getGroup());
        if (group != null) {
            log.error("Group already exists with name {}", request.getGroup());
            return badRequest().build();
        }
        groupRepository.save(new Group().setName(request.getGroup()));
        group = groupRepository.findByNameIsLike(request.getGroup());
        if (group == null) {
            log.error("Cant create new Group with name {}", request.getGroup());
            return status(HttpStatus.NOT_MODIFIED).build();
        }
        for (Student s : request.getStudents()) {
            studentRepository.save(new Student().setGroups(group).setAge(s.getAge()).setName(s.getName()));
            Student student = studentRepository.findByNameIsLikeAndAgeIs(s.getName(), s.getAge());
            if (student == null) {
                log.error("An error occured, cant add new Student with name {}", s.getName());
                return status(HttpStatus.NOT_MODIFIED).build();
            }
        }
        return ok().build();
    }

    @PostMapping("/addTeacherToGroup")
    public @ResponseBody
    ResponseEntity addTeacherToGroup(@RequestBody AddTeacherToGroupRequest request) {
        Group g = groupRepository.findByNameIsLike(request.getGroup());
        if (g == null) {
            log.error("Group has not found with name {}", request.getGroup());
            return notFound().build();
        }
        Teacher t = new Teacher().setName(request.getTeacher());
        g.setTeachers(null);
        t.getGroups().add(g);
        teacherRepository.save(t);
        return ok().build();
    }

    @PostMapping("/getGroupsByTeacher")
    public @ResponseBody
    ResponseEntity getGroupsByTeacher(@RequestBody TeacherNameRequest request) {
        Teacher t = teacherRepository.findFirstByNameIsLike(request.getTeacher());
        if (t == null) {
            log.error("Teacher has not found with name {}", request.getTeacher());
            return notFound().build();
        }
        TeacherDTO teacherDTO = new TeacherDTO().setId(t.getId()).setName(t.getName());
        for (Group g : t.getGroups()) {
            GroupDTO groupDTO = new GroupDTO()
                    .setId(g.getId())
                    .setName(g.getName());
            teacherDTO.getGroups().add(groupDTO);
        }
        Set<GroupDTO> result = teacherDTO.getGroups();
        if (result.size() > 0) {
            return ok(new ListResponse().setList(result));
        }
        log.error("Groups has not found with teacher name {}", request.getTeacher());
        return notFound().build();
    }

    @PostMapping("/getStudentsByGroup")
    public @ResponseBody
    ResponseEntity getStudentsByGroup(@RequestBody GroupNameRequest request) {
        Group g = groupRepository.findByNameIsLike(request.getGroup());
        if (g == null) {
            log.error("Group has not found with name {}", request.getGroup());
            return notFound().build();
        }
        GroupDTO groupDTO = new GroupDTO()
                .setId(g.getId())
                .setName(g.getName());
        for (Student s : g.getStudents()) {
            StudentDTO studentDTO = new StudentDTO()
                    .setAge(s.getAge())
                    .setId(s.getId())
                    .setName(s.getName())
                    .setGroupName(g.getName());
            groupDTO.getStudents().add(studentDTO);
        }
        Set<StudentDTO> result = groupDTO.getStudents();
        if (result.size() > 0) {
            return ok(new ListResponse().setList(result));
        }
        log.error("Students has not found with group name {}", request.getGroup());
        return notFound().build();
    }

    @PostMapping("/getStudentsByTeacher")
    public @ResponseBody
    ResponseEntity getStudentsByTeacher(@RequestBody TeacherNameRequest request) {
        Teacher t = teacherRepository.findFirstByNameIsLike(request.getTeacher());
        if (t == null) {
            log.error("Teacher has not found with name {}", request.getTeacher());
            return notFound().build();
        }
        Set<StudentDTO> result = new HashSet<>();
        for (Group g : t.getGroups()) {
            for (Student s : g.getStudents()) {
                StudentDTO studentDTO = new StudentDTO()
                        .setAge(s.getAge())
                        .setId(s.getId())
                        .setName(s.getName())
                        .setGroupName(g.getName());
                result.add(studentDTO);
            }
        }
        if (result.size() > 0) {
            return ok(new ListResponse().setList(result));
        }
        log.error("Students has not found with teacher name {}", request.getTeacher());
        return notFound().build();
    }
}
