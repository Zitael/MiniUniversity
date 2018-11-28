package ru.alexandrov.miniuniversity.dao.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.alexandrov.miniuniversity.dao.entity.Group;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private Integer age;
    private String groupName;
}
