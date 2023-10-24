package com.fantasticos.studentservice.dto.group;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class RecommendedStudentDto {

	private String idEstudiante;
	private List<String> tags;
	private List<String> materias;
	private List<String> carreras;
	private String facultad;
	private Double coincidencia;
	
	private String image;
    private String firstName;
    private String lastName;
    private Date birthday;
    private Long valoration;
    private String email;
    private String phone;
	
}
