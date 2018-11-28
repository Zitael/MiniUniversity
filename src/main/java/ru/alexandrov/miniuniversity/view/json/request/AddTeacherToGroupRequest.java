package ru.alexandrov.miniuniversity.view.json.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddTeacherToGroupRequest {
    @JsonProperty("teacher")
    private String teacherName;
    @JsonProperty("group")
    private String groupName;
}
