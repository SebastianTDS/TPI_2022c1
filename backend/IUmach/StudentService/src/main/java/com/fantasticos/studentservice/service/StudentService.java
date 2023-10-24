package com.fantasticos.studentservice.service;

import com.fantasticos.studentservice.dto.DtoRequestStudent;
import com.fantasticos.studentservice.dto.DtoStudent;
import com.fantasticos.studentservice.model.Student;


public interface StudentService {
    DtoRequestStudent findById(String userName);

    Student save(DtoStudent dtoStudent, String bearerToken);

    DtoRequestStudent findByEmail(String email);

}
