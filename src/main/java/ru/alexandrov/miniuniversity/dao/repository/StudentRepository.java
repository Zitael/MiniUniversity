package ru.alexandrov.miniuniversity.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandrov.miniuniversity.dao.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByNameAndAge(String name, Integer age);
}
