package ru.alexandrov.miniuniversity.dao.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private Integer age;
    private String groupName;
}
