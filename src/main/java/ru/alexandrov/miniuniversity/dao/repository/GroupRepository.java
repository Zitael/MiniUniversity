package ru.alexandrov.miniuniversity.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.alexandrov.miniuniversity.dao.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByNameIsLike(String name);

}
