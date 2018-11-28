package ru.alexandrov.miniuniversity.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandrov.miniuniversity.dao.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findFirstByNameIsLike(String name);
}
