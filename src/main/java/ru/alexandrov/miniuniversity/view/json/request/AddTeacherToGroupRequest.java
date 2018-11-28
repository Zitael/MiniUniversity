package ru.alexandrov.miniuniversity.view.json.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddTeacherToGroupRequest {
    private String teacher;
    private String group;
}
