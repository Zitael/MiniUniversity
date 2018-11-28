package ru.alexandrov.miniuniversity.dao.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.alexandrov.miniuniversity.dao.dto.GroupDTO;
import ru.alexandrov.miniuniversity.dao.dto.TeacherDTO;
import ru.alexandrov.miniuniversity.dao.entity.Group;
import ru.alexandrov.miniuniversity.dao.entity.Teacher;

@Component
public class TeacherToDtoConverter implements Converter<Teacher, TeacherDTO> {
    public TeacherDTO convert(Teacher teacher) {
        TeacherDTO teacherDTO = new TeacherDTO(teacher.getId(), teacher.getName());
        for (Group g : teacher.getGroups()) {
            GroupDTO groupDTO = new GroupDTO(g.getId(), g.getName());
            teacherDTO.getGroups().add(groupDTO);
        }
        return teacherDTO;
    }
}
