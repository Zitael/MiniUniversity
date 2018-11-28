package ru.alexandrov.miniuniversity.view.json.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.alexandrov.miniuniversity.dao.entity.Student;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FormGroupRequest {
    @JsonProperty("group")
    private String groupName;
    @JsonProperty("students")
    private List<Student> students;
}
