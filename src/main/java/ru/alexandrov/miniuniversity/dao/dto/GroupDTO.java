package ru.alexandrov.miniuniversity.dao.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Set<StudentDTO> students = new HashSet<>();
    @JsonIgnore
    private Set<TeacherDTO> teachers = new HashSet<>();

    public GroupDTO (Long id, String name){
        this.id = id;
        this.name = name;
    }
}
