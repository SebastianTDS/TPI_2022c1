package com.fantasticos.studentservice.mapper;

import com.fantasticos.studentservice.dto.DtoRequestStudent;
import com.fantasticos.studentservice.dto.DtoStudent;
import com.fantasticos.studentservice.model.Student;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;


@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentMapper INSTANCE= Mappers.getMapper(StudentMapper.class);

    DtoStudent studentToDto(Student student);

    @InheritInverseConfiguration
    Student DtoStudentToStudent(DtoStudent dtoStudent);

}
