package com.fantasticos.studentservice.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Data
@EqualsAndHashCode
public class HistGroup {
	@Id
    private Long id;
    private String name;
    private String subject;
    private Date created;
    private Date closed=new Date();
    @ManyToMany(cascade = {
            CascadeType.DETACH
    })
    @JoinTable(
            name = "hist_student",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")}
    )
    private Set<Student> students = new HashSet<>();
}
