package ru.alexandrov.miniuniversity.dao.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
