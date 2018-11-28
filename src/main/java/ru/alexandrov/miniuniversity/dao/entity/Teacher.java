package ru.alexandrov.miniuniversity.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "teacher", schema = "public")
@EqualsAndHashCode(exclude = "groups")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(targetEntity = Group.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JsonIgnore
    @JoinTable(name = "groups_and_teachers",
            joinColumns = { @JoinColumn(name = "teacher_id")},
            inverseJoinColumns = {@JoinColumn(name = "groups_id")})
    private Set<Group> groups = new HashSet<>();

    public String toString(){
        return name;
    }
}
