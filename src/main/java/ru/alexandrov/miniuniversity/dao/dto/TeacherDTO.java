package ru.alexandrov.miniuniversity.dao.dto;

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
    private Set<GroupDTO> groups = new HashSet<>();
}
