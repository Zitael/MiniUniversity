package ru.alexandrov.miniuniversity.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.alexandrov.miniuniversity.dao.entity.Group;
import ru.alexandrov.miniuniversity.dao.entity.Teacher;

import java.util.List;
import java.util.Set;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByNameIsLike (String name);
    Set<Group> findAllByTeachersIsContaining(Teacher t);
    Group findByNameIsLike (String name);

}
