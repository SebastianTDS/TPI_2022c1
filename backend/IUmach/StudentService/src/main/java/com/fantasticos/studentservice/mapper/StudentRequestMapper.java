package com.fantasticos.studentservice.mapper;

import com.fantasticos.studentservice.dto.DtoRequestStudent;
import com.fantasticos.studentservice.model.Student;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentRequestMapper {
    StudentRequestMapper INSTANCE= Mappers.getMapper(StudentRequestMapper.class);

    DtoRequestStudent studentToDto(Student student);

    @InheritInverseConfiguration
    Student DtoStudentToStudent(DtoRequestStudent dtoStudent);
}
