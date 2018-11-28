package ru.alexandrov.miniuniversity.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandrov.miniuniversity.dao.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByNameIsLikeAndAgeIs(String name, Integer age);
}
