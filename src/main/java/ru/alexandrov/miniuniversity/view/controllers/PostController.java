package ru.alexandrov.miniuniversity.view.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        Group group = groupRepository.findByNameIsLike(request.getGroupName());
        if (group != null) {
            log.error("Group already exists with name {}", request.getGroupName());
            return badRequest().build();
        }
        group = groupRepository.save(new Group(request.getGroupName()));
        for (Student s : request.getStudents()) {
            studentRepository.save(new Student(s.getName(), s.getAge(), group));
        }
        return ok().build();
    }

    @PostMapping("/addTeacherToGroup")
    public @ResponseBody
    ResponseEntity addTeacherToGroup(@RequestBody AddTeacherToGroupRequest request) {
        Group group = groupRepository.findByNameIsLike(request.getGroupName());
        if (group == null) {
            log.error("Group has not found with name {}", request.getGroupName());
            return notFound().build();
        }
        Teacher t = new Teacher(request.getTeacherName());
        t.getGroups().add(group);
        teacherRepository.save(t);
        return ok().build();
    }

    @PostMapping("/getGroupsByTeacher")
    public @ResponseBody
    ResponseEntity<Set<GroupDTO>> getGroupsByTeacher(@RequestBody TeacherNameRequest request) {
        Teacher t = teacherRepository.findFirstByNameIsLike(request.getTeacherName());
        if (t == null) {
            log.error("Teacher has not found with name {}", request.getTeacherName());
            return notFound().build();
        }
        TeacherDTO teacherDTO = new TeacherDTO(t.getId(), t.getName());
        for (Group g : t.getGroups()) {
            GroupDTO groupDTO = new GroupDTO(g.getId(), g.getName());
            teacherDTO.getGroups().add(groupDTO);
        }
        Set<GroupDTO> result = teacherDTO.getGroups();
        if (result.size() > 0) {
            return ok(result);
        }
        log.error("Groups has not found with teacher name {}", request.getTeacherName());
        return notFound().build();
    }

    @PostMapping("/getStudentsByGroup")
    public @ResponseBody
    ResponseEntity<Set<StudentDTO>> getStudentsByGroup(@RequestBody GroupNameRequest request) {
        Group g = groupRepository.findByNameIsLike(request.getGroup());
        if (g == null) {
            log.error("Group has not found with name {}", request.getGroup());
            return notFound().build();
        }
        GroupDTO groupDTO = new GroupDTO(g.getId(), g.getName());
        for (Student s : g.getStudents()) {
            StudentDTO studentDTO = new StudentDTO(s.getId(), s.getName(), s.getAge(), g.getName());
            groupDTO.getStudents().add(studentDTO);
        }
        Set<StudentDTO> result = groupDTO.getStudents();
        if (result.size() > 0) {
            return ok(result);
        }
        log.error("Students has not found with group name {}", request.getGroup());
        return notFound().build();
    }

    @PostMapping("/getStudentsByTeacher")
    public @ResponseBody
    ResponseEntity<Set<StudentDTO>> getStudentsByTeacher(@RequestBody TeacherNameRequest request) {
        Teacher t = teacherRepository.findFirstByNameIsLike(request.getTeacherName());
        if (t == null) {
            log.error("Teacher has not found with name {}", request.getTeacherName());
            return notFound().build();
        }
        Set<StudentDTO> result = new HashSet<>();
        for (Group g : t.getGroups()) {
            for (Student s : g.getStudents()) {
                StudentDTO studentDTO = new StudentDTO(s.getId(), s.getName(), s.getAge(), g.getName());
                result.add(studentDTO);
            }
        }
        if (result.size() > 0) {
            return ok(result);
        }
        log.error("Students has not found with teacher name {}", request.getTeacherName());
        return notFound().build();
    }
}
