package ru.alexandrov.miniuniversity.dao.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.alexandrov.miniuniversity.dao.dto.GroupDTO;
import ru.alexandrov.miniuniversity.dao.dto.StudentDTO;
import ru.alexandrov.miniuniversity.dao.entity.Group;
import ru.alexandrov.miniuniversity.dao.entity.Student;

@Component
public class GroupToDtoConverter implements Converter<Group, GroupDTO> {
    public GroupDTO convert(Group group) {
        GroupDTO groupDTO = new GroupDTO(group.getId(), group.getName());
        for (Student s : group.getStudents()) {
            StudentDTO studentDTO = new StudentDTO(s.getId(), s.getName(), s.getAge(), group.getName());
            groupDTO.getStudents().add(studentDTO);
        }
        return groupDTO;
    }
}
