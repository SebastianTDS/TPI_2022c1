package com.fantasticos.studentservice.dto.request;

import lombok.Data;

@Data
public class RequestJoinDataDto {

    private Long groupId;
    private Boolean accepted;
    private String usuario;

}
