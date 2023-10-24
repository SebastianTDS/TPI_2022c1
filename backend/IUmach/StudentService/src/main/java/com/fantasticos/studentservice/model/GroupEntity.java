package com.fantasticos.studentservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String image;
    private String description;
    private Boolean closed;
    private Integer maxNumberOfStudents;
    private Integer numberOfStudents;
    private String shift;
    private String career;
    private String subject;
    private Integer assessment;
    private Date created=new Date();
    @ManyToMany(cascade = {
            CascadeType.DETACH
    })
    @JoinTable(
            name = "group_student",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")}
    )
    private Set<Student> students = new HashSet<>();
    private String idUserAdmin;
}