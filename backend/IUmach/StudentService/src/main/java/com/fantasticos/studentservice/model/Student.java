package com.fantasticos.studentservice.model;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private Date createAt;
    private Long valoration;
    private String email;
    private String phone;
    private Long responsable;
    private Long colaborador;
    private Long proActivo;
    private Long organizado;
    private Long conocedor;
    private Long contribuciones;
    private Long detallista;
    private Long vetos;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
