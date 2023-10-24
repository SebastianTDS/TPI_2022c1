package com.fantasticos.studentservice.model;

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

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class Kick {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private Long idGrupo;
	private String idVotado;
	private String idVotante;
	@ManyToMany(cascade = {
            CascadeType.DETACH
    })
    @JoinTable(
            name = "kick_student",
            joinColumns = {@JoinColumn(name = "kick_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")}
    )
    private Set<Student> votantes = new HashSet<>();
}
