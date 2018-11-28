package ru.alexandrov.miniuniversity.dao.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.alexandrov.miniuniversity.dao.entity.Student;
import ru.alexandrov.miniuniversity.dao.entity.Teacher;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class GroupDTO {
    private Long id;
    private String name;
    private Set<StudentDTO> students = new HashSet<>();
    private Set<TeacherDTO> teachers = new HashSet<>();
}
