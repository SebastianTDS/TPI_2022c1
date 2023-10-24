package com.fantasticos.studentservice.dto.request;

import com.fantasticos.studentservice.dto.DtoStudent;
import com.fantasticos.studentservice.dto.group.GroupDto;
import lombok.Data;

@Data
public class RequestJoinDto {

    private Long id;
    private DtoStudent student;
    private GroupDto group;
    private Boolean accepted;
}
