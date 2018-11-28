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
public class TeacherDTO {
    private Long id;
    private String name;
    @JsonIgnore
    private Set<GroupDTO> groups = new HashSet<>();

    public TeacherDTO (Long id, String name){
        this.id = id;
        this.name = name;
    }
}
